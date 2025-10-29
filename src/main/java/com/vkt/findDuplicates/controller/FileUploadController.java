package com.vkt.findDuplicates.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.MediaType;

import com.vkt.findDuplicates.dto.FlagRequest;
import com.vkt.findDuplicates.dto.SaveDTO;

import com.vkt.findDuplicates.service.SaveService;
import com.vkt.findDuplicates.service.UploadService;

@RestController
@RequestMapping("/api/excel")
public class FileUploadController {
	
	
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private SaveService saveService;
	
	@Value("${file.max-size}")
    private long maxFileSize;
	
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	 public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file, @RequestPart("flag") FlagRequest flag) {
	  
		return uploadService.uploadExcel(file, flag.getFlag());
	  
	  
	}
	

	@PostMapping("/save")
	  public ResponseEntity<?> saveEmployees(@RequestBody SaveDTO saveDTO ){

		return saveService.saveExcel(saveDTO);
	}
}
