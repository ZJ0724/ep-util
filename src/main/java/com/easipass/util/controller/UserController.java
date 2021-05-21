package com.easipass.util.controller;

import com.easipass.util.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(BaseController.API + "/user")
public final class UserController {

    @Resource
    private UserService userService;

    @PostMapping("addUser")
    public Response addUser(@RequestBody(required = false) Map<String, Object> requestBody) {
        userService.addUser(requestBody);
        return Response.returnTrue();
    }

}