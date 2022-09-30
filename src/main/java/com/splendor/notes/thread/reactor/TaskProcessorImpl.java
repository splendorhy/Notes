package com.splendor.notes.thread.reactor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author splendor.s
 * @create 2022/9/27 下午3:30
 */
@Slf4j
@Service
public class TaskProcessorImpl implements TaskProcessor<HasResourceInfo> {

    private volatile long lastNetworkErrorTime;
    private static final Pattern READ_TIME_OUT_PATTERN = Pattern.compile(".*read.*time.*out.*");

    /**
     * 单个消费Kafka数据实现处理
     * @param hasResourceInfo
     * @return
     */
    @Override
    public ProcessingResult process(HasResourceInfo hasResourceInfo) {
         return ProcessingResult.Success;
    }

    /**
     * 批量消费Kafka数据
     * @param inquiryOrders
     * @return
     */
    @Override
    public ProcessingResult process(List<HasResourceInfo> inquiryOrders) {
        return ProcessingResult.Success;
    }
}
