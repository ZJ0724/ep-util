import httpUtil from "./baseApi.js";

export default {

    base(type, url) {
        return httpUtil.send({
            type: type,
            url: `/daKa/${url}`
        });
    },

    // 开启
    start() {
        return this.base("post", "start");
    },

    // 关闭
    stop() {
        return this.base("post", "stop");
    },

    // 获取日志
    getLog() {
        return this.base("get", "getLog");
    },

    // 获取状态
    getStatus() {
        return this.base("get", "getStatus");
    },

    // 手动打卡
    manualDaKa() {
        return this.base("post", "manualDaKa");
    }

}