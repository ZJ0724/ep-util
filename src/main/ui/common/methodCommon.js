import Loading from "../entity/Loading.js";
import popupCommon from "./popupCommon.js";

export default {

    uploadResult(result, callback, elementBlock) {
        let check = result.check();

        if (check !== true) {
            popupCommon.openMsg(check);
            return;
        }

        let loading = new Loading(elementBlock);
        loading.openLoading();
        callback().then(function () {
            popupCommon.openPopup("上传成功");
        }).catch(function (errorMsg) {
            popupCommon.openPopup(errorMsg);
        }).finally(function () {
            loading.closeLoading();
        });
    }

}