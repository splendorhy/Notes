package com.splendor.notes.thread.reactor;

import com.splendor.notes.infrastructure.util.constant.assembly.ConsumerAssembly;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author splendor.s
 * @create 2022/9/27 19:57
 * @Description kafka异步消费处理实现类
 */
@Slf4j
@Service
public class ProcessServiceImpl implements ProcessService<HasResourceInfo> {

    @Override
    public void proccess(HasResourceInfo hasResource) {
        Integer doctorIdKey = hasResource.getDoctorId().intValue();
        try {
            //获取内存队列对象
            Processor processor = getRoutingQueue(doctorIdKey);
            //将任务报文加入到队列中
            processor.process(String.valueOf(hasResource.getDoctorId()) , hasResource , ConsumerAssembly.MSG_DELAY_INTERVAL , System.currentTimeMillis());
        } catch (Exception e) {
            log.error("========有号医生同步数据kafka消费放入内存队列出错", e);
        }
    }

    private Processor getRoutingQueue(Integer key) {
        ConsumerQueue consumerQueue = ConsumerQueue.getInstance();
        int h;
        int hash = (key == null) ? 0 : (h= key.hashCode()) ^ (h >>> 16);

        int index = (consumerQueue.queueSize() - 1) & hash;

        log.info("========>kafka异步消费路由内存队列，唯一id={},队列索引={}", key, index);

        return consumerQueue.getProcess(index);
    }
}
