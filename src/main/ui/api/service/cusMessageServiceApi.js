(function (service) {
    service.cusMessageServiceApi = {
        // 上传报关单报文
        upload(file) {
            let baseServiceApi = service.baseServiceApi;

            return baseServiceApi.sendHttp({
                type: "POST",
                url: "cusMessage/formCusMessageUpload",
                data: {"formCusMessage": file},
                dataType: "file"
            });
        }
    };
})(window.epUtil.api.service);