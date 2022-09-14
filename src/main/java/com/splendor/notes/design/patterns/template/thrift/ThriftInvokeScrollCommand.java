package com.splendor.notes.design.patterns.template.thrift;

import lombok.extern.slf4j.Slf4j;

/**
 * @author splendor.s
 * @create 2022/9/14 下午8:46
 * @description 用于利用scroll形式滚动调用thrift
 */
@Slf4j
public abstract class ThriftInvokeScrollCommand<TC, TR, FR> extends ThriftInvokeCommand<TC, TR, FR> {

    private Long scrollId = 0L;

    public ThriftInvokeScrollCommand(TC command) {
        super(command);
    }

    @Override
    public void preHandle() {
        /*command 设置 ScrollId*/
        setScrollId(command, scrollId);
    }

    @Override
    public void afterHandle(TR thriftResult) {
        /*从 thriftResult 获取 scrollId*/
        scrollId = getNextScrollId(thriftResult);
    }

    /**
     * command 设置 ScrollId
     */
    protected abstract void setScrollId(TC command, Long scrollId);

    /**
     * 从 thriftResult 获取 scrollId
     */
    protected abstract Long getNextScrollId(TR thriftResult);

}
