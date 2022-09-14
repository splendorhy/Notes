package com.splendor.notes.design.patterns.template.thrift;

import com.splendor.notes.NotesApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/9/14 下午9:12
 * @description 测试按页获取远程数据
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NotesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ThriftInvokeCommandTest {

    @Autowired
    private MoviesInfoThriftService moviesInfoThriftService;

    @Test
    public void testGetMoviesByCondition() {
        log.info("测试分页获取数据：");
        MoviesConditions moviesConditions1 = MoviesConditions.builder()
                .pageNo(1)
                .pageSize(2)
                .build();
        List movieBasicInfos1 = new GetMoviesByConditionPage(moviesConditions1, moviesInfoThriftService)
                .setPageSize(5)
                .setSleepMs(100)
                .getResult();

        log.info("按实际条件返回的基本结果为：{}", movieBasicInfos1);

        log.info("测试滚动获取数据：");
        MoviesConditions moviesConditions2 = MoviesConditions.builder()
                .pageNo(1)
                .pageSize(2)
                .scrollId(0L)
                .build();
        List<MovieBasicInfo> movieBasicInfos2 = new GetMoviesByConditionScroll(moviesConditions2, moviesInfoThriftService)
                .setPageSize(5)
                .setSleepMs(100)
                .getResult();

        log.info("按实际条件返回的基本结果为：{}", movieBasicInfos2);
    }

}
