import baseServiceApi from "./baseServiceApi.js";

let configServiceApi = {

    config: "config/",

    // 设置打卡配置
    setDaKaConfig(daKaConfigDTO) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${configServiceApi.config}setDaKaConfig`,
            data: daKaConfigDTO
        });
    },

    // 获取打卡配置
    getDaKaConfig() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${configServiceApi.config}getDaKaConfig`
        });
    },

    // 设置SWGD配置
    setSWGDDatabaseConfig(swgdConfigDTO) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${configServiceApi.config}setSWGDDatabaseConfig`,
            data: swgdConfigDTO
        });
    },

    // 获取SWGD配置
    getSWGDDatabaseConfig() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${configServiceApi.config}getSWGDDatabaseConfig`
        });
    },

    // 设置sftp83配置
    setCusResultUploadSftpConfig(sftp83ConfigDTO) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${configServiceApi.config}setCusResultUploadSftpConfig`,
            data: sftp83ConfigDTO
        });
    },

    // 获取sftp83配置
    getCusResultUploadSftpConfig() {
        return baseServiceApi.sendHttp({
            type: "GET",
            url: `${configServiceApi.config}getCusResultUploadSftpConfig`
        });
    }

};

export default configServiceApi;