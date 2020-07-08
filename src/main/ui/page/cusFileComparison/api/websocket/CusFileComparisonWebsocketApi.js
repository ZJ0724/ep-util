(function (websocket) {
    websocket.CusFileComparisonWebsocketApi = class CusFileComparisonWebsocketApi extends websocket.BaseWebsocketApi {

        constructor(id, ediNo) {
            super("cusFileComparison/" + id + "/" + ediNo);
        }

    };
})(window.epUtil.api.websocket);