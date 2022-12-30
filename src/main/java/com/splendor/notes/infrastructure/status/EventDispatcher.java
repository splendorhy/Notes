package com.splendor.notes.infrastructure.status;

import com.google.common.collect.Maps;
import com.splendor.notes.infrastructure.status.service.CompareTaskPo;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author splendor.s
 * @create 2022/11/29 上午11:40
 * @description 状态逻辑分发器
 * 我们针对以上任务处理器，对实际业务处理进行分析并将其转发到相关的处理器上进行自动化处理
 * 负责对task任务不同状态运行逻辑的分发。
 */

public class EventDispatcher {
    private static Map<Integer, Class> status2Processor = Maps.newHashMap();
    private static Set<AbstractProcessor> curProcessors = new HashSet<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(EventDispatcher.class);

    static {
        Reflections reflections = new Reflections("com.splendor.notes.infrastructure.status.impl");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Status.class);
        for (Class<?> cl : classSet) {
            Annotation[] annotations = cl.getAnnotations();
            for (Annotation a : annotations) {
                if (a instanceof Status) {
                    Status status = (Status) a;
                    status2Processor.put(status.status(), cl);
                }
            }
        }
    }


   /**
     * dispatch方法目前只有cronServer线程调用，
     * 但是为了防止出现多线程调用导致的curProcessors被并发修改问题，所以用synchronized同步
     *
     * @param status        当前任务状态
     * @param compareTaskPo 比对任务消息数据
     * @return
     */

    public static synchronized boolean dispatch(int status, CompareTaskPo compareTaskPo) {
        AbstractProcessor processor = getInstance(status, compareTaskPo);
        if (processor != null) {
            curProcessors.add(processor);
            processor.process();
            return true;
        }
        return false;
    }

    private static AbstractProcessor getInstance(int status, CompareTaskPo compareTaskPo) {
        /*:主动清理一次*/
        cleanDirty();
        if (containsStatus(status)) {
            try {
                Constructor constructor = status2Processor.get(status).getConstructor(CompareTaskPo.class);
                return (AbstractProcessor) constructor.newInstance(compareTaskPo);
            } catch (Exception ex) {
                LOGGER.error("EventDispatcher dispatcher getInstance error, exception:", ex);
            }
        }
        return null;
    }

    public static boolean containsStatus(int status) {
        return status2Processor.containsKey(status);
    }

    public static synchronized void cleanDirty() {
        curProcessors.removeIf(AbstractProcessor::allowRecycle);
    }

    public static int getTaskCount() {
        return curProcessors.size();
    }
}
