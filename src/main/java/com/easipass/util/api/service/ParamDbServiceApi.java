package com.easipass.util.api.service;

import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.entity.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping("mdbComparison")
    public Response mdbComparison(@RequestParam("mdb") MultipartFile multipartFile) {
        return Response.returnTrue(ParamDbComparator.getInstance().mdbComparison(multipartFile));
    }

}