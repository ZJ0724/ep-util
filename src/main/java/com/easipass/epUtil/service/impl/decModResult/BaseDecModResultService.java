package com.easipass.epUtil.service.impl.decModResult;

import com.easipass.epUtil.entity.ResultDTO;
import com.easipass.epUtil.entity.Oracle;
import com.easipass.epUtil.entity.Response;

public class BaseDecModResultService {

    /**
     * 设置文件名
     * */
    public static Response setFileName(String preEntryId) {
        //连接数据库
        Oracle oracle = Oracle.getSWGDOracle();
        if (!oracle.connect()) {
            return Response.returnFalse("数据库连接失败");
        }

        //修改文件名
        oracle.update("UPDATE SWGD.T_SWGD_DECMOD_HEAD SET FILE_NAME=? WHERE PRE_ENTRY_ID=?", new Object[]{ResultDTO.getFileName(preEntryId), preEntryId});

        //关闭数据库
        oracle.close();

        return Response.returnTrue(null);
    }

}
