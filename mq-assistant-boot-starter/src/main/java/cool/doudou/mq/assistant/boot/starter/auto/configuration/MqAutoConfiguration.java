package cool.doudou.mq.assistant.boot.starter.auto.configuration;

import cool.doudou.mq.assistant.core.config.PulsarConfig;
import cool.doudou.mq.assistant.core.helper.MqHelper;
import cool.doudou.mq.assistant.core.helper.PulsarHelper;
import cool.doudou.mq.assistant.core.properties.PulsarProperties;
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
        System.out.println("mqHelper");
        return new PulsarHelper();
    }
}
