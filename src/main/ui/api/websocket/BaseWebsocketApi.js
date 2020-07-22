export default class BaseWebsocketApi {

    websocket;

    constructor(url) {
        url = document.location.origin + "/websocket/" + url;
        url = url.replace("http", "ws").replace("https", "ws");
        this.websocket = new WebSocket(url);
    }

    // 等待连接完成
    waitConnect() {
        return new Promise((successCallback) => {
            if (this.websocket.readyState === 1) {
                successCallback();
            } else {
                this.websocket.onopen = function () {
                    console.log("已连接");
                    successCallback();
                }
            }
        });
    }

    // 发送消息
    async send(message) {
        await this.waitConnect();

        return new Promise((successCallback) => {
            console.log(message);
            this.websocket.send(message);
            successCallback();
        });
    }

    // 监听消息
    onmessage(callback) {
        this.websocket.onmessage = function (message) {
            console.log(message);

            let data = message.data;

            try {
                callback(JSON.parse(data));
            } catch (e) {
                callback(data);
            }
        }
    }

    // 监听关闭
    onclose(callback) {
        this.websocket.onclose = function () {
            callback();
        }
    }

    // 关闭连接
    async close() {
        await this.waitConnect();

        this.websocket.close();
    }

}