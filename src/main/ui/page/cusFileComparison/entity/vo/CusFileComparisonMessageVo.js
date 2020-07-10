(function (vo) {
    vo.CusFileComparisonMessageVo = class CusFileComparisonMessageVo {

        type;

        node;

        message;

        constructor(data) {
            this.type = data.type;
            this.node = data.node;
            this.message = data.message;
        }

    }
})(window.epUtil.entity.vo);