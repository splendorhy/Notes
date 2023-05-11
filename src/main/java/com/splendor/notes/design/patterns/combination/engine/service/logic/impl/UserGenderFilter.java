package com.splendor.notes.design.patterns.combination.engine.service.logic.impl;

import com.splendor.notes.design.patterns.combination.engine.service.logic.BaseLogic;

import java.util.Map;

/**
 * @author splendor.s
 * @create 2023/5/5 下午6:06
 * @description 性别节点过滤
 */
public class UserGenderFilter extends BaseLogic {
    @Override
    public String matterValue(Long treeId, String userId, Map<String, String> decisionMatter) {
        return decisionMatter.get("gender");
    }
}


