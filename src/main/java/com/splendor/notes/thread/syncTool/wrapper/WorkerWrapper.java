package com.splendor.notes.thread.syncTool.wrapper;

import com.splendor.notes.thread.syncTool.callback.DefaultCallback;
import com.splendor.notes.thread.syncTool.callback.ICallback;
import com.splendor.notes.thread.syncTool.callback.IWorker;
import com.splendor.notes.thread.syncTool.worker.WorkResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author splendor.s
 * @create 2022/10/3 23:03
 * @description 对每个worker及callback进行包装，一对一
 */
public class WorkerWrapper<T , V> {

    /**
     * 该wrapper的唯一标识
     */
    private String id;

    /**
     * worker将来要处理的param
     */
    private T param;

    private IWorker<T, V> worker;

    private ICallback<T, V> callback;

    /**
     * 依赖的wrappers，有2种情况，
     * 1:必须依赖的全部完成后，才能执行自己
     * 2:依赖的任何一个、多个完成了，就可以执行自己
     * 通过must字段来控制是否依赖项必须完成
     * 1
     * -------3
     * 2
     * 1、2执行完毕后才能执行3
     */
    private List<WorkerWrapper<?,?>> dependWrappers;

    /**
     * 标记该事件是否已经被处理过了，譬如已经超时返回false了，
     * 后续rpc又收到返回值了，则不再二次回调
     * 经试验,volatile并不能保证"同一毫秒"内,多线程对该值的修改和拉取
     * 1-finish, 2-error, 3-working
     */
    private AtomicInteger state = new AtomicInteger(0);

    /**
     * 该map存放所有wrapper的id和wrapper映射
     */
    private Map<String , WorkerWrapper> forParamUseWrappers;

    /**
     * 也是个钩子变量，用来存临时的结果
     */
    private volatile WorkResult<V> workResult = WorkResult.defaultResult();

    private volatile boolean needCheckNextWrapperResult = true;

    private static final int FINISH = 1;
    private static final int ERROR = 2;
    private static final int WORKING = 3;
    private static final int INIT = 0;

    private WorkerWrapper(String id, IWorker<T , V> worker, T Param, ICallback<T ,V> callback){

        if (worker == null) {
            throw new NullPointerException("async.worker is null");
        }
        this.worker = worker;
        this.param = param;
        this.id = id;
        //允许不设置回调
        if (callback == null) {
            callback = new DefaultCallback<>();
        }
        this.callback = callback;
    }

    //TODO 未梳理
}
