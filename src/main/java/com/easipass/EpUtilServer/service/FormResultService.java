package com.easipass.EpUtilServer.service;

import com.easipass.EpUtilServer.entity.DTO.ResultDTO;
import com.easipass.EpUtilServer.entity.Response;
import com.easipass.EpUtilServer.entity.Sftp;
import org.springframework.stereotype.Service;
import pers.ZJ.UiAuto.Step;
import java.util.List;

@Service
public interface FormResultService {

    /**
     * 上传回执
     * */
    Response upload(String ediNo, ResultDTO formResultDTO, boolean isDisposable, Sftp sftp, Step step);

    /**
     * 一次性上传
     * */
    Response disposableUpload(String ediNo, ResultDTO formResultDTO);

    /**
     * 批量上传
     * */
    Response uploadMore(String ediNo, List<ResultDTO> resultDTOS);

}
