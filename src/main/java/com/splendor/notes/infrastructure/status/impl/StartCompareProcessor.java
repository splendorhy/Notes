package com.splendor.notes.infrastructure.status.impl;

import com.splendor.notes.infrastructure.status.AbstractProcessor;
import com.splendor.notes.infrastructure.status.CompareCons;
import com.splendor.notes.infrastructure.status.CompareTaskPo;
import com.splendor.notes.infrastructure.status.Status;
import com.splendor.notes.infrastructure.status.service.BeanFactoryUtil;
import com.splendor.notes.infrastructure.status.service.CompareTaskMapper;
import lombok.extern.slf4j.Slf4j;


/**
 * @author splendor.s
 * @create 2022/11/29 上午11:31
 * @description 开启比对任务进行处理
 */

@Status(status = CompareCons.Status.START)
@Slf4j
public class StartCompareProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public StartCompareProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("开启比对任务进行处理:当前处理id为{}", value.getId());
        CompareTaskPo compareTaskPo = compareTaskMapper.selectById(value.getId());

       /*1检查数据正确性：对应的回放信息是否满足要求，如果不满足则直接中止比对任务*/

        return startCompareProcessorCheck(compareTaskPo);
    }


   /**
     * 检查数据正确性：对应的回放信息是否满足要求，如果不满足则直接中止比对任务
     * 如果没有问题，则认为已经成功
     *
     * @param compareTaskPo 比对任务信息
     * @return true-基本检查通过；false-检查不通过
     */

    private boolean startCompareProcessorCheck(CompareTaskPo compareTaskPo) {
        return true;
    }

    @Override
    public void end(CompareTaskPo value) {
        log.info("开启比对任务进行处理完成，待更新状态:当前处理id为{}", value.getId());
        try {

           /*更新状态为"降噪字段处理中"*/

            compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.NOISE_REDUCING);
        } catch (Exception e) {
            log.info("开启比对任务进行处理完成异常异常异常异常:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.NOISE_REDUCING);
        }

        log.info("开启比对任务进行处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.NOISE_REDUCING);
    }
}
