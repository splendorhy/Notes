package com.splendor.notes.design.patterns.template.thrift;

import lombok.extern.slf4j.Slf4j;

/**
 * @author splendor.s
 * @create 2022/9/14 下午8:49
 * @description 用于利用分页形式滚动调用thrift
 */
@Slf4j
public abstract class ThriftInvokePageCommand <TC, TR, FR> extends ThriftInvokeCommand<TC, TR, FR> {

    private int pageNo = 1;

    public ThriftInvokePageCommand(TC command) {
        super(command);
    }

    @Override
    public void preHandle() {
        /*command 设置 pageNo*/
        setPageNo(command, pageNo);
    }

    @Override
    public void afterHandle(TR thriftResult) {
        /*页数加 1*/
        pageNo++;
    }

    /**
     * command 设置 pageNo
     */
    protected abstract void setPageNo(TC command, int pageNo);

}
