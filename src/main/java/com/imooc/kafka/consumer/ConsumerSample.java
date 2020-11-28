package com.imooc.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author pengfei.zhao
 * @date 2020/11/28 18:45
 */
public class ConsumerSample {
    private static final String TOPIC_NAME = "eru-topic";

    public static void main(String[] args) {
        hello();
        commitOffsetWithPartition();
    }

    private static void commitOffsetWithPartition() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "192.168.220.128:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "false");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer(props);
        // 消费订阅哪一个Topic或者几个Topic
        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.err.printf("partition = %d, offset = %d, key = %s, values = %s%n",
                        record.partition(), record.offset(), record.key(), record.value());
            }
        }
    }

    public static void hello() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "152.136.159.147:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer(props);
        // 消费订阅哪一个 topic或几个topic
        consumer.subscribe(Arrays.asList(TOPIC_NAME));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.err.printf("partition = %d, offset = %d, key = %s, values = %s%n",
                        record.partition(), record.offset(), record.key(), record.value());
            }
        }
    }
}
