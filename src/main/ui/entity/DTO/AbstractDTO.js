(function (DTO) {
    DTO.AbstractDTO = class AbstractDTO {

        setData(data) {
            for (let key in this) {
                if (this.hasOwnProperty(key)) {
                    this[key] = data[key];
                }
            }
        }

    }
})(window.epUtil.entity.DTO);