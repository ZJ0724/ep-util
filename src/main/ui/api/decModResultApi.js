import httpUtil from "./baseApi.js";

export default {

    base(type, url, decModResult) {
        let preEntryId = decModResult.preEntryId,
            result_result = decModResult.result;

        return httpUtil.send({
            type: type,
            url: `/decModResult/${url}?preEntryId=${preEntryId}`,
            data: result_result
        });
    },

    // 上传QP回执
    uploadQP(result) {
        return this.base("post", "upload/QP", result);
    },

    // 上传业务回执
    uploadYeWu(result) {
        return this.base("post", "upload/yeWu", result);
    }

}