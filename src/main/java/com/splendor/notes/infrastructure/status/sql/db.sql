CREATE TABLE `compare_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `compare_task_name` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '比对任务名称',
  `replay_task_id` bigint(20) unsigned DEFAULT NULL COMMENT '回放任务ID',
  `status` int(10) unsigned DEFAULT NULL COMMENT '比对状态：-1-取消执行，0-任务创建；1-任务启动，2-降噪字段处理中，3-降噪字段处理完成，4-业务数据比对处理中-比对成功，5-业务数据比对处理完成，6-核对数据生成最终报告处理中,7-核对数据生成最终报告处理完成,8-比对失败',
  `failure_position` int(10) unsigned DEFAULT NULL COMMENT '中间失败停留状态记录',
  `failure_reason` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '失败原因',
  `noise_result` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '噪声数据结果记录',
  `compare_result` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务结果比对结果记录',
  `final_result` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最终报告记录',
  `valid` int(11) DEFAULT '0' COMMENT ' 0当前在线 1已删除',
  `last_ping_time` int(11) NOT NULL DEFAULT '0' COMMENT '执行节点最后一次心跳时间',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本',
  `cname` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `uname` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人',
  `ctime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `utime` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='比对任务'