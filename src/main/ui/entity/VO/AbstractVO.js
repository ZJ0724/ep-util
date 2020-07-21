export default class AbstractVO {

    setData(data) {
        for (let key in this) {
            if (this.hasOwnProperty(key)) {
                this[key] = data[key];
            }
        }
    }

}