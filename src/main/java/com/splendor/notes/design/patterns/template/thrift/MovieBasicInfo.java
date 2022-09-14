package com.splendor.notes.design.patterns.template.thrift;

import lombok.Builder;
import lombok.Data;

/**
 * @author splendor.s
 * @create 2022/9/14 下午8:33
 */
@Data
@Builder
public class MovieBasicInfo {
    /**
     * ID编号
     */
    private Long id;
    /**
     * 电影名称
     */
    private String name;
    /**
     * 电影简介
     */
    private String desc;
    /**
     * 电影导演
     */
    private String director;
    /**
     * 电影演员
     */
    private String actor;
    /**
     * 电影综合评分
     */
    private Integer score;
    /**
     * 电影时长
     */
    private Integer duration;
    /**
     * 上映年份
     */
    private int year;
}
