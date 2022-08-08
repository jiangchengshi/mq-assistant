# 欢迎使用 MQ-Assistant

[![Maven](https://img.shields.io/badge/Maven-v1.0.5-blue)](https://search.maven.org/search?q=g:cool.doudou%20a:mq-assistant-*)
[![License](https://img.shields.io/badge/License-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0)
![SpringBoot](https://img.shields.io/badge/SpringBoot-v2.6.10-blue)

## 简介

MQ助手 - 简化配置，注解带飞！

## 特点

> 配置灵活，基于pulsar包，没有改变任何框架结构，只为简化； 简单注解，即可实现消息MQ

## 使用指引

### 引入依赖

```kotlin
implementation("cool.doudou:mq-assistant-boot-starter:latest")
```

### Pulsar配置

> 依赖spring自动注入PulsarClient，配置属性如下：

```yaml
pulsar:
  service-url: pulsar://127.0.0.1:6650
  subscription-name: sub-celery
  subscription-type: Shared
```

### 使用方式

> 消息订阅

- 生产者与topic进行关联绑定

```java
/**
 * 生产者主题绑定
 */
@MqProducer(topics = {"celery"})
@Component
public class MqComponent {
}
```

- 消费者与topic进行关联绑定，注意：每个消费者须绑定一个subscription-name后才能进行消费

```java
/**
 * 消费者主题绑定
 */
@Component
public class MqComponent {
    @MqConsumer(topics = {"celery"})
    public void receive(String topic, byte[] msg) {
        System.out.println("consumer: topic[" + topic + "] => " + new String(msg));
    }
}
```

> 消息发送

- send()：发送
- sendAsync()：异步发送

```java
/**
 * 消息发送
 */
@AllArgsConstructor
@Service
public class MqServiceImpl {
    private MqHelper mqHelper;

    public void test() {
        // 同步
        String msgId = mqHelper.send("celery", "hello");
        System.out.println("send: " + msgId);

        // 异步
        mqHelper.sendAsync("celery", "您好Async", System.out::println);

        // 同步
        String msgId = mqHelper.send("celery", new byte[]{0x01, 0x02, 0x03, 0x04});
        System.out.println("send: " + msgId);

        // 异步
        mqHelper.sendAsync("celery", new byte[]{0x01, 0x02, 0x03, 0x04}, System.out::println);
    }
}
```

## 版权

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## 鼓励一下，喝杯咖啡

> 欢迎提出宝贵意见，不断完善 MQ-Assistant

![鼓励一下，喝杯咖啡](https://user-images.githubusercontent.com/21210629/172556529-544b2581-ea34-4530-932b-148198b1b265.jpg)
