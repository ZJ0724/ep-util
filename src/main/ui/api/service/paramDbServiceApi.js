import baseServiceApi from "./baseServiceApi.js";

export default {

    paramDb: "paramDb",

    upload(file) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.paramDb}/upload`,
            data: {
                mdb: file
            },
            dataType: "file"
        });
    }

}