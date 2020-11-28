package com.imooc.kafka.wechat.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author pengfei.zhao
 * @date 2020/11/26 20:48
 */
@Data
public class BaseResponseVO<T> {
    private String requestId;

    private T result;

    public static <T> BaseResponseVO success(){
        BaseResponseVO<Object> result = new BaseResponseVO<>();
        result.setRequestId(getRandomRequestId());
        return result;
    }

    public static <T> BaseResponseVO<T> success(T data){
        BaseResponseVO<T> result = new BaseResponseVO<>();
        result.setRequestId(getRandomRequestId());
        result.setResult(data);
        return result;
    }

    private static String getRandomRequestId() {
        return UUID.randomUUID().toString();
    }
}
