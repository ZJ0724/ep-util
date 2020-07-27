import baseServiceApi from "./baseServiceApi.js";

export default {

    // 获取系统版本
    getVersion() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: "system/getVersion"
        });
    }

}