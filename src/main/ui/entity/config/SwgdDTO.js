export default class SwgdDTO {

    url;
    port;
    sid;
    username;
    password;

    // 设置数据
    setData(data) {
        for (let key in this) {
            if (this.hasOwnProperty(key)) {
                this[key] = data[key];
            }
        }
    }

}