package cool.doudou.celery.common.mq.helper;

import cool.doudou.celery.common.mq.ConcurrentMapFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;

import java.nio.charset.StandardCharsets;
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
            return ConcurrentMapFactory.get(topic)
                    .send(msg.getBytes(StandardCharsets.UTF_8))
                    .toString();
        } catch (PulsarClientException e) {
            log.error("send exception: ", e);
        }
        return null;
    }

    @Override
    public String send(String topic, String key, String msg) {
        try {
            return ConcurrentMapFactory.get(topic)
                    .newMessage()
                    .key(key)
                    .value(msg.getBytes(StandardCharsets.UTF_8))
                    .send()
                    .toString();
        } catch (PulsarClientException e) {
            log.error("send exception: ", e);
        }
        return null;
    }

    @Override
    public String send(String topic, String msg, long delay) {
        try {
            return ConcurrentMapFactory.get(topic)
                    .newMessage()
                    .value(msg.getBytes(StandardCharsets.UTF_8))
                    .deliverAfter(delay, TimeUnit.MILLISECONDS)
                    .send()
                    .toString();
        } catch (PulsarClientException e) {
            log.error("send exception: ", e);
        }
        return null;
    }

    @Override
    public String send(String topic, String key, String msg, long delay) {
        try {
            return ConcurrentMapFactory.get(topic)
                    .newMessage()
                    .key(key)
                    .value(msg.getBytes(StandardCharsets.UTF_8))
                    .deliverAfter(delay, TimeUnit.MILLISECONDS)
                    .send()
                    .toString();
        } catch (PulsarClientException e) {
            log.error("send exception: ", e);
        }
        return null;
    }

    @Override
    public void sendAsync(String topic, String msg, Consumer<byte[]> action) {
        CompletableFuture<MessageId> completableFuture = ConcurrentMapFactory.get(topic)
                .sendAsync(msg.getBytes(StandardCharsets.UTF_8))
                .exceptionally((e) -> {
                    log.error("sendAsync exception: ", e);
                    return null;
                });
        if (action != null) {
            completableFuture.thenAccept((messageId -> action.accept(messageId.toByteArray())));
        }
    }

    @Override
    public void sendAsync(String topic, String key, String msg, Consumer<byte[]> action) {
        CompletableFuture<MessageId> completableFuture = ConcurrentMapFactory.get(topic)
                .newMessage()
                .key(key)
                .value(msg.getBytes(StandardCharsets.UTF_8))
                .sendAsync()
                .exceptionally((e) -> {
                    log.error("sendAsync exception: ", e);
                    return null;
                });
        if (action != null) {
            completableFuture.thenAccept((messageId -> action.accept(messageId.toByteArray())));
        }
    }

    @Override
    public void sendAsync(String topic, String msg, long delay, Consumer<byte[]> action) {
        CompletableFuture<MessageId> completableFuture = ConcurrentMapFactory.get(topic)
                .newMessage()
                .value(msg.getBytes(StandardCharsets.UTF_8))
                .deliverAfter(delay, TimeUnit.MILLISECONDS)
                .sendAsync()
                .exceptionally((e) -> {
                    log.error("sendAsync exception: ", e);
                    return null;
                });
        if (action != null) {
            completableFuture.thenAccept((messageId -> action.accept(messageId.toByteArray())));
        }
    }

    @Override
    public void sendAsync(String topic, String key, String msg, long delay, Consumer<byte[]> action) {
        CompletableFuture<MessageId> completableFuture = ConcurrentMapFactory.get(topic)
                .newMessage()
                .key(key)
                .value(msg.getBytes(StandardCharsets.UTF_8))
                .deliverAfter(delay, TimeUnit.MILLISECONDS)
                .sendAsync().exceptionally((e) -> {
                    log.error("sendAsync exception: ", e);
                    return null;
                });
        if (action != null) {
            completableFuture.thenAccept((messageId -> action.accept(messageId.toByteArray())));
        }
    }
}
