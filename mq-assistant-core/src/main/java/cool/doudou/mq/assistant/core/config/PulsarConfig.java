package cool.doudou.mq.assistant.core.config;

import cool.doudou.mq.assistant.core.processor.consumer.ConsumerBeanPostProcessor;
import cool.doudou.mq.assistant.core.processor.producer.ProducerBeanPostProcessor;
import cool.doudou.mq.assistant.core.properties.PulsarProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

/**
 * PulsarConfig
 *
 * @author jiangcs
 * @since 2022/2/20
 */
@Slf4j
@AllArgsConstructor
public class PulsarConfig {
    private PulsarProperties pulsarProperties;

    @Bean
    public PulsarClient pulsarClient() {
        try {
            return PulsarClient.builder()
                    .serviceUrl(pulsarProperties.getServiceUrl())
                    .build();
        } catch (PulsarClientException e) {
            log.error("pulsarClient exception: ", e);
        }
        return null;
    }

    @DependsOn({"pulsarClient"})
    @Bean
    public ProducerBeanPostProcessor pulsarProducerBeanPostProcessor(PulsarClient pulsarClient) {
        return new ProducerBeanPostProcessor(pulsarClient, pulsarProperties);
    }

    @DependsOn({"pulsarClient"})
    @Bean
    public ConsumerBeanPostProcessor pulsarConsumerBeanPostProcessor(PulsarClient pulsarClient) {
        return new ConsumerBeanPostProcessor(pulsarClient, pulsarProperties);
    }
}
