package com.splendor.notes.design.patterns.combination.engine.service.engine;

import com.splendor.notes.design.patterns.combination.engine.service.logic.LogicFilter;
import com.splendor.notes.design.patterns.combination.engine.service.logic.impl.UserAgeFilter;
import com.splendor.notes.design.patterns.combination.engine.service.logic.impl.UserGenderFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author splendor.s
 * @create 2023/5/5 下午6:03
 * @description 决策节点配置定义
 */
public class EngineConfig {
    static Map<String, LogicFilter> logicFilterMap;

    static {
        logicFilterMap = new ConcurrentHashMap<>();
        logicFilterMap.put("userAge",new UserAgeFilter());
        logicFilterMap.put("userGender",new UserGenderFilter());
    }

    public Map<String, LogicFilter> getLogicFilterMap() {
        return logicFilterMap;
    }

    public void setLogicFilterMap(Map<String, LogicFilter> logicFilterMap) {
        EngineConfig.logicFilterMap = logicFilterMap;
    }
}

