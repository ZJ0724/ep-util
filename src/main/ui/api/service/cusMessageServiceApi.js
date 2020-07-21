import baseServiceApi from "./baseServiceApi.js";

let cusMessageServiceApi = {

    // 上传报关单报文
    upload(file) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: "cusMessage/formCusMessageUpload",
            data: {"formCusMessage": file},
            dataType: "file"
        });
    }

};

export default cusMessageServiceApi;