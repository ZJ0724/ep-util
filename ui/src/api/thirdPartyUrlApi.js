import baseApi from "./baseApi.js";

const u = "/thirdPartyUrl";

export default {
    getAll() {
        return baseApi.send({
            url: `${u}/getAll`,
            type: "GET"
        });
    }
};