export default class Config {

    sWGDUrl = "";

    sWGDPort = "";

    sWGDSid = "";

    sWGDUsername = "";

    sWGDPassword = "";

    sftp83Url = "";

    sftp83Port = "";

    sftp83Username = "";

    sftp83Password = "";

    sftp83UploadPath = "";

    // 设置数据
    setData(data) {
        for (let key in data) {
            if (data.hasOwnProperty(key)) {
                this[key] = data[key];
            }
        }
    }

}