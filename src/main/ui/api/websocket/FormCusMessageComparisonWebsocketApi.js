(function (websocket) {
    websocket.FormCusMessageComparisonWebsocketApi = class FormCusMessageComparisonWebsocketApi extends websocket.BaseWebsocketApi {

        constructor(id) {
            super("formCusMessageComparison/" + id);
        }

    };
})(window.epUtil.api.websocket);