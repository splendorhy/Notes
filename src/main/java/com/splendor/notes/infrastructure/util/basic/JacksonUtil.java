package com.splendor.notes.infrastructure.util.basic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Map;


/**
 * json工具类
 * @author Administrator
 *
 */
@Slf4j
public class JacksonUtil {

	private  static ObjectMapper objectMapper = null;


	static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
    }

	/**
     * 对象转换成json
     * @param obj
     * @param <T>
     * @return
     */
    public static <T>String objectToJson(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Parse Object to Json error",e);
            return null;
        }
    }
    
    /**
     * 将json转换成对象Class
     * @param src
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T>T jsonToObject(String src,Class<T> clazz){
        if(StringUtils.isEmpty(src) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) src : objectMapper.readValue(src,clazz);
        } catch (Exception e) {
            log.warn("Parse Json to Object error",e);
            return null;
        }
    }

    /**
     * 字符串转换为 Map<String, Object>
     *
     * @param src
     * @return
     * @throws Exception
     */
    public static <T> Map<String, Object> jsonToMap(String src) {
        if(StringUtils.isEmpty(src)){
            return null;
        }
        try {
            return objectMapper.readValue(src, Map.class);
        } catch (Exception e) {
            log.warn("Parse Json to Map error",e);
            return null;
        }

    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
    
}
