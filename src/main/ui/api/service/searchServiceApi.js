import baseServiceApi from "./baseServiceApi.js";

export default {

    formHead(data) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: "search/formHead",
            data: data
        });
    }

}