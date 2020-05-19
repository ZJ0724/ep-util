import Response from "../entity/Response.js";
import responseConfig from "../config/responseConfig.js";

export default {

    send(option) {
        return new Promise((successCallback, errorCallback) => {
            let http = new XMLHttpRequest();
            let type = option.type;
            let url = option.url;
            let data = option.data;

            http.open(type, url);
            if (type === "post" || type === "POST") {
                http.setRequestHeader("content-type", "application/json");
            }
            http.send(JSON.stringify(data));
            http.onreadystatechange = function () {
                if (http.status === 200 && http.readyState === 4) {
                    let response = new Response();
                    response.setData(JSON.parse(http.response));
                    console.log(response);
                    let flag = response.flag,
                        errorCode = response.errorCode,
                        errorMsg = response.errorMsg,
                        data = response.data,
                        responseConfigTrue = responseConfig.true,
                        responseConfigFalse = responseConfig.false,
                        errorCodes = responseConfig.errorCodes;

                    // 判断errorCode
                    for (let _errorCode of errorCodes) {
                        if (_errorCode === errorCode) {
                            alert(errorMsg);
                        }
                    }

                    // 调用不同回调
                    if (flag === responseConfigTrue) {
                        successCallback(data);
                    }
                    if (flag === responseConfigFalse) {
                        errorCallback(errorMsg);
                    }
                }
            }
        });
    }

}