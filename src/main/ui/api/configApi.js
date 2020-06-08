import httpUtil from "./baseApi.js";

export default {

    base(type, url, config) {
        return httpUtil.send({
            type: type,
            url: `/config/${url}`,
            data: config
        });
    },

    // 获取
    get() {
        return this.base("get", "get");
    },

    // 设置
    set(config) {
        return this.base("post", "set", config);
    }

}