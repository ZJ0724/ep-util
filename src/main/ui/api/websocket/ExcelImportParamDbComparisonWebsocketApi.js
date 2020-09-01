import BaseWebsocketApi from "./BaseWebsocketApi.js";

export default class ExcelImportParamDbComparisonWebsocketApi extends BaseWebsocketApi {

    constructor(tableName, fileName) {
        super(`excelImportParamDbComparison/${tableName}/${fileName}`);
    }

}