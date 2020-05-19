import BaseResult from "./BaseResult.js";

export default class FormResult extends BaseResult {

    ediNo = "";

    FormResult() {}

    check() {
        if (this.ediNo === "") {
            return "ediNo不能为空";
        }
        return super.check();
    }

}