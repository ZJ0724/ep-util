import baseServiceApi from "./baseServiceApi.js";

export default {

    paramDb: "paramDb",

    mdbComparison(file) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.paramDb}/mdbComparison`,
            data: {
                mdb: file
            },
            dataType: "file"
        });
    }

}