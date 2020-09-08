package com.easipass.util.api.service;

import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.entity.Response;
import com.zj0724.util.springboot.parameterCheck.NotNull;
import com.zj0724.util.springboot.parameterCheck.OpenParameterCheck;
import org.springframework.web.bind.annotation.*;

/**
 * SearchServiceApi
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "search")
@OpenParameterCheck
public class SearchServiceApi {

    /**
     * 搜索报关单
     *
     * @param type type
     * @param data data
     *
     * @return Response
     * */
    @GetMapping("formHead")
    public Response formHead(@RequestParam @NotNull String type, @RequestParam String data) {
        return Response.returnTrue(SWGDDatabase.queryFormHead(type, data));
    }

    /**
     * 搜索修撤单
     *
     * @param preEntryId preEntryId
     *
     * @return Response
     * */
    @GetMapping("decMod")
    public Response decMod(@RequestParam String preEntryId) {
        return Response.returnTrue(SWGDDatabase.searchDecMod(preEntryId));
    }

    /**
     * 搜索代理委托
     *
     * @param ediNo ediNo
     *
     * @return Response
     * */
    @GetMapping("agent")
    public Response agent(@RequestParam String ediNo) {
        return Response.returnTrue(SWGDDatabase.searchAgent(ediNo));
    }

}