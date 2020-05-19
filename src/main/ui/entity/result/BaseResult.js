export default class BaseResult {

    result = {
        channel: "",
        note: ""
    };

    Result() {}

    check() {
        let result = this.result;
        let channel = result.channel;
        let note = result.note;

        if (channel === "") {
            return "channel不能为空";
        }
        if (note === "") {
            return "note不能为空";
        }
        return true;
    }

}