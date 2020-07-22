import baseServiceApi from "./baseServiceApi.js";

let configServiceApi = {

    config: "config/",

    // 设置打卡配置
    setDaKa(daKaConfigDTO) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${configServiceApi.config}setDaKa`,
            data: daKaConfigDTO
        });
    },

    // 获取打卡配置
    getDaKa() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${configServiceApi.config}getDaKa`
        });
    },

    // 设置SWGD配置
    setSWGD(swgdConfigDTO) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${configServiceApi.config}setSWGD`,
            data: swgdConfigDTO
        });
    },

    // 获取SWGD配置
    getSWGD() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${configServiceApi.config}getSWGD`
        });
    },

    // 设置sftp83配置
    setSftp83(sftp83ConfigDTO) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${configServiceApi.config}setSftp83`,
            data: sftp83ConfigDTO
        });
    },

    // 获取sftp83配置
    getSftp83() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${configServiceApi.config}getSftp83`
        });
    }

};

export default configServiceApi;