import baseServiceApi from "./baseServiceApi.js";

export default {
    formCusMessageComparison(data) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: "cusMessage/formCusMessageComparison",
            dataType: "file",
            data: data
        });
    }
}