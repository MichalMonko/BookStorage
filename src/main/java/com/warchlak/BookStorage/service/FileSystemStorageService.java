package com.warchlak.BookStorage.service;

import com.warchlak.BookStorage.ExceptionHandling.customExceptions.FileStorageException;
import com.warchlak.BookStorage.configuration.EnglishMessageSource;
import com.warchlak.BookStorage.properties.StorageProperties;
import com.warchlak.BookStorage.util.ExtensionExtractor;
import com.warchlak.BookStorage.util.MediaTypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScope
@Service
public class FileSystemStorageService implements StorageService
{
	private final EnglishMessageSource messageSource;
	
	private final Path storageLocation;
	
	
	private Map<String, Long> imageStateMap;
	private static final long TIMEOUT_VALUE_MILLIS = 300000;
	
	@Autowired
	public FileSystemStorageService(EnglishMessageSource messageSource, StorageProperties properties)
	{
		this.messageSource = messageSource;
		this.storageLocation = Paths.get(properties.getStorageLocation()).toAbsolutePath();
		this.imageStateMap = new HashMap<>();
		System.out.println(storageLocation);
	}
	
	@PreDestroy
	void cleanUpTransientImages()
	{
		for (String filename : imageStateMap.keySet())
		{
			try
			{
				delete(filename);
			} catch (IOException e)
			{
				Logger.getLogger(this.getClass().getName()).info("CLEANUP: file " + filename + " cannot be deleted");
			}
		}
	}
	
	@Override
	public String store(MultipartFile file)
	{
		String originalFileName = file.getOriginalFilename();
		if (null == originalFileName)
		{
			throw new FileStorageException(messageSource.getCustomMessage("exception.FileStorageException.emptyFileName"));
		}
		
		String fileExtension = ExtensionExtractor.getExtension(originalFileName);
		if ((null == fileExtension) || !MediaTypeResolver.isImage(fileExtension))
		{
			throw new FileStorageException(messageSource.getCustomMessage("exception.FileStorageException.invalidFileExtension",
					new Object[]{originalFileName}));
		}
		
		String filename = UUID.randomUUID().toString() + "." + fileExtension;
		
		if (file.isEmpty())
		{
			throw new FileStorageException(messageSource.getCustomMessage("exception.FileStorageException.emptyFile",
					new Object[]{originalFileName}));
		}

//		if (file.getName().contains(".."))
//		{
//			throw new FileStorageException(
//					messageSource.getCustomMessage("exception.FileStorageException.outsideDirAccess",
//							new Object[]{originalFileName}));
//		}
		
		try (InputStream inputStream = file.getInputStream())
		{
			Path location = this.storageLocation.resolve(filename);
			Files.copy(inputStream, location);
			
			trackImage(filename);
			
			return filename;
			
		} catch (FileAlreadyExistsException e)
		{
			throw new FileStorageException(messageSource.getCustomMessage("exception.FileStorageException.fileAlreadyExists",
					new Object[]{originalFileName}));
		} catch (IOException e)
		{
			throw new FileStorageException(messageSource.getCustomMessage("exception.FileStorageException.errorWritingToFile",
					new Object[]{originalFileName}));
		}
	}
	
	@Override
	public boolean delete(String filename) throws IOException
	{
		Path fileLocation = storageLocation.resolve(filename);
		Files.delete(fileLocation);
		removeImageFromTracking(filename);
		
		return true;
	}
	
	@Override
	public byte[] getImage(String filename) throws IOException
	{
		Path fileLocation = this.storageLocation.resolve(filename);
		return Files.readAllBytes(fileLocation);
	}
	
	@Scheduled(fixedDelay = TIMEOUT_VALUE_MILLIS)
	void cleanOrphanImages()
	{
		for (Map.Entry entry : imageStateMap.entrySet())
		{
			long currentTimestamp = System.currentTimeMillis();
			if (currentTimestamp - (long) entry.getValue() >= TIMEOUT_VALUE_MILLIS)
			{
				try
				{
					delete((String) entry.getKey());
				} catch (IOException e)
				{
					Logger.getLogger(this.getClass().getName()).info("File " + entry.getKey().toString() + " does not exist");
				}
			}
		}
	}
	
	public void removeImageFromTracking(String filename)
	{
		imageStateMap.remove(filename);
	}
	
	private void trackImage(String filename)
	{
		imageStateMap.put(filename, System.currentTimeMillis());
	}
}
