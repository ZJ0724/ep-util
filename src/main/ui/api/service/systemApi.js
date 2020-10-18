import baseServiceApi from "./baseServiceApi.js";

export default {
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
    }
}