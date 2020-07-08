(function (websocket) {
    websocket.CusFileComparisonWebsocketApi = class CusFileComparisonWebsocketApi extends websocket.BaseWebsocketApi {

        constructor(id) {
            super("cusFileComparison/" + id);
        }

    };
})(window.epUtil.api.websocket);