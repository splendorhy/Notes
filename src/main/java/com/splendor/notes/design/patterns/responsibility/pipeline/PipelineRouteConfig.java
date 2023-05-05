package com.splendor.notes.design.patterns.responsibility.pipeline;

import com.google.common.collect.Maps;
import com.splendor.notes.design.patterns.responsibility.pipeline.enums.SensitiveClean;
import com.splendor.notes.design.patterns.responsibility.pipeline.enums.SensitiveEffect;
import com.splendor.notes.design.patterns.responsibility.pipeline.enums.SensitiveValidate;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author splendor.s
 * @create 2022/11/28 下午2:32
 * @description 管道路由表整合配置
 */
public class PipelineRouteConfig {
    private static Map<Integer, Class> contentCleanProcessor = Maps.newHashMap();
    private static Map<Integer, Class> sensitiveValidateProcessor = Maps.newHashMap();
    private static Map<Integer, Class> sensitiveEffectProcessor = Maps.newHashMap();
    private static final Logger LOGGER = LoggerFactory.getLogger(SensitivePipelineExecutor.class);


    static {
        /*1.数据清洗能力集合*/
        Reflections contentCleanRef = new Reflections("com.splendor.notes.design.patterns.responsibility.pipeline.combination.clean");
        Set<Class<?>> contentCleanClassSet = contentCleanRef.getTypesAnnotatedWith(SensitiveClean.class);
        for (Class<?> cl : contentCleanClassSet) {
            Annotation[] annotations = cl.getAnnotations();
            for (Annotation a : annotations) {
                if (a instanceof SensitiveClean) {
                    SensitiveClean sensitiveClean = (SensitiveClean) a;
                    contentCleanProcessor.put(sensitiveClean.cleanCode(), cl);
                }
            }
        }
        /*2.敏感词校验能力集合*/
        Reflections sensitiveValidateRef = new Reflections("com.splendor.notes.design.patterns.responsibility.pipeline.combination.validate");
        Set<Class<?>> sensitiveValidateClassSet = sensitiveValidateRef.getTypesAnnotatedWith(SensitiveValidate.class);
        for (Class<?> cl : sensitiveValidateClassSet) {
            Annotation[] annotations = cl.getAnnotations();
            for (Annotation a : annotations) {
                if (a instanceof SensitiveValidate) {
                    SensitiveValidate sensitiveValidate = (SensitiveValidate) a;
                    sensitiveValidateProcessor.put(sensitiveValidate.validateCode(), cl);
                }
            }
        }
        /*3.敏感词生效能力集合*/
        Reflections sensitiveEffectRef = new Reflections("com.splendor.notes.design.patterns.responsibility.pipeline.combination.effect");
        Set<Class<?>> sensitiveEffectClassSet = sensitiveEffectRef.getTypesAnnotatedWith(SensitiveEffect.class);
        for (Class<?> cl : sensitiveEffectClassSet) {
            Annotation[] annotations = cl.getAnnotations();
            for (Annotation a : annotations) {
                if (a instanceof SensitiveEffect) {
                    SensitiveEffect sensitiveEffect = (SensitiveEffect) a;
                    sensitiveEffectProcessor.put(sensitiveEffect.effectCode(), cl);
                }
            }
        }
    }

    public static ContextHandler getInstance(int code) {
        if (contentCleanProcessor.containsKey(code)) {
            try {
                Constructor constructor = contentCleanProcessor.get(code).getConstructor();
                return (ContextHandler) constructor.newInstance();
            } catch (Exception ex) {
                LOGGER.error("ContextHandler contentCleanProcessor getInstance error, exception:", ex);
            }
        }
        if (sensitiveValidateProcessor.containsKey(code)) {
            try {
                Constructor constructor = sensitiveValidateProcessor.get(code).getConstructor();
                return (ContextHandler) constructor.newInstance();
            } catch (Exception ex) {
                LOGGER.error("ContextHandler sensitiveValidateProcessor getInstance error, exception:", ex);
            }
        }
        if (sensitiveEffectProcessor.containsKey(code)) {
            try {
                Constructor constructor = sensitiveEffectProcessor.get(code).getConstructor();
                return (ContextHandler) constructor.newInstance();
            } catch (Exception ex) {
                LOGGER.error("ContextHandler sensitiveEffectProcessor getInstance error, exception:", ex);
            }
        }
        return null;
    }

    public static Map<Integer, Class> getContentCleanProcessor() {
        return contentCleanProcessor;
    }

    public static Map<Integer, Class> getSensitiveValidateProcessor() {
        return sensitiveValidateProcessor;
    }

    public static Map<Integer, Class> getSensitiveEffectProcessor() {
        return sensitiveEffectProcessor;
    }

}
