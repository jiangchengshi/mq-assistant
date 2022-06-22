package cool.doudou.mq.assistant.core.processor.consumer;

import cool.doudou.mq.assistant.annotation.MqConsumer;
import cool.doudou.mq.assistant.core.properties.PulsarProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * ConsumerBeanPostProcessor
 *
 * @author jiangcs
 * @since 2022/2/20
 */
@Slf4j
@AllArgsConstructor
public class ConsumerBeanPostProcessor implements BeanPostProcessor {
    private PulsarClient pulsarClient;
    private PulsarProperties pulsarProperties;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(MqConsumer.class))
                .forEach(method -> {
                    MqConsumer mqConsumer = method.getAnnotation(MqConsumer.class);
                    initConsumer(mqConsumer.topics(), bean, method);
                });
        return bean;
    }

    /**
     * 初始化 消费者
     *
     * @param topics
     * @param bean
     * @param method
     */
    private void initConsumer(String[] topics, Object bean, Method method) {
        if (topics == null || topics.length <= 0) {
            log.error("initConsumer error: @MqConsumer.topics must be specified");
            return;
        }

        try {
            pulsarClient.newConsumer()
                    .topic(topics)
                    .subscriptionName(pulsarProperties.getSubscriptionName())
                    .subscriptionType(SubscriptionType.valueOf(pulsarProperties.getSubscriptionType()))
                    .subscriptionInitialPosition(SubscriptionInitialPosition.valueOf(pulsarProperties.getSubscriptionInitialPosition()))
                    .negativeAckRedeliveryDelay(pulsarProperties.getNegativeAckRedeliveryDelay(), TimeUnit.SECONDS)
                    .messageListener((consumer, msg) -> {
                        try {
                            method.setAccessible(true);
                            method.invoke(bean, consumer.getTopic(), msg.getData());
                            consumer.acknowledge(msg);
                        } catch (Exception e) {
                            consumer.negativeAcknowledge(msg);

                            throw new RuntimeException("bean[" + bean + "].method[" + method + "]invoke exception: ", e);
                        }
                    })
                    .subscribe();
        } catch (PulsarClientException e) {
            log.error("initConsumer[{}] exception: ", topics, e);
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
