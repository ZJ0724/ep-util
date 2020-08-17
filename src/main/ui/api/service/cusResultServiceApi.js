import baseServiceApi from "./baseServiceApi.js";

let cusResultServiceApi = {

    cusResult: "cusResult",

    formCusResult: "formCusResult",

    decModCusResult: "decModCusResult",

    // 上传报关单通讯回执
    formCusResultUploadTongXun(data) {
        return baseServiceApi.sendHttp({
            url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.formCusResult}/uploadTongXun`,
            type: "POST",
            data: data
        });
    },

    // 上传报关单业务回执
    formCusResultUploadYeWu(data) {
        return baseServiceApi.sendHttp({
            url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.formCusResult}/uploadYeWu`,
            type: "POST",
            data: data
        });
    },

    // 上传报关单业务回执
    formCusResultDisposableUpload(ediNo, cusResultDTO) {
        return baseServiceApi.sendHttp({
            url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.formCusResult}/disposableUpload?ediNo=` + ediNo,
            type: "POST",
            data: cusResultDTO
        });
    },

    // 上传修撤单QP回执
    decModCusResultUploadQP(preEntryId, cusResultDTO) {
        return baseServiceApi.sendHttp({
            url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.decModCusResult}/uploadQP?preEntryId=` + preEntryId,
            type: "POST",
            data: cusResultDTO
        });
    },

    // 上传修撤单业务回执
    decModCusResultUploadYeWu(preEntryId, cusResultDTO) {
        return baseServiceApi.sendHttp({
            url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.decModCusResult}/uploadYeWu?preEntryId=` + preEntryId,
            type: "POST",
            data: cusResultDTO
        });
    },

    // 一次性上传修撤单回执
    decModCusResultDisposableUpload(preEntryId, cusResultDTO) {
        return baseServiceApi.sendHttp({
            url: `${cusResultServiceApi.cusResult}/${cusResultServiceApi.decModCusResult}/disposableUpload?preEntryId=` + preEntryId,
            type: "POST",
            data: cusResultDTO
        });
    },

    // 上传代理委托回执
    agentCusResultUpload(ediNo, cusResultDTO) {
        return baseServiceApi.sendHttp({
            url: `${cusResultServiceApi.cusResult}/agentCusResult/upload?ediNo=` + ediNo,
            type: "POST",
            data: cusResultDTO
        });
    }

};

export default cusResultServiceApi;