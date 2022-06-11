package cool.doudou.celery.common.mq.processor.producer;

import cool.doudou.celery.common.mq.ConcurrentMapFactory;
import cool.doudou.mq.assistant.annotation.MqProducer;
import cool.doudou.celery.common.mq.properties.PulsarProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.CompressionType;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * ProducerBeanPostProcessor
 *
 * @author jiangcs
 * @since 2022/2/19
 */
@Slf4j
@AllArgsConstructor
public class ProducerBeanPostProcessor implements BeanPostProcessor {
    private PulsarClient pulsarClient;
    private PulsarProperties pulsarProperties;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        boolean annotationPresent = bean.getClass().isAnnotationPresent(MqProducer.class);
        if (annotationPresent) {
            MqProducer mqProducer = bean.getClass().getAnnotation(MqProducer.class);
            initProducer(mqProducer.topics());
        }
        return bean;
    }

    /**
     * 初始化 生产者
     *
     * @param topics
     */
    private void initProducer(String[] topics) {
        if (topics == null || topics.length <= 0) {
            log.error("initProducer error: @MqProducer.topics must be specified");
            return;
        }

        Arrays.stream(topics).forEach((topic) -> {
            try {
                ConcurrentMapFactory.add(topic, pulsarClient.newProducer()
                        .topic(topic)
                        .compressionType(CompressionType.valueOf(pulsarProperties.getCompressionType()))
                        .sendTimeout(pulsarProperties.getSendTimeout(), TimeUnit.SECONDS)
                        .enableBatching(pulsarProperties.getEnableBatching())
                        .batchingMaxPublishDelay(pulsarProperties.getBatchingMaxPublishDelay(), TimeUnit.SECONDS)
                        .batchingMaxMessages(pulsarProperties.getBatchingMaxMessages())
                        .blockIfQueueFull(pulsarProperties.getBlockIfQueueFull())
                        .roundRobinRouterBatchingPartitionSwitchFrequency(pulsarProperties.getRoundRobinRouterBatchingPartitionSwitchFrequency())
                        .create());
            } catch (PulsarClientException e) {
                log.error("initProducer[{}] exception: ", topic, e);
            }
        });
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
