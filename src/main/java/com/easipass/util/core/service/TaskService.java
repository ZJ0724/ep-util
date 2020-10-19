package com.easipass.util.core.service;

import com.easipass.util.core.BaseException;
import com.easipass.util.core.Project;
import com.easipass.util.core.entity.Task;
import com.easipass.util.core.exception.ErrorException;
import com.easipass.util.core.util.StringUtil;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 任务服务
 *
 * @author ZJ
 * */
@Service
public final class TaskService {

    /**
     * 获取所有任务
     *
     * @return 所有任务
     * */
    public List<Task> getTasks() {
        return Project.TASKS;
    }

    /**
     * 删除任务
     *
     * @param id 任务id
     * */
    public void deleteTask(Integer id) {
        if (id == null) {
            throw new ErrorException("未选择任务");
        }

        for (Task task : Project.TASKS) {
            if (id.equals(task.getId())) {
                if (task.isRun()) {
                    throw new BaseException("任务在执行，不能删除") {};
                }
                Project.TASKS.remove(task);
                return;
            }
        }

        throw new BaseException("未找到任务") {};
    }

    /**
     * 添加任务
     *
     * @param task 任务
     * */
    public void addTask(Task task) {
        if (task == null) {
            throw new ErrorException("未添加任务");
        }

        if (StringUtil.isEmpty(task.getName())) {
            throw new BaseException("任务名不能为空") {};
        }

        Project.TASKS.add(task);
    }

}