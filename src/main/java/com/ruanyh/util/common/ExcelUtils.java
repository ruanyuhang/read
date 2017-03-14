package com.ruanyh.util.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel文档处理工具类
 * 读、写
 * 支持2013之后的版本
 */
public class ExcelUtils {
    /**
     * 私有的构造方法,不允许实例化
     */
    private ExcelUtils() {}

    /**
     * 读取Excel
     * @param originalFilePath
     * @return
     */
    public static List<Map<String, String>> read(String originalFilePath) {
        List<Map<String, String>> result = null;
        InputStream is = null;
        try {
            is = new FileInputStream(originalFilePath);
            result = read(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        return result;
    }

    /**
     * 读取Excel
     * @param is
     * @return
     */
    public static List<Map<String, String>> read(InputStream is) {
        List<Map<String, String>> result = new ArrayList<>();

        Workbook workbook = getWorkbook(is);
        int sheetIndex = 0;                         // TODO 第1个sheet页(配置)
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int maxLimit = 100;                         // TODO 最大限制数(配置)
        int rowNum = sheet.getLastRowNum();         // 总行数

        for (int rowIndex = 0; rowIndex <= rowNum; rowIndex++) {
            // TODO 多线程处理
            Map<String, String> rowMap = new HashMap<>();
            Row row = sheet.getRow(rowIndex);
            int cellNum = row.getLastCellNum();     // 行中的单元格个数
            for (int cellIndex = 0; cellIndex < cellNum; cellIndex++) {
                Cell cell = row.getCell(cellIndex);         // 单元格
                String cellValue = cell.getStringCellValue();   // 值
                rowMap.put(String.valueOf(cellIndex), cellValue);
            }
            result.add(rowMap);
        }
        return result;
    }


    /**
     * 写入Excel
     * @param list
     * @param destFilePath
     */
    public static void write(List<Map<String, String>> list, String destFilePath) {
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(destFilePath);
            write(list, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    /**
     * 写入Excel
     * @param list
     * @param stream
     */
    public static void write(List<Map<String, String>> list, OutputStream stream) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        int i = 0;
        for (Map<String, String> map : list) {
            HSSFRow row = sheet.createRow(i);
            int j = 0;
            for (String key : map.keySet()) {
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(map.get(key));
                j++;
            }
            i++;
        }

        try {
            workbook.write(stream);
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }


    private static Workbook getWorkbook(InputStream is) {
        HSSFWorkbook workbook = null;
        try {
            POIFSFileSystem fileSystem = new POIFSFileSystem(is);
            workbook = new HSSFWorkbook(fileSystem);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

}
