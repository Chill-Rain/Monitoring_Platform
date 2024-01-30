package asia.serverchillrain.school.server.utils;


import com.alibaba.fastjson2.JSON;

/**
 * @auther 2024 01 28
 * JSON支持工具
 */

public class JsonUtil {
    public static String object2Json(Object obj){
        return JSON.toJSONString(obj);
    }
    public static <T> T json2Object(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }
}
