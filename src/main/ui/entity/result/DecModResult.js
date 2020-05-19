import BaseResult from "./BaseResult.js";

export default class decModResult extends BaseResult {

    preEntryId = "";

    decModResult() {}

    check() {
        if (this.preEntryId === "") {
            return "preEntryId不能为空";
        }
        return super.check();
    }

}