import httpUtil from "./baseApi.js";

export default {

    base(type, url, result) {
        return httpUtil.send({
            type: type,
            url: `/disposableUpload/${url}`,
            data: result
        });
    },

    // 一次性上传报关单回执
    formResult(ediNo, result) {
        return this.base("post", `formResult/?ediNo=${ediNo}`, result);
    },

    // 一次性上传修撤单回执
    decModResult(preEntryId, result) {
        return this.base("post", `decModResult/?preEntryId=${preEntryId}`, result);
    }

}