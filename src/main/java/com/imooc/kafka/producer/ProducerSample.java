package com.imooc.kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Producer Sample
 *
 * @author pengfei.zhao
 * @date 2020/11/26 20:10
 */
public class ProducerSample {
    public static final String ERU_TOPIC = "eru-topic";

    private static Properties properties = null;

    static {
        properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "152.136.159.147:9092");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "0");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        properties.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");

        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    }

    public static void main(String[] args) throws Exception {
        //producerSend();

        producerSyncSend();

        //producerSendWithCallback();

    }

    /**
     * Producer 异步发送带回调函数
     */
    private static void producerSendWithCallback() {
        Producer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>(ERU_TOPIC, "key-" + i, "value-" + i);

            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println(
                            "partition : " + metadata.partition() + " , offset : " + metadata.offset());
                }
            });
        }
        // 所有打开的通道都关闭
        producer.close();
    }

    private static void producerSyncSend() throws Exception {
        Producer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 10; i++) {
            String key = "key-" + i;
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>(ERU_TOPIC, key, "value-" + i);
            Future<RecordMetadata> send = producer.send(producerRecord);
            RecordMetadata recordMetadata = send.get();
            System.out.println(key + "partition : " + recordMetadata.partition() + " , offset : " + recordMetadata.offset());
        }
        // 所有打开的通道都关闭
        producer.close();
    }

    /**
     * Producer 发送消息
     */
    private static void producerSend() {

        Producer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 10; i++) {
            // ProducerRecord 消息对象
            producer.send(new ProducerRecord<>(ERU_TOPIC, "key-" + i, "value-" + i));
        }

        // 所有打开的通道都关闭
        producer.close();
    }
}
