(function (entity) {
    entity.Response = class Response {
        flag = null;
        errorCode = null;
        errorMessage = null;
        data = null;

        setData(response) {
            for (let key in response) {
                if (response.hasOwnProperty(key)) {
                    this[key] = response[key];
                }
            }
        }
    }
})(window.epUtil.entity);