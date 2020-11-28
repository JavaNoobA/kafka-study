package com.imooc.kafka.wechat.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author pengfei.zhao
 * @date 2020/11/28 14:05
 */
@Configuration
@ConfigurationProperties(prefix = "template")
@Data
public class WechatTemplateProperties {

    private List<WechatTemplate> templates;

    private Integer templateResultType;

    private String templateResultFilePath;

    @Data
    public static class WechatTemplate {
        private Integer templateId;
        private String templateFilePath;
        private Boolean active;
    }
}
