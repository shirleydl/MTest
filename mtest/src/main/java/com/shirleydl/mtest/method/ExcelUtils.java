package com.shirleydl.mtest.method;

/**
 * excel解析工具类
 */

import com.shirleydl.mtest.entity.TestCase;
import com.shirleydl.mtest.vo.ExportInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {


    /**
     * 读入excel文件，解析后返回导入信息
     *
     * @param file
     * @throws IOException
     */
    public List<ExportInfo> readExcelToExportInfo(MultipartFile file) throws IOException {
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        List<ExportInfo> list = new ArrayList<>();
        if (workbook != null) {
            //获得当前sheet工作表
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet != null) {
                //循环所有行
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    ExportInfo exportInfo = new ExportInfo();
                    if (isMergedRegion(sheet, rowNum, 1)) {
                        exportInfo.setSystemsName(getMergedRegionValue(sheet, row.getRowNum(), 1));
                    } else {
                        exportInfo.setSystemsName(row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue().trim());
                    }
                    if (isMergedRegion(sheet, rowNum, 2)) {
                        exportInfo.setPageModulesName(getMergedRegionValue(sheet, row.getRowNum(), 2));

                    } else {
                        exportInfo.setPageModulesName(row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue().trim());

                    }
                    if (isMergedRegion(sheet, rowNum, 3)) {
                        exportInfo.setFunctionsName(getMergedRegionValue(sheet, row.getRowNum(), 3));
                    } else {
                        exportInfo.setFunctionsName(row.getCell(3) == null ? "" : row.getCell(3).getStringCellValue().trim());
                    }
                    if (isMergedRegion(sheet, rowNum, 4)) {
                        exportInfo.setTestPointName(getMergedRegionValue(sheet, row.getRowNum(), 4));
                    } else {
                        exportInfo.setTestPointName(row.getCell(4) == null ? "" : row.getCell(4).getStringCellValue().trim());
                    }

                    if (isMergedRegion(sheet, rowNum, 5)) {
                        exportInfo.setPre(getMergedRegionValue(sheet, row.getRowNum(), 5));
                    } else {
                        exportInfo.setPre(row.getCell(5) == null ? "" : row.getCell(5).getStringCellValue().trim());
                    }

                    if (isMergedRegion(sheet, rowNum, 6)) {
                        exportInfo.setStep(getMergedRegionValue(sheet, row.getRowNum(), 6));
                    } else {
                        exportInfo.setStep(row.getCell(6) == null ? "" : row.getCell(6).getStringCellValue().trim());
                    }

                    if (isMergedRegion(sheet, rowNum, 7)) {
                        exportInfo.setExpect(getMergedRegionValue(sheet, row.getRowNum(), 7));
                    } else {
                        exportInfo.setExpect(row.getCell(7) == null ? "" : row.getCell(7).getStringCellValue().trim());
                    }

                    if (!exportInfo.getSystemsName().equals("") && !exportInfo.getPageModulesName().equals("") && !exportInfo.getFunctionsName().equals("") && !exportInfo.getTestPointName().equals("")) {
                        list.add(exportInfo);
                    }
                }
            }
            workbook.close();
        }
        return list;
    }

    /**
     * 读入excel文件，解析后返回需求用例信息
     *
     * @param file
     * @throws IOException
     */
    public List<TestCase> readExcelToTestCase(MultipartFile file, int demandId, int maxSortId) throws IOException {
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        List<TestCase> list = new ArrayList<>();
        if (workbook != null) {
            //获得当前sheet工作表
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet != null) {
                //循环所有行
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    TestCase testCase = new TestCase();
                    if (isMergedRegion(sheet, rowNum, 1)) {
                        testCase.setSystemsName(getMergedRegionValue(sheet, row.getRowNum(), 1));
                    } else {
                        testCase.setSystemsName(row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue().trim());
                    }
                    if (isMergedRegion(sheet, rowNum, 2)) {
                        testCase.setPageModulesName(getMergedRegionValue(sheet, row.getRowNum(), 2));
                    } else {
                        testCase.setPageModulesName(row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue().trim());
                    }
                    if (isMergedRegion(sheet, rowNum, 3)) {
                        testCase.setFunctionsName(getMergedRegionValue(sheet, row.getRowNum(), 3));
                    } else {
                        testCase.setFunctionsName(row.getCell(3) == null ? "" : row.getCell(3).getStringCellValue().trim());
                    }
                    if (isMergedRegion(sheet, rowNum, 4)) {
                        testCase.setTestPointName(getMergedRegionValue(sheet, row.getRowNum(), 4));
                    } else {
                        testCase.setTestPointName(row.getCell(4) == null ? "" : row.getCell(4).getStringCellValue().trim());
                    }

                    if (isMergedRegion(sheet, rowNum, 5)) {
                        testCase.setPre(getMergedRegionValue(sheet, row.getRowNum(), 5));
                    } else {
                        testCase.setPre(row.getCell(5) == null ? "" : row.getCell(5).getStringCellValue().trim());
                    }

                    if (isMergedRegion(sheet, rowNum, 6)) {
                        testCase.setStep(getMergedRegionValue(sheet, row.getRowNum(), 6));
                    } else {
                        testCase.setStep(row.getCell(6) == null ? "" : row.getCell(6).getStringCellValue().trim());
                    }

                    if (isMergedRegion(sheet, rowNum, 7)) {
                        testCase.setExpect(getMergedRegionValue(sheet, row.getRowNum(), 7));
                    } else {
                        testCase.setExpect(row.getCell(7) == null ? "" : row.getCell(7).getStringCellValue().trim());
                    }

                    if (!testCase.getSystemsName().equals("") || !testCase.getPageModulesName().equals("") || !testCase.getFunctionsName().equals("") || !testCase.getTestPointName().equals("") || !testCase.getPre().equals("") || !testCase.getStep().equals("") || !testCase.getExpect().equals("")) {
                        testCase.setSortId(maxSortId + rowNum);
                        testCase.setDemandId(demandId);
                        list.add(testCase);
                    }
                }
            }
            workbook.close();
        }
        return list;
    }


    private Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith("xls")) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    private boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return fCell.getStringCellValue().trim();
                }
            }
        }
        return null;
    }


}