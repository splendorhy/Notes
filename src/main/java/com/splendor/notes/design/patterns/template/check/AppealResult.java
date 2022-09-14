package com.splendor.notes.design.patterns.template.check;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/9/14 下午8:22
 */
public class AppealResult {

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
     * 申诉项处理结果
     */
    public List<String> appealResults;

}
