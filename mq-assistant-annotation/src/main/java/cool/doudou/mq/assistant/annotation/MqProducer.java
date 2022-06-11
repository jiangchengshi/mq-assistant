package cool.doudou.mq.assistant.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MqProducer
 *
 * @author jiangcs
 * @since 2022/2/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MqProducer {
    /**
     * @return 生产主题
     */
    String[] topics() default {};
}
