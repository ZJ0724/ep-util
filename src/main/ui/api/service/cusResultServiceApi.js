(function (service) {
    let cusResultServiceApi = {
        cusResult: "cusResult",

        formCusResult: "formCusResult",

        // 上传报关单通讯回执
        formCusResultUploadTongXun(ediNo, cusResultDTO) {
            return service.baseServiceApi.sendHttp({
                url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.formCusResult}/tongXunUpload?ediNo=` + ediNo,
                type: "POST",
                data: cusResultDTO
            });
        }
    };

    service.cusResultServiceApi = cusResultServiceApi;
})(window.epUtil.api.service);