package com.imooc.kafka.wechat.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengfei.zhao
 * @date 2020/11/28 18:29
 */
@Configuration
@ConfigurationProperties(prefix = "wechat.kafka")
@Data
public class KafkaProperties {
    private String bootstrapServers;
    private String acksConfig;
    private String partitionerClass;
}
