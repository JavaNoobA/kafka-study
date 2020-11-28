package com.imooc.kafka.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imooc.kafka.wechat.common.BaseResponseVO;
import com.imooc.kafka.wechat.conf.WechatTemplateProperties;
import com.imooc.kafka.wechat.service.WechatTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pengfei.zhao
 * @date 2020/11/28 15:24
 */
@RestController
@RequestMapping(value = "/v1")
public class WechatTemplateController {
    @Autowired
    private WechatTemplateService wechatTemplateService;

    @GetMapping(value = "/template")
    public BaseResponseVO getTemplate() {
        WechatTemplateProperties.WechatTemplate wechatTemplate = wechatTemplateService.getWechatTemplate();
        return BaseResponseVO.success(wechatTemplate);
    }

    @GetMapping(value = "/template/result")
    public BaseResponseVO templateStatistics(@RequestParam(value = "templateId", required = false)String templateId) {
        JSONObject statistics = wechatTemplateService.templateStatistics(templateId);

        return BaseResponseVO.success(statistics);
    }

    @PostMapping(value = "/template/report")
    public BaseResponseVO dataReported(@RequestBody String reportData) {
        wechatTemplateService.templateReported(JSON.parseObject(reportData));

        return BaseResponseVO.success();
    }
}
