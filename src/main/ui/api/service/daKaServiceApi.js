import baseServiceApi from "./baseServiceApi.js";

let daKaServiceApi = {

    daKa: "daKa/",

    // 开启自动打卡
    openAutoDaKa() {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${daKaServiceApi.daKa}openAutoDaKa`
        });
    },

    // 关闭打卡
    closeAutoDaKa() {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${daKaServiceApi.daKa}closeAutoDaKa`
        });
    },

    // 获取自动打卡状态
    getStatus() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${daKaServiceApi.daKa}getStatus`
        });
    },

    // 清空日志
    cleanLog() {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${daKaServiceApi.daKa}cleanLog`
        });
    },

    // 手动打卡
    manualKaKa() {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${daKaServiceApi.daKa}manualKaKa`
        });
    }

};

export default daKaServiceApi;