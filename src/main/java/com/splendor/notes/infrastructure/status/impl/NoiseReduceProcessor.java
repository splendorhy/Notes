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
 * @create 2022/11/29 上午11:32
 * @description 降噪字段处理任务处理
 *
 * 主要功能：假设我们通过比对两次master处理回放来分析得出一些噪声处理信息，比对处理噪声的主要代码如下：
 */

@Status(status = CompareCons.Status.NOISE_REDUCING)
@Slf4j
public class NoiseReduceProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public NoiseReduceProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("降噪字段处理任务处理:当前处理id为{}", value.getId());

       /*1.根据回放任务id来查看对应回放记录中的数据信息*/

        CompareTaskPo compareTaskPo = compareTaskMapper.selectById(value.getId());
        if (Objects.isNull(compareTaskPo)) {
            log.error("降噪字段处理任务处理异常:比对任务{}并不存在，请进行核对！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED,
                    CompareCons.Status.NOISE_REDUCING, "比对任务并不存在！");
            return false;
        }

        ReplayTaskApplicationService replayTaskApplicationService = BeanFactoryUtil.getBean(ReplayTaskApplicationService.class);
        ReplayDataResultValue replayDataResultValue = replayTaskApplicationService.getBdfPathListByReplayTaskId(compareTaskPo.getReplayTaskId());
        if (Objects.isNull(replayDataResultValue) || StringUtils.isBlank(replayDataResultValue.getMasterFirstBdfPath())
                || StringUtils.isBlank(replayDataResultValue.getMasterSecondBdfPath())) {
            log.error("降噪字段处理任务处理异常:比对任务{}对应回放记录id相关数据文件数据并不存在，请进行核对！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED,
                    CompareCons.Status.NOISE_REDUCING, "对应回放记录id相关数据文件数据并不存在或不完整！");
            return false;
        }

//        String masterFirstBdfPath = "/Users/yanfengzhang/Downloads/replay_data_10.dat.mfbdf.rpresult";
//        String masterSecondBdfPath = "/Users/yanfengzhang/Downloads/replay_data_10.dat.msbdf.rpresult";
        String masterFirstBdfPath = replayDataResultValue.getMasterFirstBdfPath();
        String masterSecondBdfPath = replayDataResultValue.getMasterSecondBdfPath();

      /*2.检查回放记录中两次master文件对应的条数是否一致*/

        Long masterFirstBdfLines = null;
        Long masterSecondBdfLines = null;
        try {
            masterFirstBdfLines = Files.lines(Paths.get(masterFirstBdfPath)).count();
            masterSecondBdfLines = Files.lines(Paths.get(masterSecondBdfPath)).count();
        } catch (Exception e) {
            log.error("降噪字段处理任务处理异常:比对任务{}对应回放记录中两次master回放文件读取异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.NOISE_REDUCING, "两次master回放文件读取异常");
            return false;
        }
        if (!Objects.equals(masterFirstBdfLines, masterSecondBdfLines)) {

            log.error("降噪字段处理任务处理异常：比对任务{}对应回放记录中两次master文件数据条数并不一致！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.NOISE_REDUCING, "两次master文件数据条数并不一致");
            return false;
        }


        /*3.文件各行进行数据对比并进行记录*/
        try {
            String compareMasterFile = "/Users/splendors.s/Downloads/" + value.getCompareTaskName() + "_" + value.getId() + "_降噪比对数据.txt";
            for (int i = 1; i < masterFirstBdfLines + 1; i++) {
                /*String masterFirstBdfStr = FileUtils.readAppointedLineNumber(masterFirstBdfPath, i);
                String masterSecondBdfStr = FileUtils.readAppointedLineNumber(masterSecondBdfPath, i);
                JsonNode diffInfo = JsonDealUtils.getCompareJsonResult(masterFirstBdfStr, masterSecondBdfStr);
                FileUtils.writeContent(compareMasterFile, diffInfo.toString());
*/
            }
            compareTaskMapper.updateNoiseResult(value.getId(), compareMasterFile);
        } catch (Exception e) {
            log.error("降噪字段处理任务处理异常：比对任务{}生成噪声数据异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.NOISE_REDUCING, "生成噪声数据异常");
            return false;
        }


       /*4.执行完毕无异常，进行状态变更*/

        return true;
    }


    @Override
    public void end(CompareTaskPo value) {
        log.info("降噪字段处理任务处理完成，待更新状态:当前处理id为{}", value.getId());

       /*更新状态为"降噪字段处理完成"*/

        compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.NOISE_REDUCED);
        log.info("降噪字段处理任务处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.NOISE_REDUCED);
    }
}
