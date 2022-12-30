package com.splendor.notes.infrastructure.status.impl;

import com.splendor.notes.infrastructure.status.*;
import com.splendor.notes.infrastructure.status.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;


/**
 * @author splendor.s
 * @create 2022/11/29 上午11:37
 * @description 业务数据比对处理
 * 主要功能：对本次业务代码改动和master代码进行对比来分析对应的内容处理变化统计，具体代码如下：
 */

@Status(status = CompareCons.Status.BIZ_COMPARING)
@Slf4j
public class BizCompareProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public BizCompareProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("开启业务数据比对处理处理:当前处理id为{}", value.getId());
        CompareTaskPo compareTaskPo = compareTaskMapper.selectById(value.getId());

        /*1.根据回放任务id来查看对应回放记录中的数据信息*/

        if (Objects.isNull(compareTaskPo)) {
            log.error("开启业务数据比对处理处理异常:比对任务{}并不存在，请进行核对！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED,
                    CompareCons.Status.NOISE_REDUCING, "比对任务并不存在！");
            return false;
        }

        ReplayTaskApplicationService replayTaskApplicationService = BeanFactoryUtil.getBean(ReplayTaskApplicationService.class);
        ReplayDataResultValue replayDataResultValue = replayTaskApplicationService.getBdfPathListByReplayTaskId(compareTaskPo.getReplayTaskId());
        if (Objects.isNull(replayDataResultValue) || StringUtils.isBlank(replayDataResultValue.getMasterFirstBdfPath())
                || StringUtils.isBlank(replayDataResultValue.getFeatureBdfPath())) {
            log.error("开启业务数据比对处理处理异常:比对任务{}对应回放记录id相关数据文件数据并不存在，请进行核对！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED,
                    CompareCons.Status.NOISE_REDUCING, "对应回放记录id相关数据文件数据并不存在或不完整！");
            return false;
        }

//        String masterFirstBdfPath = "/Users/yanfengzhang/Downloads/replay_data_10.dat.mfbdf.rpresult";
//        String featureBdfPath = "/Users/yanfengzhang/Downloads/replay_data_10.dat.fbdf.rpresult";
        String masterFirstBdfPath = replayDataResultValue.getMasterFirstBdfPath();
        String featureBdfPath = replayDataResultValue.getFeatureBdfPath();

        /*2.检查回放记录中master文件和dev文件对应的条数是否一致*/

        Long masterFirstBdfLines = null;
        Long featureBdfLines = null;
        try {
            masterFirstBdfLines = Files.lines(Paths.get(masterFirstBdfPath)).count();
            featureBdfLines = Files.lines(Paths.get(featureBdfPath)).count();
        } catch (Exception e) {
            log.error("比对任务{}对应回放记录中master回放文件或dev回放文件读取异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.BIZ_COMPARING, "master回放文件或dev回放文件读取异常");
            return false;
        }
        if (!Objects.equals(masterFirstBdfLines, featureBdfLines)) {
            log.error("比对任务{}对应回放记录中master回放文件和dev回放文件数据条数并不一致！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.BIZ_COMPARING, "master回放文件和dev回放文件数据条数并不一致");
            return false;
        }

        /*3.文件各行进行数据对比并进行记录*/
        try {
            String compareBizFile = "/Users/yanfengzhang/Downloads/" + value.getCompareTaskName() + "_" + value.getId() + "_业务比对数据.txt";
            for (int i = 1; i < masterFirstBdfLines + 1; i++) {
               /*
                String masterFirstBdfStr = FileUtils.readAppointedLineNumber(masterFirstBdfPath, i);
                String featureBdfStr = FileUtils.readAppointedLineNumber(featureBdfPath, i);
                JsonNode diffInfo = JsonDealUtils.getCompareJsonResult(masterFirstBdfStr, featureBdfStr);
                FileUtils.writeContent(compareBizFile, diffInfo.toString());
               */
            }
            compareTaskMapper.updateCompareResult(value.getId(), compareBizFile);
        } catch (Exception e) {
            log.error("比对任务{}生成业务比对数据异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.BIZ_COMPARING, "生成业务比对数据异常");
            return false;
        }

        /*4.执行完毕无异常，进行状态变更*/

        return true;
    }

    @Override
    public void end(CompareTaskPo value) {
        log.info("开启业务数据比对处理处理完成，待更新状态:当前处理id为{}", value.getId());
        /*更新状态为"业务数据比对处理完成"*/
        compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.BIZ_COMPARED);
        log.info("开启业务数据比对处理处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.BIZ_COMPARED);
    }
}
