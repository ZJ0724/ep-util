import AbstractDTO from "./AbstractDTO.js";

export default class DaKaConfigDTO extends AbstractDTO {

    date;

    week;

    toWorkTime;

    offWorkTime;

    username;

    password;

    setData(data) {
        super.setData(data);

        // 特殊处理date
        let date = this.date;

        if (date !== "" && !Array.isArray(date)) {
            this.date = date.split(",");
        }

        // 特殊处理week
        let week = this.week;

        if (week !== "" && !Array.isArray(week)) {
            this.week = week.split(",");
        }
    }

}