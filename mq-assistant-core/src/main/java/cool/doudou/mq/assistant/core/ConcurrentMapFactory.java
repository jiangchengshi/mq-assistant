package cool.doudou.mq.assistant.core;

import org.apache.pulsar.client.api.Producer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentMapFactory
 *
 * @author jiangcs
 * @since 2022/2/19
 */
public class ConcurrentMapFactory {
    private static final Map<String, Producer<byte[]>> PRODUCER_MAP = new ConcurrentHashMap<>();

    public static Producer<byte[]> get(String topic) {
        return PRODUCER_MAP.get(topic);
    }

    public static void add(String topic, Producer<byte[]> producer) {
        PRODUCER_MAP.put(topic, producer);
    }
}
