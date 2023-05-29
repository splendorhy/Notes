package com.splendor.notes.infrastructure.status;

/**
 * @author splendor.s
 * @create 2022/11/29 上午11:22
 * @description 状态转换常量  比对相关常量
 */
public class CompareCons {

    /**
     * 比对基本状态信息
     */
    public static class Status {
        /**
         * 比对任务取消执行
         */
        public static final int CANCEL = -1;
        /**
         * 比对任务创建
         */
        public static final int CREATE = 0;
        /**
         * 比对任务启动
         */
        public static final int START = 1;
        /**
         * 降噪字段处理中
         */
        public static final int NOISE_REDUCING = 2;
        /**
         * 降噪字段处理完成
         */
        public static final int NOISE_REDUCED = 3;
        /**
         * 业务数据比对处理中
         */
        public static final int BIZ_COMPARING = 4;
        /**
         * 业务数据比对处理完成
         */
        public static final int BIZ_COMPARED = 5;

        /**
         * 核对数据生成最终报告处理中
         */
        public static final int GENERATE_REPORTING = 6;
        /**
         * 核对数据生成最终报告处理完成
         */
        public static final int GENERATE_REPORTED = 7;
        /**
         * 比对失败
         */
        public static final int FAILED = 8;
    }


}
