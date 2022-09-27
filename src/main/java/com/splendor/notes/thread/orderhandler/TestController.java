package com.splendor.notes.thread.orderhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Queue;
import java.util.UUID;

/**
 * @author splendor.s
 * @create 2022/9/25 上午10:23
 *
 * 主要是自己在项目中(中小型项目) 有支付下单业务(只是办理VIP，没有涉及到商品库存)，目前用户量还没有上来，目前没有出现问题，但是想到如果用户量变大，下单并发量变大，可能会出现一系列的问题，趁着空闲时间，做了这个demo测试相关问题。
 * 可能遇到的问题如下：
 *
 * 订单重复
 * 高并发下，性能变慢
 * 解决方式：ThreadPoolExecutor线程池 + Queue队列
 */
@RestController
public class TestController {

    @Resource
    private TestThreadPoolManager testThreadPoolManager;

    /**
     * 测试模拟下单请求 入口
     * @param id
     * @return
     */
    @GetMapping("/start/{id}")
    public String start(@PathVariable Long id) {
        //模拟的随机数
        String orderNo = System.currentTimeMillis() + UUID.randomUUID().toString();

        testThreadPoolManager.addOrders(orderNo);

        return "Test ThreadPoolExecutor start";
    }

    /**
     * 停止服务
     * @param id
     * @return
     */
    @GetMapping("/end/{id}")
    public String end(@PathVariable Long id) {

        testThreadPoolManager.shutdown();

        Queue q = testThreadPoolManager.getMsgQueue();
        System.out.println("关闭了线程服务，还有未处理的信息条数：" + q.size());
        return "Test ThreadPoolExecutor start";
    }
}
