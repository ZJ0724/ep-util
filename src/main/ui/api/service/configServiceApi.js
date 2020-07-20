(function (service) {
    let config = "config/";

    service.configServiceApi = {
        // 设置打卡配置
        setDaKa(daKaConfigDTO) {
            let baseServiceApi = service.baseServiceApi;

            return baseServiceApi.sendHttp({
                type: "POST",
                url: `${config}setDaKa`,
                data: daKaConfigDTO
            });
        },

        // 获取打卡配置
        getDaKa() {
            let baseServiceApi = service.baseServiceApi;

            return baseServiceApi.sendHttp({
                type: "GET",
                url: `${config}getDaKa`
            });
        },

        // 设置SWGD配置
        setSWGD(swgdConfigDTO) {
            let baseServiceApi = service.baseServiceApi;

            return baseServiceApi.sendHttp({
                type: "POST",
                url: `${config}setSWGD`,
                data: swgdConfigDTO
            });
        },

        // 获取SWGD配置
        getSWGD() {
            let baseServiceApi = service.baseServiceApi;

            return baseServiceApi.sendHttp({
                type: "GET",
                url: `${config}getSWGD`
            });
        },

        // 设置sftp83配置
        setSftp83(sftp83ConfigDTO) {
            let baseServiceApi = service.baseServiceApi;

            return baseServiceApi.sendHttp({
                type: "POST",
                url: `${config}setSftp83`,
                data: sftp83ConfigDTO
            });
        },

        // 获取sftp83配置
        getSftp83() {
            let baseServiceApi = service.baseServiceApi;

            return baseServiceApi.sendHttp({
                type: "GET",
                url: `${config}getSftp83`
            });
        }
    };
})(window.epUtil.api.service);