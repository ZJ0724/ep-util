package com.easipass.EP_Util_Server.service.impl;

import com.easipass.EP_Util_Server.entity.DecMod;
import com.easipass.EP_Util_Server.entity.SWGD;
import com.easipass.EP_Util_Server.exception.TipException;
import com.easipass.EP_Util_Server.exception.UtilTipException;
import com.easipass.EP_Util_Server.service.DecModService;
import com.easipass.EP_Util_Server.util.OracleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DecModServiceImpl implements DecModService {

    @Autowired
    SWGD swgd;

    @Override
    public void setFileName(DecMod decMod) {
        //连接数据库
        OracleUtil oracleUtil=new OracleUtil(swgd.getUrl(),swgd.getPort(),swgd.getSid(),swgd.getUsername(),swgd.getPassword());
        try {
            oracleUtil.connect();
        }catch (UtilTipException e){
            throw new TipException("SWGD数据库连接失败");
        }

        //修改文件名
        oracleUtil.update("update SWGD.T_SWGD_DECMOD_HEAD set FILE_NAME=? where PRE_ENTRY_ID=?",new Object[]{decMod.getFileName(),decMod.getPreEntryId()});

        //关闭数据库
        oracleUtil.close();
    }

}