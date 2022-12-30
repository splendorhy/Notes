package com.splendor.notes.infrastructure.status.service;

import java.util.List;

/**
 * @author splendor.s
 * @create 2022/11/29 下午1:41
 * @description
 */
public interface CompareTaskMapper {

    void updateLastPingTime(int value, int curTime);

    /**
     * 通过最后更新拿锁
     * @param Id
     * @param curSecond
     * @param version
     * @return
     */
    int updateLastPingTimeByVersion(int Id, int curSecond, int version);


    /**
     * 通过时间范围获取任务
     * @param curSecond
     * @return
     */
    List<CompareTaskPo> selectCompareTaskPoByTimeRange(int curSecond);

    /**
     * 更新任务状态
     * @param valueId
     * @param status
     */
    void updateStatus(Integer valueId, int status);

    /**
     * 更新比对结果
     * @param valueId
     * @param fileUrl
     */
    void updateCompareResult(Integer valueId, String fileUrl);

    /**
     * 更新任务ID获取任务实体
     * @param id
     * @return
     */
    CompareTaskPo selectById(Integer id);

    /**
     * 更新任务状态或失败
     * @param id
     * @param status
     * @param modStatus
     * @param desc
     */
     void updateStatusAndFailure(int id, int status, int modStatus, String desc);

    /**
     * 更新降噪结果
     * @param id
     * @param fileUrl
     */
     void updateNoiseResult(int id, String fileUrl);

}
