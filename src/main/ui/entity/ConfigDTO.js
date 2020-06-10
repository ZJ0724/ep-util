import SwgdDTO from "./config/swgdDTO.js";
import Sftp83DTO from "./config/Sftp83DTO.js";

export default class ConfigDTO {

    swgd = new SwgdDTO();

    sftp83 = new Sftp83DTO();

    // 设置数据
    setData(data) {
        for (let key in this) {
            if (this.hasOwnProperty(key)) {
                this[key].setData(data[key]);
            }
        }
    }

}