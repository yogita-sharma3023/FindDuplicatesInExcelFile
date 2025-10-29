package com.vkt.findDuplicates.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vkt.findDuplicates.exception.CustomException;
import com.vkt.findDuplicates.exception.ErrorDetail;
import com.vkt.findDuplicates.exception.ExcelValidationException;
import com.vkt.findDuplicates.model.Employee;

@Service
public class ExcelReader {
	@Autowired
    private DuplicateFinder duplicateFinder;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public List<Employee> readExcelFile(MultipartFile file) throws IOException {

        List<Employee> employees = new ArrayList<>();
        DataFormatter formatter = new DataFormatter(); 

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            Map<String, Integer> columnIndexMap = new HashMap<>();
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                columnIndexMap.put(headerRow.getCell(i).getStringCellValue().trim().toLowerCase(), i);
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { 
                Row row = sheet.getRow(i);
                if (row == null) continue;

                
                String idStr = formatter.formatCellValue(row.getCell(columnIndexMap.get("id")));
                String name = formatter.formatCellValue(row.getCell(columnIndexMap.get("name")));
                String ageStr = formatter.formatCellValue(row.getCell(columnIndexMap.get("age")));
                String email = formatter.formatCellValue(row.getCell(columnIndexMap.get("email")));
                String phoneStr = formatter.formatCellValue(row.getCell(columnIndexMap.get("phone_number")));

               
                int id = parseIntSafe(idStr, i, "id");
                int age = parseIntSafe(ageStr, i, "age");
                long phone_number = parseLongSafe(phoneStr, i, "phone_number");

                
                if (email == null || email.trim().isEmpty()) {
                    throw new ExcelValidationException(CustomException.EMAIL,new ErrorDetail(i + 1, "email", "Email is blank"));
                }
                if (!EMAIL_PATTERN.matcher(email).matches()) {
                    throw new ExcelValidationException(CustomException.EMAIL_FORMAT,new ErrorDetail(i + 1, "email", "Email format is wrong"));
                }
                if (phoneStr == null || phoneStr.trim().isEmpty()) {
                    throw new ExcelValidationException(CustomException.PHONE,new ErrorDetail(i + 1, "phone", "Phone is blank"));
                }
                if (!phoneStr.matches("\\d{10}")) {
                    throw new ExcelValidationException(CustomException.PHONE_FORMAT,new ErrorDetail(i + 1, "phone", "Phone is in wrong format"));
                }
                if (name == null || name.trim().isEmpty()) {
                    throw new ExcelValidationException(CustomException.NAME,new ErrorDetail(i + 1, "name", "Name is blank"));
                }

                employees.add(new Employee(id, age, name, email, phone_number));
            }

            workbook.close();
            inputStream.close();

        } catch (Exception e) {
            
            if (e instanceof ExcelValidationException) {
                throw (ExcelValidationException) e;
            }
            throw new RuntimeException("Something went wrong while reading Excel: " + e.getMessage(), e);
        }

        return duplicateFinder.findDuplicates(employees);
    }

    
    private int parseIntSafe(String value, int rowNum, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new ExcelValidationException(CustomException.EMAIL,new ErrorDetail(rowNum + 1 , field, field + " is Blank"));
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ExcelValidationException(CustomException.INVALID_FORMAT,new ErrorDetail(rowNum + 1, field, "Invalid number format → " + value));
        }
    }

    private long parseLongSafe(String value, int rowNum, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new ExcelValidationException(CustomException.PHONE,new ErrorDetail(rowNum + 1 , field, field + " is Blank"));
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            throw new ExcelValidationException(CustomException.INVALID_FORMAT,new ErrorDetail(rowNum + 1, field, "Invalid number format → " + value));
        }
    }
}
