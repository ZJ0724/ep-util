import baseServiceApi from "./baseServiceApi.js";

export default {

    search: "search",

    formHead(data) {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${this.search}/formHead?type=${data.type}&data=${data.data}`
        });
    },

    decMod(preEntryId) {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${this.search}/decMod?preEntryId=${preEntryId}`
        });
    },

    agent(ediNo) {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${this.search}/agent?ediNo=${ediNo}`
        });
    }

}