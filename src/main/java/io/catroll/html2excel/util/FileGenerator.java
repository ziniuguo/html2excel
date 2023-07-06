package io.catroll.html2excel.util;

import io.catroll.html2excel.Constant;
import io.catroll.html2excel.parse.GeneralParser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class FileGenerator {
    private GeneralParser parser;
    public void setParser(GeneralParser parser) {this.parser = parser;}

    public void generateFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("result");
        Row headerRow = sheet.createRow(0);
        Cell headerCell;
        List<String> splitValues = Arrays.asList((",Title,Publisher,PublishDate,Text,Word Count," +
                "dual,f/r,class,f/r,share,f/r,shares,f/r,weighted,f/r," +
                "voting,f/r,right,f/r,rights,f/r,structure,f/r," +
                "capital,f/r,inflow,f/r,outflow,f/r,dual class,f/r," +
                "weighted voting,f/r,voting right,f/r\n").split(","));
        for (int i = 0; i < splitValues.size(); i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(splitValues.get(i));
        }

        List<String> titleList = parser.parseTitle();
        List<String> publisherList = parser.parsePublisher();
        List<String> dateList = parser.parseDate();
        List<String> wordCountList = parser.parseWordCount();
        List<String> textList = parser.parseText();
        Map<String, List<ArrayList<String>>> frequencyMap = parser.countFrequency();

        for (int i = 0; i < textList.size(); i++) {
            // 100 rows
            Row currRow = sheet.createRow(i + 1);
            Cell currCell;
            // 15 columns
            currCell = currRow.createCell(0);
            currCell.setCellValue(i);
            currCell = currRow.createCell(1);
            currCell.setCellValue(titleList.get(i));
            currCell = currRow.createCell(2);
            currCell.setCellValue(publisherList.get(i));
            currCell = currRow.createCell(3);
            currCell.setCellValue(dateList.get(i));
            currCell = currRow.createCell(4);
            currCell.setCellValue(textList.get(i));
            currCell = currRow.createCell(5, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(wordCountList.get(i)));

            currCell = currRow.createCell(6, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("dual").get(i).get(0)));
            currCell = currRow.createCell(7, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("dual").get(i).get(1)));

            currCell = currRow.createCell(8, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("class").get(i).get(0)));
            currCell = currRow.createCell(9, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("class").get(i).get(1)));

            currCell = currRow.createCell(10, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("share").get(i).get(0)));
            currCell = currRow.createCell(11, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("share").get(i).get(1)));

            currCell = currRow.createCell(12, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("shares").get(i).get(0)));
            currCell = currRow.createCell(13, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("shares").get(i).get(1)));

            currCell = currRow.createCell(14, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("weighted").get(i).get(0)));
            currCell = currRow.createCell(15, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("weighted").get(i).get(1)));

            currCell = currRow.createCell(16, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("voting").get(i).get(0)));
            currCell = currRow.createCell(17, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("voting").get(i).get(1)));

            currCell = currRow.createCell(18, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("right").get(i).get(0)));
            currCell = currRow.createCell(19, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("right").get(i).get(1)));

            currCell = currRow.createCell(20, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("rights").get(i).get(0)));
            currCell = currRow.createCell(21, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("rights").get(i).get(1)));

            currCell = currRow.createCell(22, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("structure").get(i).get(0)));
            currCell = currRow.createCell(23, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("structure").get(i).get(1)));

            currCell = currRow.createCell(24, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("capital").get(i).get(0)));
            currCell = currRow.createCell(25, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("capital").get(i).get(1)));

            currCell = currRow.createCell(26, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("inflow").get(i).get(0)));
            currCell = currRow.createCell(27, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("inflow").get(i).get(1)));

            currCell = currRow.createCell(28, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("outflow").get(i).get(0)));
            currCell = currRow.createCell(29, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("outflow").get(i).get(1)));

            currCell = currRow.createCell(30, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("dual class").get(i).get(0)));
            currCell = currRow.createCell(31, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("dual class").get(i).get(1)));

            currCell = currRow.createCell(32, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("weighted voting").get(i).get(0)));
            currCell = currRow.createCell(33, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("weighted voting").get(i).get(1)));

            currCell = currRow.createCell(34, CellType.NUMERIC);
            currCell.setCellValue(Integer.parseInt(frequencyMap.get("voting right").get(i).get(0)));
            currCell = currRow.createCell(35, CellType.NUMERIC);
            currCell.setCellValue(Double.parseDouble(frequencyMap.get("voting right").get(i).get(1)));

        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "currResult.xlsx";

        FileOutputStream outputStream = new FileOutputStream("output/currResult.xlsx");
        workbook.write(outputStream);
        workbook.close();



        Iterator<ArrayList<String>> dualIterator =
                frequencyMap.get(Constant.KEYWORDS.get(0)).iterator();

    }

}
