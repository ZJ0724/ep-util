import BaseWebsocketApi from "./BaseWebsocketApi.js";

export default class FormCusMessageComparisonWebsocketApi extends BaseWebsocketApi {

    constructor(id) {
        super("formCusMessageComparison/" + id);
    }

}