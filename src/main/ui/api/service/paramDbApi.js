import baseServiceApi from "./baseServiceApi.js";

export default {
    url: "paramDb2.0",

    importComparator(groupName, file) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/importComparator`,
            data: {
                groupName: groupName,
                file: file
            },
            dataType: "file"
        });
    },

    exportComparator(groupName, file) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/exportComparator`,
            data: {
                groupName: groupName,
                file: file
            },
            dataType: "file"
        });
    }
}