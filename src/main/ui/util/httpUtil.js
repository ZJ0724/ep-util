import Response from "../entity/Response.js";

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

                    // errorCode 400
                    if (response.errorCode === 400) {
                        alert(response.errorMsg);
                        return;
                    }

                    if (response.flag === "T") {
                        successCallback(response.data);
                    } else {
                        errorCallback(response.errorMsg);
                    }
                }
            }
        });
    }

}