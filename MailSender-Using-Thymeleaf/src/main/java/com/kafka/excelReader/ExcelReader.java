package com.kafka.excelReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.kafka.entity.EmailData;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ExcelReader {
	
	
	  public static List<EmailData> readExcelData(MultipartFile eFile) throws IOException {
	        List<EmailData> emailDataList = new ArrayList<>();
	        Workbook workbook = WorkbookFactory.create(eFile.getInputStream());
	        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	            Row row = sheet.getRow(i);
	            if (row != null) {
	                String name = row.getCell(0).getStringCellValue();
	                String email = row.getCell(1).getStringCellValue();
	               
	                String location = row.getCell(2).getStringCellValue();
	               

	                emailDataList.add(new EmailData(name, email));
	            }
	        }
	        workbook.close();
	        return emailDataList;
	    }

	
}
