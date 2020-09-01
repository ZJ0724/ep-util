import BaseWebsocketApi from "./BaseWebsocketApi.js";

export default class ImportParamDbComparisonWebsocketApi extends BaseWebsocketApi {

    constructor(groupName, fileName) {
        super(`importParamDbComparison/${groupName}/${fileName}`);
    }

}