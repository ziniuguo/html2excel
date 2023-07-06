package io.catroll.html2excel.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copy different Excel files to one Excel file with different sheets.
 * @author ziniu@catroll.io
 */
public class ExcelFileMerger {

    public static void main(String[] args) {
        String folderPath = "C:\\winWorkspace\\html2csv\\output";
        List<File> inputFiles = collectFilesFromFolder(folderPath);

        String outputFile = "mergedFile.xlsx";

        try (Workbook mergedWorkbook = new XSSFWorkbook()) {
            for (File inputFile : inputFiles) {
                try (Workbook workbook = WorkbookFactory.create(inputFile)) {
                    int numberOfSheets = workbook.getNumberOfSheets();
                    for (int i = 0; i < numberOfSheets; i++) {
                        Sheet sheet = workbook.getSheetAt(i);
                        copySheet(mergedWorkbook, sheet, inputFile);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading input file: " + inputFile);
                    e.printStackTrace();
                }
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                mergedWorkbook.write(fileOutputStream);
                System.out.println("Merged file created successfully!");
            } catch (IOException e) {
                System.out.println("Error writing merged file: " + outputFile);
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Error creating merged workbook.");
            e.printStackTrace();
        }
    }

    private static void copySheet(Workbook destWorkbook, Sheet sourceSheet, File inputFile) {
        String sheetName = sourceSheet.getSheetName();
        // use inputFile's name
        Sheet destSheet = destWorkbook.createSheet(inputFile.getName().substring(0,
                inputFile.getName().length()-5)); // and remove .xlsx

        int rowCount = sourceSheet.getLastRowNum();
        for (int i = 0; i <= rowCount; i++) {
            Row sourceRow = sourceSheet.getRow(i);
            Row destRow = destSheet.createRow(i);

            if (sourceRow != null) {
                int columnCount = sourceRow.getLastCellNum();
                for (int j = 0; j < columnCount; j++) {
                    Cell sourceCell = sourceRow.getCell(j);
                    Cell destCell = destRow.createCell(j);

                    if (sourceCell != null) {
                        CellType cellType = sourceCell.getCellType();
                        destCell.setCellType(cellType);

                        switch (cellType) {
                            case STRING:
                                destCell.setCellValue(sourceCell.getStringCellValue());
                                break;
                            case NUMERIC:
                                destCell.setCellValue(sourceCell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                destCell.setCellValue(sourceCell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                destCell.setCellFormula(sourceCell.getCellFormula());
                                break;
                            default:
                                // Copy cell style and value as is for other types
                                destCell.setCellStyle(sourceCell.getCellStyle());
                                if (sourceCell.getCellComment() != null) {
                                    destCell.setCellComment(sourceCell.getCellComment());
                                }
                                if (sourceCell.getHyperlink() != null) {
                                    destCell.setHyperlink(sourceCell.getHyperlink());
                                }
                                destCell.setCellValue(sourceCell.getStringCellValue());
                                break;
                        }
                    }
                }
            }
        }
    }
    public static List<File> collectFilesFromFolder(String folderPath) {
        List<File> files = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] folderFiles = folder.listFiles();
            if (folderFiles != null) {
                for (File file : folderFiles) {
                    if (file.isFile()) {
                        files.add(file);
                    }
                }
            }
        } else {
            System.out.println("Invalid folder path: " + folderPath);
        }

        return files;
    }
}
