package com.easipass.util.core.util;

import com.easipass.util.core.exception.WarningException;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * excel工具
 *
 * @author ZJ
 * */
public final class ExcelUtil {

    /**
     * sheet
     * */
    private final Sheet sheet;

    /**
     * 构造函数
     *
     * @param path 路径
     * @param sheetIndex sheet序号
     * */
    public ExcelUtil(String path, int sheetIndex) throws WarningException {
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(new File(path));
            Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(fileInputStream);
            this.sheet = workbook.getSheetAt(sheetIndex);
        } catch (IOException | org.apache.poi.EmptyFileException e) {
            throw new WarningException(e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取总行数
     *
     * @return 总行数
     * */
    public int getAllRow() {
        return this.sheet.getLastRowNum() + 1;
    }

    /**
     * 获取单行所有数据
     *
     * @param rowIndex 行号
     *
     * @return 单行所有数据
     * */
    public List<String> getRowData(int rowIndex) {
        List<String> result = new ArrayList<>();

        int allCell = this.sheet.getRow(rowIndex).getLastCellNum();

        for (int i = 0; i < allCell; i++) {
            result.add(this.getData(rowIndex, i));
        }

        return result;
    }

    /**
     * 获取数据
     *
     * @param rowIndex 行号
     * @param cellIndex 列号
     *
     * @return 数据
     * */
    public String getData(int rowIndex, int cellIndex) {
        Row row = this.sheet.getRow(rowIndex);

        if (row == null) {
            return null;
        }

        Cell cell = row.getCell(cellIndex);

        if (cell == null) {
            return null;
        }

        return cell.getStringCellValue();
    }

}