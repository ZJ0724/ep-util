import BaseWebsocketApi from "./BaseWebsocketApi";

export default class ImportParamDbComparisonWebsocketApi extends BaseWebsocketApi {

    constructor(groupName, fileName) {
        super(`importParamDbComparison/${groupName}/${fileName}`);
    }

}