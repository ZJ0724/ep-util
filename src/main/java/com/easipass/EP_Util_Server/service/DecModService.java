package com.easipass.EP_Util_Server.service;

import com.easipass.EP_Util_Server.entity.DecMod;
import org.springframework.stereotype.Service;

@Service
public interface DecModService {

    /**
     * 设置修撤单回执文件名
     * */
    public void setFileName(DecMod decMod);

}