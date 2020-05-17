import maskUtil from "./maskUtil.js";

export default {

    uploadResult(result, callback) {
        let check = result.check();

        if (check !== true) {
            alert(check);
            return;
        }
        maskUtil.openMainMask();
        callback().then(function () {
            alert("上传成功");
        }).catch(function (errorMsg) {
            alert(errorMsg);
        }).finally(function () {
            maskUtil.closeMainMask();
        });
    }

}