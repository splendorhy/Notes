/*
package com.splendor.notes.infrastructure.status.impl;

import com.splendor.notes.infrastructure.status.AbstractProcessor;
import com.splendor.notes.infrastructure.status.CompareCons;
import com.splendor.notes.infrastructure.status.Status;
import lombok.extern.slf4j.Slf4j;

*/
/**
 * @author splendor.s
 * @create 2022/11/29 上午11:35
 * @description 开启业务数据比对处理
 * 主要功能：没有其他检查数据内容的话，可以直接进行状态转换，我这边暂时忽略检查！
 *//*

@Status(status = CompareCons.Status.NOISE_REDUCED)
@Slf4j
public class StartBizCompareProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public StartBizCompareProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("开启业务数据比对处理:当前处理id为{}", value.getId());
        */
/*该状态下当前不做任何处理，基本没有检查的相关启动条件*//*

        return true;
    }

    @Override
    public void end(CompareTaskPo value) {
        log.info("开启业务数据比对处理完成，待更新状态:当前处理id为{}", value.getId());
        */
/*更新状态为"业务数据比对处理中"*//*

        compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.BIZ_COMPARING);
        log.info("开启业务数据比对处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.BIZ_COMPARING);
    }
}
*/
