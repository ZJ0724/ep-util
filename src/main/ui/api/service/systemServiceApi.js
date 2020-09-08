import baseServiceApi from "./baseServiceApi.js";

export default {

    system: "system/",

    // 获取系统版本
    getVersion() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: this.system + "getVersion"
        });
    },

    getTime() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: this.system + "getTime"
        });
    },

    getCache() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: this.system + "getCache"
        });
    },

    deleteCacheFile(data) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: this.system + "deleteCacheFile",
            data: data
        });
    }

}