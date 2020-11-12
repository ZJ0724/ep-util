import baseServiceApi from "./baseServiceApi.js";

export default {
    url: "paramDb2.0",

    mdbImportComparator(data) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/mdbImportComparator`,
            data: data,
            dataType: "file"
        });
    },

    mdbExportComparator(data) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/mdbExportComparator`,
            data: data,
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
    },

    mdbImport(data) {
        return baseServiceApi.sendHttp({
            type: "POST",
            url: `${this.url}/mdbImport`,
            data: data,
            dataType: "file"
        });
    },

    mdbExport() {
        return baseServiceApi.downLoad(`${this.url}/mdbExport`);
    }
}