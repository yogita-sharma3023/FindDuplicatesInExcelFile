package com.vkt.findDuplicates.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkt.findDuplicates.config.UrlHelperClass;
import com.vkt.findDuplicates.dto.APIResponse;
import com.vkt.findDuplicates.dto.SaveDTO;
import com.vkt.findDuplicates.exception.CustomException;
import com.vkt.findDuplicates.exception.ExcelValidationException;
import com.vkt.findDuplicates.exception.ServiceException;
import com.vkt.findDuplicates.model.Employee;

@Service
public class UploadService {

	@Autowired
	private ExcelReader excelReader;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Value("${file.max-size}")
    private long maxFileSize;
	 
	@Autowired
	private UrlHelperClass urlHelperClass;
	
	public ResponseEntity<?> uploadExcel(MultipartFile file, String flag){
		
		 
	            String fileName = file.getOriginalFilename();
	            System.out.println(fileName);

	            if (file.isEmpty()) {
	                System.out.println("Error: File is empty");
	                throw new ServiceException(CustomException.EMPTY);
	            }

	            if (fileName == null || !(fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))) {
	                System.out.println("Error: Invalid file type");
	                throw new ServiceException(CustomException.FILE_TYPE);
	            }

	            if (file.getSize() > maxFileSize) {
	            	throw new ServiceException(CustomException.SIZE);
	                
	            }
	            try {
	            List<Employee> employees = excelReader.readExcelFile(file);

	            if (employees.isEmpty()) {
	                System.out.println("All records are duplicate inside Excel. Nothing to save.");
	                throw new ServiceException(CustomException.DUPLICATE);
	            }

	            SaveDTO saveDTO = new SaveDTO();
	            saveDTO.setEmployee(employees);
	            saveDTO.setFlag(flag);
	            
	            
	            String url = urlHelperClass.getBaseUrl() + "/api/excel/save?flag=" + saveDTO.getFlag();
	            System.out.println("Flag: " + saveDTO.getFlag());

	            try {
	                ResponseEntity<APIResponse> response = restTemplate.postForEntity(url, saveDTO, APIResponse.class);
	                return response;
	            } catch (HttpClientErrorException | HttpServerErrorException ex) {
	                
	                String responseBody = ex.getResponseBodyAsString();
	                	                ObjectMapper mapper = new ObjectMapper();
	                APIResponse apiResponse = mapper.readValue(responseBody, APIResponse.class);
	                return ResponseEntity.status(apiResponse.getCode()).body(apiResponse);
	            }

	        } catch (ExcelValidationException e) {
	            throw e;  
	        } catch (Exception e) {
	            throw new RuntimeException(e.getMessage(), e);
	        }
	    }
	}

