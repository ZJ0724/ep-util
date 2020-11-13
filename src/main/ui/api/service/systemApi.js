import baseServiceApi from "./baseServiceApi.js";

export default {
    uri: "system",

    getErrorLog() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: "system/getErrorLog"
        });
    },

    cleanErrorLog(data) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: "system/cleanErrorLog",
            data: data
        });
    },

    gc() {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: "system/gc"
        });
    },

    getSWGDPARAConfig() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${this.uri}/getSWGDPARAConfig`
        });
    }
}