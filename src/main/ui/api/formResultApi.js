import httpUtil from "./baseApi.js";

export default {

    base(type, url, formResult) {
        let ediNo = formResult.ediNo,
            result_result = formResult.result;

        return httpUtil.send({
            type: type,
            url: `/formResult/${url}?ediNo=${ediNo}`,
            data: result_result
        });
    },

    // 上传通讯回执
    tongXunFormResultUpload(result) {
        return this.base("post", "upload/tongXun", result);
    },

    // 上传业务回执
    yeWuFormResultUpload(result) {
        return this.base("post", "upload/yeWu", result);
    },

    // 一次性上传回执
    disposableUpload(result) {
        return this.base("post", "disposableUpload", result);
    }

}