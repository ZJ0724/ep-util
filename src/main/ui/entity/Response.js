export default class Response {

    flag;
    errorCode;
    errorMsg;
    data;

    Response() {}

    setData(response) {
        for (let key in this) {
            this[key] = response[key];
        }
    }

}