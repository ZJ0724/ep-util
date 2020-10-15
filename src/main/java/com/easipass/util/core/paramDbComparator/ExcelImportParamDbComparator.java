package com.easipass.util.core.paramDbComparator;

import com.easipass.util.core.ParamDbComparator;
import com.easipass.util.core.database.SWGDPARADatabase;
import com.easipass.util.core.exception.WarningException;
import com.easipass.util.core.util.ExcelUtil;
import com.easipass.util.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.List;

/**
 * excel导入比对器
 *
 * @author ZJ
 * */
public class ExcelImportParamDbComparator extends ParamDbComparator {

    /**
     * 表名
     * */
    private final String tableName;

    /**
     * excel文件路径
     * */
    private final String excelPath;

    /**
     * 日志
     * */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelImportParamDbComparator.class);

    /**
     * 构造函数
     *
     * @param tableName 表名
     * @param excelPath excel文件路径
     * */
    public ExcelImportParamDbComparator(String tableName, String excelPath) {
        this.tableName = tableName;
        this.excelPath = excelPath;
    }

    @Override
    public ComparisonMessage comparison() {
        ComparisonMessage result = new ComparisonMessage();

        try {
            result.AllTable = 1;

            ComparisonMessage.TableFinishMessage finishMessage = new ComparisonMessage.TableFinishMessage();

            finishMessage.dbName = this.tableName;

            // excel
            ExcelUtil excelUtil = new ExcelUtil(this.excelPath, 0);
            // excel第一行数据
            List<String> oneRowData = excelUtil.getRowData(0);
            // excel一共的行数
            int allRow = excelUtil.getAllRow();

            // mdb字段映射
            List<String> mdbFields = SWGDPARADatabase.getTableFields(this.tableName, "SOURCE_NAME");
            // 数据库字段映射
            List<String> dbFields = SWGDPARADatabase.getTableFields(this.tableName, "TARGET_NAME");

            // 版本
            String version = SWGDPARADatabase.getTableVersion(this.tableName);

            finishMessage.dbCount = SWGDPARADatabase.getTableCount(this.tableName);
            finishMessage.resourceName = new File(this.excelPath).getName();
            finishMessage.resourceCount = allRow - 1;

            this.sendData(result.toString());

            for (int i = 1; i < allRow; i++) {
                String sql = "SELECT * FROM " + SWGDPARADatabase.SWGDPARA + "." + this.tableName + " WHERE 1 = 1 AND PARAMS_VERSION = '" + version + "'";

                for (int j = 0; j < mdbFields.size(); j++) {
                    int cell = -1;

                    for (int n = 0; n < oneRowData.size(); n++) {
                        if (oneRowData.get(n).equals(mdbFields.get(j))) {
                            cell = n;
                            break;
                        }
                    }

                    if (cell == -1) {
                        throw new WarningException("excel未匹配到字段：" + mdbFields.get(j));
                    }

                    // 单个数据
                    String data = excelUtil.getData(i, cell);

                    // null
                    if (StringUtil.isEmptyAll(data)) {
                        continue;
                    }

//                    if (data == null) {
//                        throw new WarningException("存在数据为null");
//                    }

                    data = data.replaceAll("'", "''");

                    // null
                    if (StringUtil.isEmptyAll(data)) {
                        continue;
                    }

                    // \N
                    if ("\\N".equals(data)) {
                        continue;
                    }

                    // 字段类型
                    String fieldType = SWGDPARADatabase.getFieldType(this.tableName, dbFields.get(j));

                    // 兼容日期格式
                    if ("TIMESTAMP".equals(fieldType)) {
                        data = parseDate(data);

                        sql = StringUtil.append(sql, " AND ", dbFields.get(j), " = ", "TO_DATE('" + data + "','yyyy-mm-dd hh24:mi:ss')");
                        continue;
                    }

                    sql = StringUtil.append(sql, " AND ", dbFields.get(j), " = '", data, "'");
                }

                LOGGER.info(sql);

                // 查找数据是否存在
                if (!SWGDPARADatabase.dataIsExist(sql)) {
                    finishMessage.messages.add(sql);
                    finishMessage.flag = false;
                }
            }

            result.finishMessages.add(finishMessage);
            result.currentProgress = "100%";

            if (finishMessage.flag) {
                finishMessage.messages.add("SUCCESS");
            }
        } catch (WarningException e) {
            result.flag = false;
            result.message = e.getMessage();
        }

        LOGGER.info(result.toString());

        this.sendData(result.toString());

        return result;
    }

}