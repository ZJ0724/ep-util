(function (service) {
    service.cusFileServiceApi = {
        // 上传报文
        upload(cusFile) {
            return service.baseServiceApi.sendHttp({
                type: "POST",
                url: "cusFile/upload",
                data: {"cusFile": cusFile},
                dataType: "file"
            });
        }
    };
})(window.epUtil.api.service);