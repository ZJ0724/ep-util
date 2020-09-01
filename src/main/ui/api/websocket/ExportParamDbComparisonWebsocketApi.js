import BaseWebsocketApi from "./BaseWebsocketApi.js";

export default class ExportParamDbComparisonWebsocketApi extends BaseWebsocketApi {

    constructor(groupName, fileName) {
        super(`exportParamDbComparison/${groupName}/${fileName}`);
    }

}