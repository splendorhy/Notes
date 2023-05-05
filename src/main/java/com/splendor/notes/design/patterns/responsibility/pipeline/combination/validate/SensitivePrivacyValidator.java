package com.splendor.notes.design.patterns.responsibility.pipeline.combination.validate;

import com.google.common.collect.Lists;
import com.splendor.notes.design.patterns.responsibility.pipeline.ContextHandler;
import com.splendor.notes.design.patterns.responsibility.pipeline.enums.SensitiveValidate;
import com.splendor.notes.design.patterns.responsibility.pipeline.model.ContentCleanResContext;
import com.splendor.notes.design.patterns.responsibility.pipeline.model.SensitiveWord;
import com.splendor.notes.design.patterns.responsibility.pipeline.constant.*;
import com.splendor.notes.design.patterns.responsibility.pipeline.model.SensitveHitContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/11/28 下午2:42
 * @description
 */
@Component
@SensitiveValidate(validateCode = SensitiveCons.Validate.PRIVACY)
public class SensitivePrivacyValidator implements ContextHandler<ContentCleanResContext, SensitveHitContext> {

    /**
     * 敏感词分析处理：手机号身份证号处理
     *
     * @param context 处理时的上下文数据
     * @return
     */
    @Override
    public SensitveHitContext handle(ContentCleanResContext context) {
        return SensitveHitContext.builder()
                .content(context.getContent())
                .cleanContent(context.getCleanContent())
                .contentAttr(context.getContentAttr())
                .hitWords(getPrivacy(context.getCleanContent())).build();
    }

    private List<SensitiveWord> getPrivacy(String content) {
        /*模拟*/
        List<SensitiveWord> sensitiveWords = Lists.newArrayList();
        if (content.contains("18252066688")) {
            sensitiveWords.add(SensitiveWord.builder()
                    .sensitive("18252066688")
                    .sensitiveId(453L)
                    .kind(18).build());
        }
        return sensitiveWords;
    }
}
