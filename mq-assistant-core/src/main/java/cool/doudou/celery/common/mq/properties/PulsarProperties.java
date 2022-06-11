package cool.doudou.celery.common.mq.properties;

import lombok.Data;
import org.apache.pulsar.client.api.CompressionType;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * PulsarProperties
 *
 * @author jiangcs
 * @since 2022/2/18
 */
@Data
@ConfigurationProperties(prefix = "pulsar")
public class PulsarProperties {
    private String serviceUrl = "pulsar://127.0.0.1:6650";

    ////////// 生产者相关参数 //////////
    /**
     * 压缩策略，目前支持 4 种策略 (NONE、LZ4、ZLIB、ZSTD)
     */
    private String compressionType = CompressionType.LZ4.name();
    /**
     * 超时时间：默认30s
     * <p>
     * 如果 Producer 在超时时间未收到 ACK，会进行重新发送
     * 设置为0代表无限制，建议配置为0
     */
    private Integer sendTimeout = 30;
    /**
     * 是否开启消息批量处理：默认true
     * <p>
     * 这个参数只有在异步发送 (sendAsync) 时才能生效，选择同步发送会失效
     */
    private Boolean enableBatching = true;
    /**
     * 批量发送消息的时间段：默认 10ms
     * <p>
     * 需要注意的是，设置了批量时间，就不会受消息数量的影响。
     * 批量发送会把要发送的批量消息放在一个网络包里发送出去，减少网络 IO 次数，大大提高网卡的发送效率
     */
    private Integer batchingMaxPublishDelay = 10;
    /**
     * 批量发送消息的最大数量，默认1000
     */
    private Integer batchingMaxMessages = 1000;
    /**
     * 等待从 broker 接收 ACK 的消息队列最大长度，默认1000
     */
    private Integer maxPendingMessages = 1000;
    /**
     * Producer 发送消息时会把消息先放入本地 Queue 缓存，如果缓存满了，就会阻塞消息发送，默认为false
     */
    private Boolean blockIfQueueFull = false;
    /**
     * 如果发送消息时没有指定 key，那默认采用 round robin 的方式切换 partition 的频率，默认10ms
     */
    private Integer roundRobinRouterBatchingPartitionSwitchFrequency = 10;

    ////////// 消费者相关参数 //////////
    /**
     * 关联的 subscription 名
     */
    private String subscriptionName = "default";
    /**
     * 订阅类型： 默认Shared
     * <p>
     * Exclusive：独占模式，同一个 Topic 只能有一个消费者，如果多个消费者，就会出错
     * Failover：灾备模式，同一个 Topic 可以有多个消费者，但是只能有一个消费者消费，其他消费者作为故障转移备用，如果当前消费者出了故障，就从备用消费者中选择一个进行消费
     * Shared：共享模式，同一个 Topic 可以由多个消费者订阅和消费。消息通过 round robin 轮询机制分发给不同的消费者，并且每个消息仅会被分发给一个消费者。当消费者断开，如果发送给它消息没有被消费，这些消息会被重新分发给其它存活的消费者
     * Key_Shared：消息和消费者都会绑定一个key，消息只会发送给绑定同一个key的消费者。如果有新消费者建立连接或者有消费者断开连接，就需要更新一些消息的 key。跟 Shared 模式相比，Key_Shared 的好处是既可以让消费者并发地消费消息，又能保证同一Key下的消息顺序
     */
    private String subscriptionType = SubscriptionType.Shared.name();
    /**
     * 新的 subscription 从哪里开始消费：
     * <p>
     * Latest：从最新的消息开始消费
     * Earliest：从最早的消息开始消费
     */
    private String subscriptionInitialPosition = SubscriptionInitialPosition.Earliest.name();

    /**
     * 消费失败后间隔多久 broker 重新发送，默认60s
     */
    private Integer negativeAckRedeliveryDelay = 60;
}
