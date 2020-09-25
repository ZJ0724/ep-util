import baseServiceApi from "./baseServiceApi.js";

export default {
    url: "daKa2.0",

    open() {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/open`
        });
    },

    close() {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/close`
        });
    },

    manual() {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/manual`
        });
    },

    getStatus() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${this.url}/getStatus`
        });
    },

    getLog() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${this.url}/getLog`
        });
    },

    cleanLog() {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/cleanLog`
        });
    }
}