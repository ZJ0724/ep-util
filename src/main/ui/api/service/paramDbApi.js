import baseServiceApi from "./baseServiceApi.js";

export default {
    url: "paramDb2.0",

    mdbImportComparator(groupName, file) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/mdbImportComparator`,
            data: {
                groupName: groupName,
                file: file
            },
            dataType: "file"
        });
    },

    mdbExportComparator(groupName, file) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/mdbExportComparator`,
            data: {
                groupName: groupName,
                file: file
            },
            dataType: "file"
        });
    },

    excelImportComparator(tableName, file) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/excelImportComparator`,
            data: {
                tableName: tableName,
                file: file
            },
            dataType: "file"
        });
    },

    excelImport(tableName, file) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/excelImport`,
            data: {
                tableName: tableName,
                file: file
            },
            dataType: "file"
        });
    }
}