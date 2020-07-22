export default class Response {

    flag = null;

    errorCode = null;

    errorMessage = null;

    data = null;

    setData(response) {
        for (let key in this) {
            if (this.hasOwnProperty(key)) {
                this[key] = response[key];
            }
        }
    }

}