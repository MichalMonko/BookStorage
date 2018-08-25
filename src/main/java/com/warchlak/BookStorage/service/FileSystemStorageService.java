package com.warchlak.BookStorage.service;

import com.warchlak.BookStorage.ExceptionHandling.customExceptions.FileStorageException;
import com.warchlak.BookStorage.configuration.EnglishMessageSource;
import com.warchlak.BookStorage.properties.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService
{
	private final EnglishMessageSource messageSource;
	
	private final Path storageLocation;
	
	@Autowired
	public FileSystemStorageService(EnglishMessageSource messageSource, StorageProperties properties)
	{
		this.messageSource = messageSource;
		this.storageLocation = Paths.get(properties.getStorageLocation()).toAbsolutePath();
		System.out.println(storageLocation);
	}
	
	@Override
	public String store(MultipartFile file)
	{
		String filename = UUID.randomUUID().toString();
		
		if (file.isEmpty())
		{
			throw new FileStorageException(messageSource.getCustomMessage("exception.FileStorageException.emptyFile",
					new Object[]{filename}));
		}
		
		if (file.getName().contains(".."))
		{
			throw new FileStorageException(
					messageSource.getCustomMessage("exception.FileStorageException.outsideDirAccess",
							new Object[]{filename}));
		}
		
		try (InputStream inputStream = file.getInputStream())
		{
			Path location = this.storageLocation.resolve(filename);
			Files.copy(inputStream, location);
			return filename;
		} catch (FileAlreadyExistsException e)
		{
			throw new FileStorageException(messageSource.getCustomMessage("exception.FileStorageException.fileAlreadyExists",
					new Object[]{filename}));
		} catch (IOException e)
		{
			throw new FileStorageException(messageSource.getCustomMessage("exception.FileStorageException.errorWritingToFile",
					new Object[]{filename}));
		}
	}
	
	@Override
	public boolean delete(String filename) throws IOException
	{
		Path fileLocation = storageLocation.resolve(filename);
		Files.delete(fileLocation);
		return true;
	}
}
