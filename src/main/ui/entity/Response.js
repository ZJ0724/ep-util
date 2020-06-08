export default class Response {

    flag;
    errorCode;
    errorMessage;
    data;

    Response() {}

    setData(response) {
        for (let key in response) {
            if (response.hasOwnProperty(key)) {
                this[key] = response[key];
            }
        }
    }

}