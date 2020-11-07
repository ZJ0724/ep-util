import baseServiceApi from "./baseServiceApi.js";

export default {
    url: "cacheFile",

    getAll() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${this.url}/getAll`
        });
    },

    delete(data) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/delete`,
            data: data
        });
    }
}