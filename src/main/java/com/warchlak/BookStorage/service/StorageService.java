package com.warchlak.BookStorage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService
{
	String store(MultipartFile file);
	
	boolean delete(String filename) throws IOException;
}
