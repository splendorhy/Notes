package com.splendor.notes.design.patterns.template.check;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/9/14 下午8:12
 *  * @description 申诉事项内容
 */
@Data
@Builder
public class AppealMatters {
    /**
     * 主键;主键
     */
    public Long id;
    /**
     * 申诉记录编号
     */
    public String code;
    /**
     * 申诉人
     */
    public String declarant;
    /**
     * 申诉类型
     */
    public Integer type;
    /**
     * 申诉项
     */
    public List<String> appealItems;
}
