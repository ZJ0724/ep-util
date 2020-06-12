export default class Sftp83DTO {

    url;
    port;
    username;
    password;
    uploadPath;

    // 设置数据
    setData(data) {
        for (let key in this) {
            if (this.hasOwnProperty(key)) {
                this[key] = data[key];
            }
        }
    }

}