package cool.doudou.mq.assistant.boot.starter.auto.configuration;

import cool.doudou.celery.common.mq.config.PulsarConfig;
import cool.doudou.celery.common.mq.helper.MqHelper;
import cool.doudou.celery.common.mq.helper.PulsarHelper;
import cool.doudou.celery.common.mq.properties.PulsarProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * MqAutoConfiguration
 *
 * @author jiangcs
 * @since 2022/2/19
 */
@EnableConfigurationProperties({PulsarProperties.class})
@Import({PulsarConfig.class})
@Configuration
public class MqAutoConfiguration {
    @ConditionalOnMissingBean(MqHelper.class)
    @Bean
    public MqHelper mqHelper() {
        return new PulsarHelper();
    }
}
