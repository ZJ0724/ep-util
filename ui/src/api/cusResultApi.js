import baseApi from "./baseApi.js";

const u = "/cusResult"

export default {
    uploadCustomsDeclaration(data) {
        return baseApi.send({
            url: `${u}/uploadCustomsDeclaration`,
            type: "POST",
            data: data,
            check: {
                data: {
                    customsDeclarationNumber: "",
                    code: "",
                    note: ""
                }
            }
        });
    }
};