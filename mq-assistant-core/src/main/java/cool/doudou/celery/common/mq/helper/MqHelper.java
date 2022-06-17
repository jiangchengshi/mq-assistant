package cool.doudou.celery.common.mq.helper;

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
     * @param topic  主题
     * @param msgArr 消息数组
     * @return MessageId
     */
    String send(String topic, byte[] msgArr);

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
     * @param topic  主题
     * @param key    关键字
     * @param msgArr 消息数组
     * @return MessageId
     */
    String send(String topic, String key, byte[] msgArr);

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
     * @param topic  主题
     * @param msgArr 消息数组
     * @param delay  延迟时间，单位 毫秒
     * @return MessageId
     */
    String send(String topic, byte[] msgArr, long delay);

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
     * 发送消息
     *
     * @param topic  主题
     * @param key    关键字
     * @param msgArr 消息数组
     * @param delay  延迟时间，单位 毫秒
     * @return MessageId
     */
    String send(String topic, String key, byte[] msgArr, long delay);

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
     * @param msgArr 消息数组
     * @param action 回执动作，参数为MessageId
     */
    void sendAsync(String topic, byte[] msgArr, Consumer<String> action);

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
     * @param key    关键字
     * @param msgArr 消息数组
     * @param action 回执动作，参数为MessageId
     */
    void sendAsync(String topic, String key, byte[] msgArr, Consumer<String> action);

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
     * @param msgArr 消息数组
     * @param delay  延迟时间，单位 毫秒
     * @param action 回执动作，参数为MessageId
     */
    void sendAsync(String topic, byte[] msgArr, long delay, Consumer<String> action);

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

    /**
     * 异步发送消息
     *
     * @param topic  主题
     * @param key    关键字
     * @param msgArr 消息数组
     * @param delay  延迟时间，单位 毫秒
     * @param action 回执动作，参数为MessageId
     */
    void sendAsync(String topic, String key, byte[] msgArr, long delay, Consumer<String> action);
}
