package com.splendor.notes.infrastructure.status.impl;

/**
 * @author splendor.s
 * @create 2022/11/29 上午11:31
 * @description 开启比对任务进行处理
 */
Status(status = CompareCons.Status.START)
@Slf4j
public class StartCompareProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public StartCompareProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("开启比对任务进行处理:当前处理id为{}", value.getId());
        CompareTaskPo compareTaskPo = compareTaskMapper.selectById(value.getId());
        /*1检查数据正确性：对应的回放信息是否满足要求，如果不满足则直接中止比对任务*/
        return startCompareProcessorCheck(compareTaskPo);
    }

    /**
     * 检查数据正确性：对应的回放信息是否满足要求，如果不满足则直接中止比对任务
     * 如果没有问题，则认为已经成功
     *
     * @param compareTaskPo 比对任务信息
     * @return true-基本检查通过；false-检查不通过
     */
    private boolean startCompareProcessorCheck(CompareTaskPo compareTaskPo) {
        return true;
    }

    @Override
    public void end(CompareTaskPo value) {
        log.info("开启比对任务进行处理完成，待更新状态:当前处理id为{}", value.getId());
        try {
            /*更新状态为"降噪字段处理中"*/
            compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.NOISE_REDUCING);
        } catch (Exception e) {
            log.info("开启比对任务进行处理完成异常异常异常异常:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.NOISE_REDUCING);
        }

        log.info("开启比对任务进行处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.NOISE_REDUCING);
    }
}
降噪字段处理任务处理
        主要功能：假设我们通过比对两次master处理回放来分析得出一些噪声处理信息，比对处理噪声的主要代码如下：

/**
 * @author yanfengzhang
 * @description 降噪字段处理任务处理
 * @date 2022/5/2  00:29
 */
@Status(status = CompareCons.Status.NOISE_REDUCING)
@Slf4j
public class NoiseReduceProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public NoiseReduceProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("降噪字段处理任务处理:当前处理id为{}", value.getId());
        /*1.根据回放任务id来查看对应回放记录中的数据信息*/
        CompareTaskPo compareTaskPo = compareTaskMapper.selectById(value.getId());
        if (Objects.isNull(compareTaskPo)) {
            log.error("降噪字段处理任务处理异常:比对任务{}并不存在，请进行核对！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED,
                    CompareCons.Status.NOISE_REDUCING, "比对任务并不存在！");
            return false;
        }

        ReplayTaskApplicationService replayTaskApplicationService = BeanFactoryUtil.getBean(ReplayTaskApplicationService.class);
        ReplayDataResultValue replayDataResultValue = replayTaskApplicationService.getBdfPathListByReplayTaskId(compareTaskPo.getReplayTaskId());
        if (Objects.isNull(replayDataResultValue) || StringUtils.isBlank(replayDataResultValue.getMasterFirstBdfPath())
                || StringUtils.isBlank(replayDataResultValue.getMasterSecondBdfPath())) {
            log.error("降噪字段处理任务处理异常:比对任务{}对应回放记录id相关数据文件数据并不存在，请进行核对！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED,
                    CompareCons.Status.NOISE_REDUCING, "对应回放记录id相关数据文件数据并不存在或不完整！");
            return false;
        }

//        String masterFirstBdfPath = "/Users/yanfengzhang/Downloads/replay_data_10.dat.mfbdf.rpresult";
//        String masterSecondBdfPath = "/Users/yanfengzhang/Downloads/replay_data_10.dat.msbdf.rpresult";
        String masterFirstBdfPath = replayDataResultValue.getMasterFirstBdfPath();
        String masterSecondBdfPath = replayDataResultValue.getMasterSecondBdfPath();
        /*2.检查回放记录中两次master文件对应的条数是否一致*/
        Long masterFirstBdfLines = null;
        Long masterSecondBdfLines = null;
        try {
            masterFirstBdfLines = Files.lines(Paths.get(masterFirstBdfPath)).count();
            masterSecondBdfLines = Files.lines(Paths.get(masterSecondBdfPath)).count();
        } catch (Exception e) {
            log.error("降噪字段处理任务处理异常:比对任务{}对应回放记录中两次master回放文件读取异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.NOISE_REDUCING, "两次master回放文件读取异常");
            return false;
        }
        if (!Objects.equals(masterFirstBdfLines, masterSecondBdfLines)) {
            log.error("降噪字段处理任务处理异常：比对任务{}对应回放记录中两次master文件数据条数并不一致！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.NOISE_REDUCING, "两次master文件数据条数并不一致");
            return false;
        }

        /*3.文件各行进行数据对比并进行记录*/
        try {
            String compareMasterFile = "/Users/yanfengzhang/Downloads/" + value.getCompareTaskName() + "_" + value.getId() + "_降噪比对数据.txt";
            for (int i = 1; i < masterFirstBdfLines + 1; i++) {
                String masterFirstBdfStr = FileUtils.readAppointedLineNumber(masterFirstBdfPath, i);
                String masterSecondBdfStr = FileUtils.readAppointedLineNumber(masterSecondBdfPath, i);
                JsonNode diffInfo = JsonDealUtils.getCompareJsonResult(masterFirstBdfStr, masterSecondBdfStr);
                FileUtils.writeContent(compareMasterFile, diffInfo.toString());
            }
            compareTaskMapper.updateNoiseResult(value.getId(), compareMasterFile);
        } catch (Exception e) {
            log.error("降噪字段处理任务处理异常：比对任务{}生成噪声数据异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.NOISE_REDUCING, "生成噪声数据异常");
            return false;
        }

        /*4.执行完毕无异常，进行状态变更*/
        return true;
    }

    @Override
    public void end(CompareTaskPo value) {
        log.info("降噪字段处理任务处理完成，待更新状态:当前处理id为{}", value.getId());
        /*更新状态为"降噪字段处理完成"*/
        compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.NOISE_REDUCED);
        log.info("降噪字段处理任务处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.NOISE_REDUCED);
    }
}

开启业务数据比对处理
        主要功能：没有其他检查数据内容的话，可以直接进行状态转换，我这边暂时忽略检查！

/**
 * @author yanfengzhang
 * @description 开启业务数据比对处理
 * @date 2022/5/2  00:36
 */
@Status(status = CompareCons.Status.NOISE_REDUCED)
@Slf4j
public class StartBizCompareProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public StartBizCompareProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("开启业务数据比对处理:当前处理id为{}", value.getId());
        /*该状态下当前不做任何处理，基本没有检查的相关启动条件*/
        return true;
    }

    @Override
    public void end(CompareTaskPo value) {
        log.info("开启业务数据比对处理完成，待更新状态:当前处理id为{}", value.getId());
        /*更新状态为"业务数据比对处理中"*/
        compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.BIZ_COMPARING);
        log.info("开启业务数据比对处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.BIZ_COMPARING);
    }
}

业务数据比对处理
        主要功能：对本次业务代码改动和master代码进行对比来分析对应的内容处理变化统计，具体代码如下：

/**
 * @author yanfengzhang
 * @description 业务数据比对处理
 * @date 2022/5/2  00:53
 */
@Status(status = CompareCons.Status.BIZ_COMPARING)
@Slf4j
public class BizCompareProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public BizCompareProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("开启业务数据比对处理处理:当前处理id为{}", value.getId());
        CompareTaskPo compareTaskPo = compareTaskMapper.selectById(value.getId());
        /*1.根据回放任务id来查看对应回放记录中的数据信息*/
        if (Objects.isNull(compareTaskPo)) {
            log.error("开启业务数据比对处理处理异常:比对任务{}并不存在，请进行核对！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED,
                    CompareCons.Status.NOISE_REDUCING, "比对任务并不存在！");
            return false;
        }

        ReplayTaskApplicationService replayTaskApplicationService = BeanFactoryUtil.getBean(ReplayTaskApplicationService.class);
        ReplayDataResultValue replayDataResultValue = replayTaskApplicationService.getBdfPathListByReplayTaskId(compareTaskPo.getReplayTaskId());
        if (Objects.isNull(replayDataResultValue) || StringUtils.isBlank(replayDataResultValue.getMasterFirstBdfPath())
                || StringUtils.isBlank(replayDataResultValue.getFeatureBdfPath())) {
            log.error("开启业务数据比对处理处理异常:比对任务{}对应回放记录id相关数据文件数据并不存在，请进行核对！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED,
                    CompareCons.Status.NOISE_REDUCING, "对应回放记录id相关数据文件数据并不存在或不完整！");
            return false;
        }

//        String masterFirstBdfPath = "/Users/yanfengzhang/Downloads/replay_data_10.dat.mfbdf.rpresult";
//        String featureBdfPath = "/Users/yanfengzhang/Downloads/replay_data_10.dat.fbdf.rpresult";
        String masterFirstBdfPath = replayDataResultValue.getMasterFirstBdfPath();
        String featureBdfPath = replayDataResultValue.getFeatureBdfPath();
        /*2.检查回放记录中master文件和dev文件对应的条数是否一致*/
        Long masterFirstBdfLines = null;
        Long featureBdfLines = null;
        try {
            masterFirstBdfLines = Files.lines(Paths.get(masterFirstBdfPath)).count();
            featureBdfLines = Files.lines(Paths.get(featureBdfPath)).count();
        } catch (Exception e) {
            log.error("比对任务{}对应回放记录中master回放文件或dev回放文件读取异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.BIZ_COMPARING, "master回放文件或dev回放文件读取异常");
            return false;
        }
        if (!Objects.equals(masterFirstBdfLines, featureBdfLines)) {
            log.error("比对任务{}对应回放记录中master回放文件和dev回放文件数据条数并不一致！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.BIZ_COMPARING, "master回放文件和dev回放文件数据条数并不一致");
            return false;
        }

        /*3.文件各行进行数据对比并进行记录*/
        try {
            String compareBizFile = "/Users/yanfengzhang/Downloads/" + value.getCompareTaskName() + "_" + value.getId() + "_业务比对数据.txt";
            for (int i = 1; i < masterFirstBdfLines + 1; i++) {
                String masterFirstBdfStr = FileUtils.readAppointedLineNumber(masterFirstBdfPath, i);
                String featureBdfStr = FileUtils.readAppointedLineNumber(featureBdfPath, i);
                JsonNode diffInfo = JsonDealUtils.getCompareJsonResult(masterFirstBdfStr, featureBdfStr);
                FileUtils.writeContent(compareBizFile, diffInfo.toString());
            }
            compareTaskMapper.updateCompareResult(value.getId(), compareBizFile);
        } catch (Exception e) {
            log.error("比对任务{}生成业务比对数据异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.BIZ_COMPARING, "生成业务比对数据异常");
            return false;
        }

        /*4.执行完毕无异常，进行状态变更*/
        return true;
    }

    @Override
    public void end(CompareTaskPo value) {
        log.info("开启业务数据比对处理处理完成，待更新状态:当前处理id为{}", value.getId());
        /*更新状态为"业务数据比对处理完成"*/
        compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.BIZ_COMPARED);
        log.info("开启业务数据比对处理处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.BIZ_COMPARED);
    }
}

开始核对数据生成最终报告处理
        主要功能：没有其他检查数据内容的话，可以直接进行状态转换，我这边暂时忽略检查！

/**
 * @author yanfengzhang
 * @description 开始核对数据生成最终报告处理
 * @date 2022/5/2  00:59
 */
@Status(status = CompareCons.Status.BIZ_COMPARED)
@Slf4j
public class StartGenerateReportProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public StartGenerateReportProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("开始核对数据生成最终报告处理:当前处理id为{}", value.getId());
        /*该状态下当前不做任何处理，基本没有检查的相关启动条件（检查相关文件是否存在）*/
        return true;
    }

    @Override
    public void end(CompareTaskPo value) {
        log.info("开始核对数据生成最终报告处理完成，待更新状态:当前处理id为{}", value.getId());
        /*更新状态为"核对数据生成最终报告处理中"*/
        compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.GENERATE_REPORTING);
        log.info("开始核对数据生成最终报告处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.GENERATE_REPORTING);
    }
}

核对数据生成最终报告处理
        主要功能：结合前面处理生成的数据进行最终报告的比对任务生成报告，具体处理流程如下：

/**
 * @author yanfengzhang
 * @description 核对数据生成最终报告处理
 * @date 2022/5/2  01:20
 */
@Status(status = CompareCons.Status.GENERATE_REPORTING)
@Slf4j
public class GenerateReportProcessor extends AbstractProcessor {

    private CompareTaskMapper compareTaskMapper;

    public GenerateReportProcessor(CompareTaskPo value) {
        super(value);
        compareTaskMapper = BeanFactoryUtil.getBean(CompareTaskMapper.class);
    }

    @Override
    public boolean actualProcess(CompareTaskPo value) {
        log.info("开始核对数据生成最终报告处理:当前处理id为{}", value.getId());
        CompareTaskPo compareTaskPo = compareTaskMapper.selectById(value.getId());
        if (Objects.isNull(compareTaskPo)) {
            log.error("开始核对数据生成最终报告处理异常:比对任务{}并不存在，请进行核对！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED,
                    CompareCons.Status.NOISE_REDUCING, "比对任务并不存在！");
            return false;
        }
        /*1.根据回放任务id来查看对应回放记录中的数据信息*/
        String compareBizResultPath = compareTaskPo.getCompareResult();
        String noiseResultPath = compareTaskPo.getNoiseResult();
        /*2.检查回放记录中master文件和dev文件对应的条数是否一致*/
        Long compareBizResultLines = null;
        Long noiseResultLines = null;
        try {
            compareBizResultLines = Files.lines(Paths.get(compareBizResultPath)).count();
            noiseResultLines = Files.lines(Paths.get(noiseResultPath)).count();
        } catch (Exception e) {
            log.error("比对任务{}对应核对数据生成最终报告读取文件异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.GENERATE_REPORTING, "对应核对数据生成最终报告读取文件异常");
            return false;
        }
        if (!Objects.equals(compareBizResultLines, noiseResultLines)) {
            log.error("比对任务{}对应核对数据生成最终报告相关文件数据条数并不一致！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.GENERATE_REPORTING, "对应核对数据生成最终报告相关文件数据条数并不一致");
            return false;
        }

        /*3.文件各行进行数据对比并进行记录*/
        try {
            String compareBizFile = "/Users/yanfengzhang/Downloads/" + value.getCompareTaskName() + "_" + value.getId() + "_最终结果报告.txt";
            for (int i = 1; i < compareBizResultLines + 1; i++) {
                String compareBizResultStr = FileUtils.readAppointedLineNumber(compareBizResultPath, i);
                String noiseResultStr = FileUtils.readAppointedLineNumber(noiseResultPath, i);
                List<CompareDataMeta> compareDataMetas = CompareDataResult.getCompareDataResult(noiseResultStr, compareBizResultStr);
                FileUtils.writeContent(compareBizFile, JSON.toJSONString(compareDataMetas));
            }
            compareTaskMapper.updateNoiseResult(value.getId(), compareBizFile);
        } catch (Exception e) {
            log.error("比对任务{}核对数据生成最终报告数据处理异常！", value.getId());
            compareTaskMapper.updateStatusAndFailure(value.getId(), CompareCons.Status.FAILED, CompareCons.Status.GENERATE_REPORTING, "核对数据生成最终报告数据处理异常");
            return false;
        }

        /*4.执行完毕无异常，进行状态变更*/
        return true;
    }

    @Override
    public void end(CompareTaskPo value) {
        log.info("开始核对数据生成最终报告处理完成，待更新状态:当前处理id为{}", value.getId());
        /*更新状态为"核对数据生成最终报告处理完成"*/
        compareTaskMapper.updateStatus(value.getId(), CompareCons.Status.GENERATE_REPORTED);
        log.info("开始核对数据生成最终报告处理完成:当前处理id为{}，状态已更新为{}", value.getId(), CompareCons.Status.GENERATE_REPORTED);
    }
}

状态逻辑分发器
        我们针对以上任务处理器，对实际业务处理进行分析并将其转发到相关的处理器上进行自动化处理，具体实现逻辑如下：

/**
 * @author yanfengzhang
 * @description 负责对task任务不同状态运行逻辑的分发。
 * @date 2022/5/2  01:44
 */
public class EventDispatcher {
    private static Map<Integer, Class> status2Processor = Maps.newHashMap();
    private static Set<AbstractProcessor> curProcessors = new HashSet<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(EventDispatcher.class);

    static {
        Reflections reflections = new Reflections("com.sankuai.tsp.product.bsap.domain.compare.event.processor.impl");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Status.class);
        for (Class<?> cl : classSet) {
            Annotation[] annotations = cl.getAnnotations();
            for (Annotation a : annotations) {
                if (a instanceof Status) {
                    Status status = (Status) a;
                    status2Processor.put(status.status(), cl);
                }
            }
        }
    }

    /**
     * dispatch方法目前只有cronServer线程调用，
     * 但是为了防止出现多线程调用导致的curProcessors被并发修改问题，所以用synchronized同步
     *
     * @param status        当前任务状态
     * @param compareTaskPo 比对任务消息数据
     * @return
     */
    public static synchronized boolean dispatch(int status, CompareTaskPo compareTaskPo) {
        AbstractProcessor processor = getInstance(status, compareTaskPo);
        if (processor != null) {
            curProcessors.add(processor);
            processor.process();
            return true;
        }
        return false;
    }

    private static AbstractProcessor getInstance(int status, CompareTaskPo compareTaskPo) {
        /*zyf:主动清理一次*/
        cleanDirty();
        if (containsStatus(status)) {
            try {
                Constructor constructor = status2Processor.get(status).getConstructor(CompareTaskPo.class);
                return (AbstractProcessor) constructor.newInstance(compareTaskPo);
            } catch (Exception ex) {
                LOGGER.error("EventDispatcher dispatcher getInstance error, exception:", ex);
            }
        }
        return null;
    }

    public static boolean containsStatus(int status) {
        return status2Processor.containsKey(status);
    }

    public static synchronized void cleanDirty() {
        curProcessors.removeIf(AbstractProcessor::allowRecycle);
    }

    public static int getTaskCount() {
        return curProcessors.size();
    }
}

定时任务定义
        针对以上的内容，我们内部维护一个基本的定时器来完成实际的业务自动化流转处理，主要代码和业务处理如下：

/**
 * @author yanfengzhang
 * @description 定时任务：定时读取数据库中比对数据需要处理的task任务，并分发到响应的processor处理。
 * @date 2022/5/2  02:18
 */
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
            /*清理已经完成的任务*/
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
                    /*如果处理的内容不在我们规定的范围时直接跳出*/
                    if (!EventDispatcher.containsStatus(compareTaskPo.getStatus())) {
                        continue;
                    }
                    /**
                     * 思考：
                     * 尝试更新一下last_ping_update的时间，更新成功代表抢锁成功，然后执行任务。
                     * 如果更新成功但是执行失败，待后续CronServer运行时再次尝试。
                     * 每台服务器每次定时任务只运行一个任务，防止同一台服务器抢占多个任务导致压力过大、负载不均衡的问题。
                     * （由于目前任务运行周期在多台服务器是一致的，所以极端情况下可能会出现任务被一台机器抢占的情况，
                     * 后续可以考虑使不同机器的运行周期随机或者引入分布式任务分配（负载均衡）策略）
                     */
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
}

