package com.splendor.notes.design.patterns.responsibility.base;

import com.splendor.notes.NotesApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author splendor.s
 * @create 2022/9/14 下午9:48
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NotesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivityBizServiceTest {

    @Autowired
    private ActivityBizService activityBizService;

    @Test
    public void testActivityBizService() {
        ActivityDto activityDto = ActivityDto.builder()
                .activityName("splendor活动有敏感词")
                .activityType(1)
                .status(1)
                .activityConfig("splendor活动配置中有敏感词")
                .activityPrice(BigDecimal.valueOf(120))
                .startTime("2022-09-30 12:00:00")
                .endTime("2022-09-30 16:00:00").build();
        log.info("=======对活动常规性校验处理结果开始=======");
        List<String> checkRes = activityBizService.check(activityDto);
        if (CollectionUtils.isEmpty(checkRes)) {
            log.info("校验处理结果完成：该活动符合要求！");
            return;
        }
        log.info("校验处理结果完成，该活动不符合要求的主要原因如下：");
        checkRes.forEach(res -> {
            log.info("{}", res);
        });
    }
}
