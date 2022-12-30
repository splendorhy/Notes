package com.splendor.notes.infrastructure.status.impl;

import com.splendor.notes.infrastructure.status.AbstractProcessor;
import com.splendor.notes.infrastructure.status.CompareCons;
import com.splendor.notes.infrastructure.status.service.CompareTaskPo;
import com.splendor.notes.infrastructure.status.Status;
import com.splendor.notes.infrastructure.status.service.BeanFactoryUtil;
import com.splendor.notes.infrastructure.status.service.CompareTaskMapper;
import lombok.extern.slf4j.Slf4j;


/**
 * @author splendor.s
 * @create 2022/11/29 上午11:38
 * @description 开始核对数据生成最终报告处理
 * 主要功能：没有其他检查数据内容的话，可以直接进行状态转换，我这边暂时忽略检查！
 */

@Status(status = CompareCons.Status.BIZ_COMPARED)
@Slf4j
public class StartGenerateReportProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public StartGenerateReportProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("开始核对数据生成最终报告处理:当前处理id为{}", value.getId());

        /*该状态下当前不做任何处理，基本没有检查的相关启动条件（检查相关文件是否存在）*/

        return true;
    }

    @Override
    public void end(CompareTaskPo value) {
        log.info("开始核对数据生成最终报告处理完成，待更新状态:当前处理id为{}", value.getId());

        /*更新状态为"核对数据生成最终报告处理中"*/

        compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.GENERATE_REPORTING);
        log.info("开始核对数据生成最终报告处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.GENERATE_REPORTING);
    }
}
