package cool.doudou.mq.assistant.core.helper;

import java.util.function.Consumer;

/**
 * MqHelper
 *
 * @author jiangcs
 * @since 2022/2/18
 */
public interface MqHelper {
    /**
     * 发送消息
     *
     * @param topic 主题
     * @param msg   消息
     * @return MessageId
     */
    String send(String topic, String msg);

    /**
     * 发送消息
     *
     * @param topic 主题
     * @param key   关键字
     * @param msg   消息
     * @return MessageId
     */
    String send(String topic, String key, String msg);

    /**
     * 发送消息
     *
     * @param topic 主题
     * @param msg   消息
     * @param delay 延迟时间，单位 毫秒
     * @return MessageId
     */
    String send(String topic, String msg, long delay);

    /**
     * 发送消息
     *
     * @param topic 主题
     * @param key   关键字
     * @param msg   消息
     * @param delay 延迟时间，单位 毫秒
     * @return MessageId
     */
    String send(String topic, String key, String msg, long delay);

    /**
     * 异步发送消息
     *
     * @param topic  主题
     * @param msg    消息
     * @param action 回执动作，参数为MessageId
     */
    void sendAsync(String topic, String msg, Consumer<String> action);

    /**
     * 异步发送消息
     *
     * @param topic  主题
     * @param key    关键字
     * @param msg    消息
     * @param action 回执动作，参数为MessageId
     */
    void sendAsync(String topic, String key, String msg, Consumer<String> action);

    /**
     * 异步发送消息
     *
     * @param topic  主题
     * @param msg    消息
     * @param delay  延迟时间，单位 毫秒
     * @param action 回执动作，参数为MessageId
     */
    void sendAsync(String topic, String msg, long delay, Consumer<String> action);

    /**
     * 异步发送消息
     *
     * @param topic  主题
     * @param key    关键字
     * @param msg    消息
     * @param delay  延迟时间，单位 毫秒
     * @param action 回执动作，参数为MessageId
     */
    void sendAsync(String topic, String key, String msg, long delay, Consumer<String> action);
}
