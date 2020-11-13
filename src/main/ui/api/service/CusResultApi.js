import baseServiceApi from "./baseServiceApi.js";

export default {
    url: "cusResult",

    uploadNewAgentCusResult(data) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/uploadNewAgentCusResult`,
            data: data
        });
    }
}