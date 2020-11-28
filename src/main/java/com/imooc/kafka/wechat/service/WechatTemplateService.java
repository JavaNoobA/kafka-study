package com.imooc.kafka.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.imooc.kafka.wechat.conf.WechatTemplateProperties;

/**
 * @author pengfei.zhao
 * @date 2020/11/28 14:09
 */
public interface WechatTemplateService {
    /**
     * 获取微信调查问卷模板 (active=true)
     * @return 调查问卷模板
     */
    WechatTemplateProperties.WechatTemplate getWechatTemplate();

    /**
     * 上报调查问卷填写结果
     * @param reportInfo 查问卷填写结果
     */
    void templateReported(JSONObject reportInfo);

    /**
     * 获取调查问卷的统计结果
     * @param templateId 调查问卷模板Id
     * @return 调查问卷的统计结果
     */
    JSONObject templateStatistics(String templateId);
}
