import baseServiceApi from "./baseServiceApi.js";

export default {
    getErrorLog() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: "system/getErrorLog"
        });
    },

    cleanErrorLog() {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: "system/cleanErrorLog"
        });
    }
}