import baseServiceApi from "./baseServiceApi.js";

let cusMessageServiceApi = {

    // 上传报关单报文
    uploadFormCusMessage(file) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: "cusMessage/upload/formCusMessage",
            data: {"formCusMessage": file},
            dataType: "file"
        });
    },

    // 上传报关单报文
    uploadDecModCusMessage(file) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: "cusMessage/upload/decModCusMessage",
            data: {"decModCusMessage": file},
            dataType: "file"
        });
    }

};

export default cusMessageServiceApi;