package com.splendor.notes.design.patterns.template.check;

import lombok.Builder;
import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/9/13 下午9:55
 * @description 校验基本返回结构
 */
@Data
@Builder
public class CheckResponse {

    /**
     * 成功标志：true-校验通过，false-校验未通过
     */
    private boolean pass;
    /**
     * 校验码
     */
    private Integer code;
    /**
     * 未通过原因
     */
    private String errorMsg;

}
