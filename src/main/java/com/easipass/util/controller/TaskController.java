package com.easipass.util.controller;

import com.easipass.util.api.service.BaseServiceApi;
import com.easipass.util.core.service.TaskService;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

/**
 * TaskController
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "task")
public class TaskController {

    @Resource
    private TaskService taskService;

    /**
     * 获取所有任务
     *
     * @return Response
     * */
    @GetMapping("getTasks")
    public Response getTasks() {
        return Response.returnTrue(taskService.getTasks());
    }

    /**
     * 删除任务
     *
     * @param requestBody requestBody
     *
     * @return Response
     * */
    @PostMapping("deleteTask")
    public Response deleteTask(@RequestBody Map<String, Object> requestBody) {
        taskService.deleteTask((Integer) requestBody.get("id"));
        return Response.returnTrue();
    }

}