package com.yu.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;

/**
 * Map转JSON共用类
 */
public class MapToJsonUtil {

    /**
     * feign调用其他微服务接口，统一返回封装对象 R
     * 将R中result转为json对象返回
     * @param r
     * @return
     */
    public static JSONObject feignMapToJson(R r){
        JSONObject jsonObj = null;
        if(!StringUtils.isEmpty(r.get("result"))){
            LinkedHashMap<String,Object> map = (LinkedHashMap<String, Object>) r.get("result");
            jsonObj = new JSONObject(map);
        }
        return jsonObj;
    }

}
