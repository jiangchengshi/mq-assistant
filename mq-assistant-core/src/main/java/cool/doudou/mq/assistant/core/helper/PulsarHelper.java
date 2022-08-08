package cool.doudou.mq.assistant.core.helper;

import cool.doudou.mq.assistant.core.ConcurrentMapFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * PulsarHelper
 *
 * @author jiangcs
 * @since 2022/2/19
 */
@Slf4j
public class PulsarHelper implements MqHelper {
    @Override
    public String send(String topic, String msg) {
        try {
            Producer<String> producer = ConcurrentMapFactory.get(topic);
            if (producer != null) {
                return producer
                        .send(msg)
                        .toString();
            }
        } catch (PulsarClientException e) {
            log.error("send exception: ", e);
        }
        return null;
    }

    @Override
    public String send(String topic, String key, String msg) {
        try {
            Producer<String> producer = ConcurrentMapFactory.get(topic);
            if (producer != null) {
                return producer
                        .newMessage()
                        .key(key)
                        .value(msg)
                        .send()
                        .toString();
            }
        } catch (PulsarClientException e) {
            log.error("send exception: ", e);
        }
        return null;
    }

    @Override
    public String send(String topic, String msg, long delay) {
        try {
            Producer<String> producer = ConcurrentMapFactory.get(topic);
            if (producer != null) {
                return producer
                        .newMessage()
                        .value(msg)
                        .deliverAfter(delay, TimeUnit.MILLISECONDS)
                        .send()
                        .toString();
            }
        } catch (PulsarClientException e) {
            log.error("send exception: ", e);
        }
        return null;
    }

    @Override
    public String send(String topic, String key, String msg, long delay) {
        try {
            Producer<String> producer = ConcurrentMapFactory.get(topic);
            if (producer != null) {
                return producer
                        .newMessage()
                        .key(key)
                        .value(msg)
                        .deliverAfter(delay, TimeUnit.MILLISECONDS)
                        .send()
                        .toString();
            }
        } catch (PulsarClientException e) {
            log.error("send exception: ", e);
        }
        return null;
    }

    @Override
    public void sendAsync(String topic, String msg, Consumer<String> action) {
        Producer<String> producer = ConcurrentMapFactory.get(topic);
        if (producer != null) {
            CompletableFuture<MessageId> completableFuture = producer.sendAsync(msg)
                    .exceptionally((e) -> {
                        log.error("sendAsync exception: ", e);
                        return null;
                    });
            if (action != null) {
                completableFuture.thenAccept((messageId -> action.accept(messageId.toString())));
            }
        }
    }

    @Override
    public void sendAsync(String topic, String key, String msg, Consumer<String> action) {
        Producer<String> producer = ConcurrentMapFactory.get(topic);
        if (producer != null) {
            CompletableFuture<MessageId> completableFuture = producer.newMessage()
                    .key(key)
                    .value(msg)
                    .sendAsync()
                    .exceptionally((e) -> {
                        log.error("sendAsync exception: ", e);
                        return null;
                    });
            if (action != null) {
                completableFuture.thenAccept((messageId -> action.accept(messageId.toString())));
            }
        }
    }

    @Override
    public void sendAsync(String topic, String msg, long delay, Consumer<String> action) {
        Producer<String> producer = ConcurrentMapFactory.get(topic);
        if (producer != null) {
            CompletableFuture<MessageId> completableFuture = producer.newMessage()
                    .value(msg)
                    .deliverAfter(delay, TimeUnit.MILLISECONDS)
                    .sendAsync()
                    .exceptionally((e) -> {
                        log.error("sendAsync exception: ", e);
                        return null;
                    });
            if (action != null) {
                completableFuture.thenAccept((messageId -> action.accept(messageId.toString())));
            }
        }
    }

    @Override
    public void sendAsync(String topic, String key, String msg, long delay, Consumer<String> action) {
        Producer<String> producer = ConcurrentMapFactory.get(topic);
        if (producer != null) {
            CompletableFuture<MessageId> completableFuture = producer.newMessage()
                    .key(key)
                    .value(msg)
                    .deliverAfter(delay, TimeUnit.MILLISECONDS)
                    .sendAsync()
                    .exceptionally((e) -> {
                        log.error("sendAsync exception: ", e);
                        return null;
                    });
            if (action != null) {
                completableFuture.thenAccept((messageId -> action.accept(messageId.toString())));
            }
        }
    }
}
