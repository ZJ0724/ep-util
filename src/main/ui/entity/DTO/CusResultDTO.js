(function (DTO) {

    DTO.CusResultDTO = class CusResultDTO {

        channel = "";

        note = "";

        // 校验
        check(callback) {
            if (this.channel === "") {
                callback("channel不能为空");
                return false;
            }

            if (this.note === "") {
                callback("note不能为空");
                return false;
            }

            return true;
        }

    }

})(window.epUtil.entity.DTO);