package com.easipass.util.core.service;

import com.easipass.util.core.entity.Task;
import com.easipass.util.core.util.ThreadUtil;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 执行任务服务
 *
 * @author ZJ
 * */
public abstract class TaskRunService {

    /**
     * 线程池
     * */
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = ThreadUtil.getThreadPoolExecutor(10);

    /**
     * 任务名
     * */
    private final String name;

    /**
     * 构造函数
     *
     * @param name 任务名
     * */
    public TaskRunService(String name) {
        this.name = name;
    }

    /**
     * run
     *
     * @return 任务结果
     * */
    public abstract String run();

    /**
     * 任务执行完回调
     * */
    public void afterRun() {}

    /**
     * 开始任务
     * */
    public final void start() {
        final Task task = new Task(this.name);
        new TaskService().addTask(task);

        THREAD_POOL_EXECUTOR.execute(() -> {
            String message = null;
            try {
                message = this.run();
            } catch (Throwable e) {
                message = e.getMessage();
            } finally {
                this.afterRun();
                task.end(message);
            }
        });
    }

}