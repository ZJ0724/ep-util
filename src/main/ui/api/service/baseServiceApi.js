import Response from "../../entity/Response.js";

let baseServiceApi = {
    // T
    T: "T",

    // F
    F: "F",

    // errorCode
    errorCode: [500, 501, 400],

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
    },

    // 下载
    downLoad(url) {
        return new Promise((successCallback) => {
            let http = new XMLHttpRequest();
            http.open("get", "/api/" + url);
            http.responseType = "blob";

            http.onload = function () {
                if (http.status === 200) {
                    let fileReader = new FileReader();
                    fileReader.onload = function (e) {
                        let a = document.createElement("a");
                        a.download = decodeURIComponent(http.getResponseHeader("Content-disposition")).substring(20);
                        a.href = e.target.result;
                        a.click();
                        successCallback();
                    };
                    fileReader.readAsDataURL(http.response);
                }
            };

            http.send();
        });
    }
};

export default baseServiceApi;