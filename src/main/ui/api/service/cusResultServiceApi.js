(function (service) {
    let cusResultServiceApi = {
        cusResult: "cusResult",

        formCusResult: "formCusResult",

        decModCusResult: "decModCusResult",

        // 上传报关单通讯回执
        formCusResultUploadTongXun(ediNo, cusResultDTO) {
            return service.baseServiceApi.sendHttp({
                url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.formCusResult}/uploadTongXun?ediNo=` + ediNo,
                type: "POST",
                data: cusResultDTO
            });
        },

        // 上传报关单业务回执
        formCusResultUploadYeWu(ediNo, cusResultDTO) {
            return service.baseServiceApi.sendHttp({
                url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.formCusResult}/uploadYeWu?ediNo=` + ediNo,
                type: "POST",
                data: cusResultDTO
            });
        },

        // 上传报关单业务回执
        formCusResultDisposableUpload(ediNo, cusResultDTO) {
            return service.baseServiceApi.sendHttp({
                url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.formCusResult}/disposableUpload?ediNo=` + ediNo,
                type: "POST",
                data: cusResultDTO
            });
        },

        // 上传修撤单QP回执
        decModCusResultUploadQP(preEntryId, cusResultDTO) {
            return service.baseServiceApi.sendHttp({
                url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.decModCusResult}/uploadQP?preEntryId=` + preEntryId,
                type: "POST",
                data: cusResultDTO
            });
        },

        // 上传修撤单业务回执
        decModCusResultUploadYeWu(preEntryId, cusResultDTO) {
            return service.baseServiceApi.sendHttp({
                url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.decModCusResult}/uploadYeWu?preEntryId=` + preEntryId,
                type: "POST",
                data: cusResultDTO
            });
        },

        // 一次性上传修撤单回执
        decModCusResultDisposableUpload(preEntryId, cusResultDTO) {
            return service.baseServiceApi.sendHttp({
                url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.decModCusResult}/disposableUpload?preEntryId=` + preEntryId,
                type: "POST",
                data: cusResultDTO
            });
        },

        // 上传代理委托回执
        agentCusResultUpload(ediNo, cusResultDTO) {
            return service.baseServiceApi.sendHttp({
                url: `${cusResultServiceApi.cusResult}/agentCusResult/upload?ediNo=` + ediNo,
                type: "POST",
                data: cusResultDTO
            });
        }
    };

    service.cusResultServiceApi = cusResultServiceApi;
})(window.epUtil.api.service);