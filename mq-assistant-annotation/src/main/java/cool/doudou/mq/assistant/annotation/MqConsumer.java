package cool.doudou.mq.assistant.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MqConsumer
 *
 * @author jiangcs
 * @since 2022/2/18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MqConsumer {
    /**
     * @return 消费主题
     */
    String[] topics() default {};
}
