import baseServiceApi from "./baseServiceApi.js";

export default {

    sendCusFile(data) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: "supplySend/sendCusFile",
            data: data
        });
    }

}