package com.splendor.notes.design.patterns.responsibility.pipeline.combination.validate;

import com.google.common.collect.Lists;
import com.splendor.notes.design.patterns.responsibility.pipeline.ContextHandler;
import com.splendor.notes.design.patterns.responsibility.pipeline.enums.SensitiveValidate;
import com.splendor.notes.design.patterns.responsibility.pipeline.model.ContentCleanResContext;
import com.splendor.notes.design.patterns.responsibility.pipeline.model.SensitiveWord;
import com.splendor.notes.design.patterns.responsibility.pipeline.constant.*;
import com.splendor.notes.design.patterns.responsibility.pipeline.model.SensitveHitContext;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author splendor.s
 * @create 2022/11/28 下午2:44
 * @description 敏感词校验：相关词库处理
 */

@Component
@SensitiveValidate(validateCode = SensitiveCons.Validate.THESAURUS)
public class SensitiveThesaurusValidator implements ContextHandler<ContentCleanResContext, SensitveHitContext> {

    /**
     * 敏感词分析处理：根据相关业务配置进行相关词库校验匹配
     *
     * @param context 处理时的上下文数据
     * @return 处理结果（代进入敏感词生效处理）
     */
    @Override
    public SensitveHitContext handle(ContentCleanResContext context) {
        Integer bizType = context.getContentAttr().getBizType();
        /*根据业务方接入来源获取对应的业务方词库校验要求*/
        List<Integer> validatorModes = getBizSensitiveModes(bizType);
        if (CollectionUtils.isEmpty(validatorModes)) {
            /*没有配置则直接默认走企业词库校验*/
            validatorModes.add(1);
        }

        List<SensitiveWord> hitWords = Lists.newArrayList();
        for (Integer validatorMode : validatorModes) {
            if (validatorMode.equals(1)) {
                /*企业词库校验*/
                hitWords.addAll(companySensitive(context.getContent()));
            }
            if (validatorMode.equals(2)) {
                /*企业词库校验*/
                hitWords.addAll(departmentSensitive(context.getContent()));
            }
            if (validatorMode.equals(3)) {
                /*企业词库校验*/
                hitWords.addAll(otherSensitive(context.getContent()));
            }
        }

        return SensitveHitContext.builder()
                .content(context.getContent())
                .cleanContent(context.getCleanContent())
                .contentAttr(context.getContentAttr())
                .hitWords(hitWords).build();
    }


    /**
     * 模拟假设配置的都是所有词库都跑一遍
     *
     * @return 业务方词库配置
     */
    private List<Integer> getBizSensitiveModes(Integer bizType) {
        /**
         * 1-企业词库校验；2-部门词库校验；3-其他词库校验
         */
        return Arrays.asList(1, 2, 3);
    }

    /**
     * 企业敏感词库（编号1）匹配,实际为词库校验，此处只做模拟
     *
     * @param content 用户内容
     * @return 匹配结果
     */
    private List<SensitiveWord> companySensitive(String content) {
        List<SensitiveWord> sensitiveWords = Lists.newArrayList();
        if (content.contains("splendor")) {
            sensitiveWords.add(SensitiveWord.builder()
                    .sensitive("splendor")
                    .sensitiveId(1L)
                    .kind(1).build());
        }
        if (content.contains("splendor")) {
            sensitiveWords.add(SensitiveWord.builder()
                    .sensitive("splendor")
                    .sensitiveId(86L)
                    .kind(1).build());
        }
        return sensitiveWords;
    }

    /**
     * 部门敏感词库（编号2）匹配,实际为不同部门的词库校验，此处只做模拟
     *
     * @param content 用户内容
     * @return 匹配结果
     */
    private List<SensitiveWord> departmentSensitive(String content) {
        List<SensitiveWord> sensitiveWords = Lists.newArrayList();
        if (content.contains("妓院")) {
            sensitiveWords.add(SensitiveWord.builder()
                    .sensitive("妓院")
                    .sensitiveId(23L)
                    .kind(2).build());
        }
        if (content.contains("赌博")) {
            sensitiveWords.add(SensitiveWord.builder()
                    .sensitive("赌博")
                    .sensitiveId(36L)
                    .kind(2).build());
        }
        return sensitiveWords;
    }

    /**
     * 其他敏感词库（编号3）匹配,实际为不同第三方词库校验，此处只做模拟
     *
     * @param content 用户内容
     * @return 匹配结果
     */
    private List<SensitiveWord> otherSensitive(String content) {
        List<SensitiveWord> sensitiveWords = Lists.newArrayList();
        if (content.contains("禁药")) {
            sensitiveWords.add(SensitiveWord.builder()
                    .sensitive("禁药")
                    .sensitiveId(28L)
                    .kind(3).build());
        }
        if (content.contains("毒品")) {
            sensitiveWords.add(SensitiveWord.builder()
                    .sensitive("毒品")
                    .sensitiveId(376L)
                    .kind(3).build());
        }
        return sensitiveWords;
    }
}
