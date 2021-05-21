package com.easipass.util.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public interface UserService {

    /**
     * 新建用户
     *
     * @param data 数据
     * */
    void addUser(Map<String, Object> data);

}