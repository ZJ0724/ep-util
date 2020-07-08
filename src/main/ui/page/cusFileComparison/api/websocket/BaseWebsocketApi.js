(function (websocket) {
    websocket.BaseWebsocketApi = class BaseWebsocketApi {

        websocket;

        constructor(url) {
            url = document.location.origin + "/websocket/" + url;
            url = url.replace("http", "ws").replace("https", "ws");
            this.websocket = new WebSocket(url);
        }

        // 等待连接完成
        async waitConnect() {
            await new Promise((successCallback) => {
                if (this.websocket.readyState === 1) {
                    successCallback();
                    return;
                }

                this.websocket.onopen = function () {
                    successCallback();
                }
            });
        }

        // 发送消息
        send(message) {
            this.waitConnect().then();
            this.websocket.send(message);
        }

        // 监听消息
        onmessage(callback) {
            this.websocket.onmessage = function (message) {
                callback(JSON.parse(message.data));
            }
        }

        // 监听关闭
        onclose(callback) {
            this.websocket.onclose = function () {
                callback();
            }
        }

        // 关闭连接
        close() {
            this.waitConnect().then();
            this.websocket.close();
        }

    }
})(window.epUtil.api.websocket);