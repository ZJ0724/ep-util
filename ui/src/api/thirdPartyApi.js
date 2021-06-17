import baseApi from "./baseApi.js";

const u = "/thirdParty";

export default {
    send(data) {
        return baseApi.send({
            url: `${u}/send`,
            type: "POST",
            data: data,
            check: {
                data: {
                    userCode: String,
                    url: String,
                    header: Object,
                    requestData: String
                }
            }
        });
    },

    getUsers() {
        return baseApi.send({
            url: `${u}/getUsers`,
            type: "GET"
        });
    }
};