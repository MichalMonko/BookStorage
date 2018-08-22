package com.warchlak.BookStorage.controller;

import com.warchlak.BookStorage.configuration.EnglishMessageSource;
import com.warchlak.BookStorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/images/")
public class FileAccessController
{
	private final StorageService storageService;
	private final EnglishMessageSource messageSource;
	
	@Autowired
	public FileAccessController(StorageService storageService, EnglishMessageSource messageSource)
	{
		this.storageService = storageService;
		this.messageSource = messageSource;
	}
	
	@GetMapping
	public String showUploadFileForm()
	{
		return "uploadForm_temp";
	}
	
	@PostMapping
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file)
	{
		storageService.store(file);
		
		return new ResponseEntity<>(messageSource.getCustomMessage("success.fileSaved",
				new Object[]{file.getOriginalFilename()}), HttpStatus.OK);
	}
}
