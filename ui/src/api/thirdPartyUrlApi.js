import baseApi from "./baseApi.js";

const u = "/thirdPartyUrl";

export default {
    getAll() {
        return baseApi.send({
            url: `${u}/getAll`,
            type: "GET"
        });
    },

    save(data) {
        return baseApi.send({
            url: `${u}/save`,
            type: "POST",
            data: data,
            check: {
                data: {
                    id: Number,
                    url: String,
                    note: String,
                    requestHeader: String,
                    requestData: String
                }
            }
        });
    },

    delete(data) {
        return baseApi.send({
            url: `${u}/delete`,
            type: "POST",
            data: data,
            check: {
                data: {
                    id: Number
                }
            }
        });
    }
};