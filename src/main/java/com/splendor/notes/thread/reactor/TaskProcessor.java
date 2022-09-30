package com.splendor.notes.thread.reactor;

import java.util.List;

/**
 * @Author splendor.s
 * @create 2022/9/27 19:28
 */
public interface TaskProcessor<T> {

    enum ProcessingResult {
        Success, Congestion, TransientError, PermanentError
    }

    /**
     * 处理单个消费数据
     *
     * @param task
     * @return
     */
    ProcessingResult process(T task);

    /**
     * 处理批量消费数据
     *
     * @param inquiryOrders
     * @return
     */
    public ProcessingResult process(List<T> inquiryOrders);

}
