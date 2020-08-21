package com.easipass.util.api.service;

import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * ParamDbServiceApi
 *
 * @author ZJ
 * */
@RestController
@RequestMapping(BaseServiceApi.URL + "paramDb")
public class ParamDbServiceApi {

    /**
     * 比对mdb文件表数量
     *
     * @param multipartFile multipartFile
     *
     * @return Response
     * */
    @PostMapping("importComparison")
    public Response importComparison(@RequestParam("mdb") MultipartFile multipartFile, @RequestParam("groupName") String groupName) {
        return Response.returnTrue(ParamDbComparator.getInstance().importComparison(groupName, multipartFile));
    }

}