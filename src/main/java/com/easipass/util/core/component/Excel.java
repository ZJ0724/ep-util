package com.easipass.util.core.component;

import com.easipass.util.core.BaseException;
import com.easipass.util.core.util.StringUtil;
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
 * excel
 *
 * @author ZJ
 * */
public final class Excel {

    /**
     * 数据集合
     * */
    private final List<List<String>> data = new ArrayList<>();

    /**
     * 构造函数
     *
     * @param path 路径
     * @param sheetIndex sheet序号
     * */
    public Excel(String path, int sheetIndex) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(path));
            Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(fileInputStream);
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int index = 0;
            Integer titleSize = null;
            for (Row row : sheet) {
                index++;
                List<String> strings = new ArrayList<>();
                if (index == 1) {
                    for (Cell cell : row) {
                        String cellData = cell.getStringCellValue();
                        if (StringUtil.isEmpty(cellData)) {
                            break;
                        }
                        strings.add(cellData);
                    }
                    titleSize = strings.size();
                } else {
                    if (titleSize != null) {
                        for (int i = 0; i < titleSize; i++) {
                            Cell cell = row.getCell(i);
                            String cellData = "";
                            if (cell != null) {
                                String c = cell.getStringCellValue();
                                if (c != null) {
                                    cellData = c;
                                }
                            }
                            strings.add(cellData);
                        }
                    }
                }
                this.data.add(strings);
            }
        } catch (IOException | org.apache.poi.EmptyFileException e) {
            throw new BaseException(e.getMessage()) {};
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
        return data.size();
    }

    /**
     * 获取单行所有数据
     *
     * @param rowIndex 行号
     *
     * @return 单行所有数据
     * */
    public List<String> getRowData(int rowIndex) {
        if (rowIndex >= data.size()) {
            return new ArrayList<>();
        }

        return data.get(rowIndex);
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
        List<String> rowData = this.getRowData(rowIndex);

        if (cellIndex >= rowData.size()) {
            return null;
        }

        return rowData.get(cellIndex);
    }

    /**
     * 获取所有数据
     *
     * @return 所有数据
     * */
    public List<List<String>> getAllData() {
        return this.data;
    }

}