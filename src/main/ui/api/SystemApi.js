// 基础api方法
import BaseApi from "./baseApi.js";

export default {

    // 基础方法
    base(type, url) {
        return BaseApi.send({
            type: type,
            url: `/system/${url}`
        });
    },

    // 获取版本信息
    getVersion() {
        return this.base("get", "getVersion");
    }

}