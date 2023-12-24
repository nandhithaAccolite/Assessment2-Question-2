package com.example.demo;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadExcel {
    public static List<DataModel> readExcel() throws IOException {
        List<DataModel> interviewData=new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\linga.nandhitha\\Downloads\\trial2\\demo\\src\\main\\resources\\FinalAccolite.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            for (int n = 1; n < sheet.getPhysicalNumberOfRows(); n++) {
                Row row = sheet.getRow(n);
                DataModel data = new DataModel();
                int i = row.getFirstCellNum();


                data.setDate(dataFormatter.formatCellValue(row.getCell(i)));
                data.setMonth( dataFormatter.formatCellValue(row.getCell(++i)));
                data.setTeam(dataFormatter.formatCellValue(row.getCell(++i)));
                data.setPanelName(dataFormatter.formatCellValue(row.getCell(++i)));
                data.setRound(dataFormatter.formatCellValue(row.getCell(++i)));
                data.setSkill(dataFormatter.formatCellValue(row.getCell(++i)));
                data.setTime(dataFormatter.formatCellValue(row.getCell(++i)));
                data.setCandidateCurrentLocation(dataFormatter.formatCellValue(row.getCell(++i)));
                data.setCandidatePreferredLocation(dataFormatter.formatCellValue(row.getCell(++i)));
                data.setCandidateName(dataFormatter.formatCellValue(row.getCell(++i)));
                interviewData.add(data);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return interviewData;
    }
}
