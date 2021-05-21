import baseApi from "./baseApi.js";

const u = "/user";

export default {
    addUser(data) {
        return baseApi.send({
            url: `${u}/addUser`,
            type: "POST",
            data: data
        });
    }
};