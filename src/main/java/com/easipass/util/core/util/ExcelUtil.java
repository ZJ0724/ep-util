package com.easipass.util.core.util;

import com.easipass.util.core.exception.WarningException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
     * xssfSheet
     * */
    private final XSSFSheet xssfSheet;

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
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);

            this.xssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
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
        return this.xssfSheet.getLastRowNum() + 1;
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

        int allCell = this.xssfSheet.getRow(rowIndex).getLastCellNum();

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
        XSSFRow xssfRow = this.xssfSheet.getRow(rowIndex);

        if (xssfRow == null) {
            return null;
        }

        XSSFCell xssfCell = xssfRow.getCell(cellIndex);

        if (xssfCell == null) {
            return null;
        }

        return xssfCell.getStringCellValue();
    }

}