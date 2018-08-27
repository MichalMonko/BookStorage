package com.warchlak.BookStorage.controller;

import com.warchlak.BookStorage.ExceptionHandling.customExceptions.FileStorageException;
import com.warchlak.BookStorage.ExceptionHandling.customExceptions.MyResourceNotFoundException;
import com.warchlak.BookStorage.configuration.EnglishMessageSource;
import com.warchlak.BookStorage.service.StorageService;
import com.warchlak.BookStorage.util.MediaTypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
	@ResponseBody
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file)
	{
		String filename = storageService.store(file);
		
		return new ResponseEntity<>(filename, HttpStatus.OK);
	}
	
	@GetMapping("{filename:.+}")
	@ResponseBody
	public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename)
	{
		try
		{
			HttpHeaders headers = new HttpHeaders();
			MediaType contentType = MediaTypeResolver.getMediaType(filename);
			
			if (contentType == null)
			{
				throw new FileStorageException(messageSource.getCustomMessage("exception.FileStorageException.invalidFileExtension",
						new Object[]{filename}));
			}
			headers.setContentType(contentType);
			headers.setCacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES));
			
			byte[] imageBytes = storageService.getImage(filename);
			return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
		} catch (IOException e)
		{
			throw new MyResourceNotFoundException(messageSource.getCustomMessage("exception.ResourceNotFound"));
		}
	}
	
	
}
