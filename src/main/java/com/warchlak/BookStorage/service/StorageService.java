package com.warchlak.BookStorage.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService
{
	void store(MultipartFile file);
}
