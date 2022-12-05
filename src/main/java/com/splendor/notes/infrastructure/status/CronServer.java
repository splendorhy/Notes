/*
package com.splendor.notes.infrastructure.status;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

*/
/**
 * @author splendor.s
 * @create 2022/11/29 上午11:42
 * @description 定时任务定义
 *
 * 定时任务：定时读取数据库中比对数据需要处理的task任务，并分发到响应的processor处理。
 *
 * 针对以上的内容，我们内部维护一个基本的定时器来完成实际的业务自动化流转处理，主要代码和业务处理如下：
 *//*

@Component
@DependsOn("beanFactoryUtil")
public class CronServer implements InitializingBean {

    @Autowired
    private CompareTaskMapper compareTaskMapper;

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final Logger LOGGER = LoggerFactory.getLogger(CronServer.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        SCHEDULED_EXECUTOR_SERVICE.scheduleWithFixedDelay(new CompareCronTask(), 20, 3, TimeUnit.SECONDS);
    }

    class CompareCronTask implements Runnable {
        @Override
        public void run() {
            if (BsapCompareSwitch.cronServerPause()) {
                LOGGER.warn("--------------cron server pause--------------");
                return;
            }
            int taskCount = EventDispatcher.getTaskCount();
            */
/*清理已经完成的任务*//*

            EventDispatcher.cleanDirty();
            LOGGER.warn("[--------当前正在运行的任务数量为:{}-------]", EventDispatcher.getTaskCount());
            if (taskCount != 0 && EventDispatcher.getTaskCount() == 0) {
                LOGGER.warn("[------------------------任务数量存在问题，主动进行gc处理中---------------------------]");
                System.gc();
            }
            int curSecond = (int) (System.currentTimeMillis() / 1000);
            try {
                List<CompareTaskPo> compareTaskPos = compareTaskMapper.selectCompareTaskPoByTimeRange(curSecond - 20);
                if (CollectionUtils.isEmpty(compareTaskPos)) {
                    return;
                }
                for (CompareTaskPo compareTaskPo : compareTaskPos) {
                    */
/*如果处理的内容不在我们规定的范围时直接跳出*//*

                    if (!EventDispatcher.containsStatus(compareTaskPo.getStatus())) {
                        continue;
                    }
                    */
/**
                     * 思考：
                     * 尝试更新一下last_ping_update的时间，更新成功代表抢锁成功，然后执行任务。
                     * 如果更新成功但是执行失败，待后续CronServer运行时再次尝试。
                     * 每台服务器每次定时任务只运行一个任务，防止同一台服务器抢占多个任务导致压力过大、负载不均衡的问题。
                     * （由于目前任务运行周期在多台服务器是一致的，所以极端情况下可能会出现任务被一台机器抢占的情况，
                     * 后续可以考虑使不同机器的运行周期随机或者引入分布式任务分配（负载均衡）策略）
                     *//*

                    if (compareTaskMapper.updateLastPingTimeByVersion(compareTaskPo.getId(), curSecond - 15, compareTaskPo.getVersion()) > 0) {
                        compareTaskPo.setVersion(compareTaskPo.getVersion() + 1);
                        compareTaskPo.setLastPingTime(curSecond - 15);
                        if (EventDispatcher.dispatch(compareTaskPo.getStatus(), compareTaskPo)) {
                            LOGGER.warn("CronServer 提交一个任务，任务id为{}, 任务详细信息:{}", compareTaskPo.getId(), JSON.toJSON(compareTaskPo));
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("server cron run catch an exception:", e);
            }
        }
    }
}*/
