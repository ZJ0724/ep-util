import BaseWebsocketApi from "./BaseWebsocketApi.js";

export default class DecModCusMessageComparisonWebsocketApi extends BaseWebsocketApi {

    constructor(id) {
        super("decModCusMessageComparison/" + id);
    }

}