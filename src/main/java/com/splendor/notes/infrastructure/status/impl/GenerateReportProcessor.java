/*
package com.splendor.notes.infrastructure.status.impl;

import com.splendor.notes.infrastructure.status.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

*/
/**
 * @author splendor.s
 * @create 2022/11/29 上午11:39
 * @description 核对数据生成最终报告处理
 * 主要功能：结合前面处理生成的数据进行最终报告的比对任务生成报告，具体处理流程如下：
 *//*

@Status(status = CompareCons.Status.GENERATE_REPORTING)
@Slf4j
public class GenerateReportProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public GenerateReportProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("开始核对数据生成最终报告处理:当前处理id为{}", value.getId());
        CompareTaskPo compareTaskPo = compareTaskMapper.selectById(value.getId());
        if (Objects.isNull(compareTaskPo)) {
            log.error("开始核对数据生成最终报告处理异常:比对任务{}并不存在，请进行核对！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED,
                    CompareCons.Status.NOISE_REDUCING, "比对任务并不存在！");
            return false;
        }
        */
/*1.根据回放任务id来查看对应回放记录中的数据信息*//*

        String compareBizResultPath = compareTaskPo.getCompareResult();
        String noiseResultPath = compareTaskPo.getNoiseResult();
        */
/*2.检查回放记录中master文件和dev文件对应的条数是否一致*//*

        Long compareBizResultLines = null;
        Long noiseResultLines = null;
        try {
            compareBizResultLines = Files.lines(Paths.get(compareBizResultPath)).count();
            noiseResultLines = Files.lines(Paths.get(noiseResultPath)).count();
        } catch (Exception e) {
            log.error("比对任务{}对应核对数据生成最终报告读取文件异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.GENERATE_REPORTING, "对应核对数据生成最终报告读取文件异常");
            return false;
        }
        if (!Objects.equals(compareBizResultLines, noiseResultLines)) {
            log.error("比对任务{}对应核对数据生成最终报告相关文件数据条数并不一致！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.GENERATE_REPORTING, "对应核对数据生成最终报告相关文件数据条数并不一致");
            return false;
        }

        */
/*3.文件各行进行数据对比并进行记录*//*

        try {
            String compareBizFile = "/Users/yanfengzhang/Downloads/" + value.getCompareTaskName() + "_" + value.getId() + "_最终结果报告.txt";
            for (int i = 1; i < compareBizResultLines + 1; i++) {
                String compareBizResultStr = FileUtils.readAppointedLineNumber(compareBizResultPath, i);
                String noiseResultStr = FileUtils.readAppointedLineNumber(noiseResultPath, i);
                List<CompareDataMeta> compareDataMetas = CompareDataResult.getCompareDataResult(noiseResultStr, compareBizResultStr);
                FileUtils.writeContent(compareBizFile, JSON.toJSONString(compareDataMetas));
            }
            compareTaskMapper.updateNoiseResult(value.getId(), compareBizFile);
        } catch (Exception e) {
            log.error("比对任务{}核对数据生成最终报告数据处理异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.GENERATE_REPORTING, "核对数据生成最终报告数据处理异常");
            return false;
        }

        */
/*4.执行完毕无异常，进行状态变更*//*

        return true;
    }

    @Override
    public void end(CompareTaskPo value) {
        log.info("开始核对数据生成最终报告处理完成，待更新状态:当前处理id为{}", value.getId());
        */
/*更新状态为"核对数据生成最终报告处理完成"*//*

        compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.GENERATE_REPORTED);
        log.info("开始核对数据生成最终报告处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.GENERATE_REPORTED);
    }
}
*/
