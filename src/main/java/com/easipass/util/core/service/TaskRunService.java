package com.easipass.util.core.service;

import com.easipass.util.core.Project;
import com.easipass.util.core.entity.Task;

/**
 * 执行任务服务
 *
 * @author ZJ
 * */
public abstract class TaskRunService {

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
     * 开始任务
     * */
    public final void start() {
        final Task task = new Task(this.name);
        new TaskService().addTask(task);

        Project.THREAD_POOL_EXECUTOR.execute(() -> {
            String message = null;
            try {
                message = this.run();
            } catch (Throwable e) {
                message = e.getMessage();
            } finally {
                task.end(message);
            }
        });
    }

}