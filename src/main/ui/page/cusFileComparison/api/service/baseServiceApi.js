(function (service, entity) {
    let baseServiceApi = {
        // T
        T: "T",

        // F
        F: "F",

        // errorCode
        errorCode: [500, 501],

        // 发送
        sendHttp(option) {
            return new Promise((successCallback, errorCallback) => {
                let type = option.type,
                    url = "/api/" + option.url,
                    data = option.data,
                    dataType = option.dataType,
                    sendData = JSON.stringify(data);

                let http = new XMLHttpRequest();
                http.open(type, url);

                if (dataType === "file") {
                    sendData = new FormData();
                    for (let key in data) {
                        if (data.hasOwnProperty(key)) {
                            sendData.append(key, data[key]);
                        }
                    }
                }

                if ((type === "post" || type === "POST") && dataType !== "file") {
                    http.setRequestHeader("content-type", "application/json");
                }

                http.send(sendData);

                http.onreadystatechange = function () {
                    if (http.status === 200 && http.readyState === 4) {
                        let Response = entity.Response;
                        let response = new Response();
                        response.setData(JSON.parse(http.response));
                        console.log(response);

                        // 判断errorCode
                        for (let value of baseServiceApi.errorCode) {
                            if (value === response.errorCode) {
                                alert(response.errorMessage);
                                return;
                            }
                        }

                        // 调用不同回调
                        if (response.flag === baseServiceApi.T) {
                            successCallback(response.data);
                            return;
                        }
                        if (response.flag === baseServiceApi.F) {
                            errorCallback(response.errorMessage);
                        }
                    }
                }
            });
        }
    };

    service.baseServiceApi = baseServiceApi;
})(window.epUtil.api.service, window.epUtil.entity);