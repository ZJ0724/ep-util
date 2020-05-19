import httpUtil from "./baseApi.js";

export default {

    base(type, url, agentResult) {
        let ediNo = agentResult.ediNo,
            result_result = agentResult.result;

        return httpUtil.send({
            type: type,
            url: `/agentResult/${url}?ediNo=${ediNo}`,
            data: result_result
        });
    },

    // 上传代理委托回执
    upload(agentResult) {
        return this.base("post", "upload", agentResult);
    }

}