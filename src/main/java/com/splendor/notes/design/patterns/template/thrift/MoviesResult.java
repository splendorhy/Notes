package com.splendor.notes.design.patterns.template.thrift;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/9/14 下午8:35
 */
@Data
@Builder
public class MoviesResult {
    /**
     * 总数目
     */
    private long total;
    /**
     * 具体电影信息
     */
    public List<MovieInfo> movieInfos;
    /**
     * 电影ID集合
     */
    public List<Long> movieIdList;
    /**
     * 下一个滚动ID
     */
    private Long nextScrollId;
}
