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
        return send(topic, msg.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String send(String topic, byte[] msgArr) {
        try {
            return ConcurrentMapFactory.get(topic)
                    .send(msgArr)
                    .toString();
        } catch (PulsarClientException e) {
            log.error("send exception: ", e);
        }
        return null;
    }

    @Override
    public String send(String topic, String key, String msg) {
        return send(topic, key, msg.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String send(String topic, String key, byte[] msgArr) {
        try {
            return ConcurrentMapFactory.get(topic)
                    .newMessage()
                    .key(key)
                    .value(msgArr)
                    .send()
                    .toString();
        } catch (PulsarClientException e) {
            log.error("send exception: ", e);
        }
        return null;
    }

    @Override
    public String send(String topic, String msg, long delay) {
        return send(topic, msg.getBytes(StandardCharsets.UTF_8), delay);
    }

    @Override
    public String send(String topic, byte[] msgArr, long delay) {
        try {
            return ConcurrentMapFactory.get(topic)
                    .newMessage()
                    .value(msgArr)
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
        return send(topic, key, msg.getBytes(StandardCharsets.UTF_8), delay);
    }

    @Override
    public String send(String topic, String key, byte[] msgArr, long delay) {
        try {
            return ConcurrentMapFactory.get(topic)
                    .newMessage()
                    .key(key)
                    .value(msgArr)
                    .deliverAfter(delay, TimeUnit.MILLISECONDS)
                    .send()
                    .toString();
        } catch (PulsarClientException e) {
            log.error("send exception: ", e);
        }
        return null;
    }

    @Override
    public void sendAsync(String topic, String msg, Consumer<String> action) {
        sendAsync(topic, msg.getBytes(StandardCharsets.UTF_8), action);
    }

    @Override
    public void sendAsync(String topic, byte[] msgArr, Consumer<String> action) {
        CompletableFuture<MessageId> completableFuture = ConcurrentMapFactory.get(topic)
                .sendAsync(msgArr)
                .exceptionally((e) -> {
                    log.error("sendAsync exception: ", e);
                    return null;
                });
        if (action != null) {
            completableFuture.thenAccept((messageId -> action.accept(messageId.toString())));
        }
    }

    @Override
    public void sendAsync(String topic, String key, String msg, Consumer<String> action) {
        sendAsync(topic, key, msg.getBytes(StandardCharsets.UTF_8), action);
    }

    @Override
    public void sendAsync(String topic, String key, byte[] msgArr, Consumer<String> action) {
        CompletableFuture<MessageId> completableFuture = ConcurrentMapFactory.get(topic)
                .newMessage()
                .key(key)
                .value(msgArr)
                .sendAsync()
                .exceptionally((e) -> {
                    log.error("sendAsync exception: ", e);
                    return null;
                });
        if (action != null) {
            completableFuture.thenAccept((messageId -> action.accept(messageId.toString())));
        }
    }

    @Override
    public void sendAsync(String topic, String msg, long delay, Consumer<String> action) {
        sendAsync(topic, msg.getBytes(StandardCharsets.UTF_8), delay, action);
    }

    @Override
    public void sendAsync(String topic, byte[] msgArr, long delay, Consumer<String> action) {
        CompletableFuture<MessageId> completableFuture = ConcurrentMapFactory.get(topic)
                .newMessage()
                .value(msgArr)
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

    @Override
    public void sendAsync(String topic, String key, String msg, long delay, Consumer<String> action) {
        sendAsync(topic, key, msg.getBytes(StandardCharsets.UTF_8), delay, action);
    }

    @Override
    public void sendAsync(String topic, String key, byte[] msgArr, long delay, Consumer<String> action) {
        CompletableFuture<MessageId> completableFuture = ConcurrentMapFactory.get(topic)
                .newMessage()
                .key(key)
                .value(msgArr)
                .deliverAfter(delay, TimeUnit.MILLISECONDS)
                .sendAsync().exceptionally((e) -> {
                    log.error("sendAsync exception: ", e);
                    return null;
                });
        if (action != null) {
            completableFuture.thenAccept((messageId -> action.accept(messageId.toString())));
        }
    }
}
