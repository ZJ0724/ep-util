package com.easipass.util.api.service;

import com.easipass.util.core.DTO.SearchFormHeadDTO;
import com.easipass.util.core.database.SWGDDatabase;
import com.easipass.util.entity.Response;
import com.zj0724.util.springboot.parameterCheck.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SearchServiceApi
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "search")
public class SearchServiceApi {

    /**
     * 搜索报关单
     *
     * @param searchFormHeadDTO searchFormHeadDTO
     *
     * @return Response
     * */
    @PostMapping("formHead")
    public Response formHead(@RequestBody @NotNull SearchFormHeadDTO searchFormHeadDTO) {
        return Response.returnTrue(SWGDDatabase.queryFormHead(searchFormHeadDTO.getType(), searchFormHeadDTO.getData()));
    }

}