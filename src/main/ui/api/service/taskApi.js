import baseServiceApi from "./baseServiceApi.js";

export default {
    url: "task",

    getTasks() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${this.url}/getTasks`
        });
    },

    deleteTask(data) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/deleteTask`,
            data: data
        });
    }
}