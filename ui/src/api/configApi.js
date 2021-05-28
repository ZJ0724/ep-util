import baseApi from "./baseApi.js";

const u = "/config";

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
                    code: String,
                    note: String,
                    data: String
                }
            }
        });
    }
};