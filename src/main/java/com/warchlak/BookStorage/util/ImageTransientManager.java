package com.warchlak.BookStorage.util;

import com.warchlak.BookStorage.service.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class ImageTransientManager
{
	private Map<String, Long> imageStateMap;
	private static final long TIMEOUT_VALUE_MILLIS = 5000;
	
	private FileSystemStorageService fileSystemStorageService;
	
	@Autowired
	public ImageTransientManager(FileSystemStorageService fileSystemStorageService)
	{
		this.fileSystemStorageService = fileSystemStorageService;
		this.imageStateMap = new HashMap<>();
	}
	
	@Scheduled(fixedDelay = TIMEOUT_VALUE_MILLIS)
	private void cleanOrphanImages()
	{
		for (Map.Entry entry : imageStateMap.entrySet())
		{
			long currentTimestamp = System.currentTimeMillis();
			if (currentTimestamp - (long) entry.getValue() >= TIMEOUT_VALUE_MILLIS)
			{
				try
				{
					fileSystemStorageService.delete((String) entry.getKey());
					imageStateMap.entrySet().remove(entry);
				} catch (IOException e)
				{
					Logger.getLogger(this.getClass().getName()).info("File " + entry.getKey().toString() + " does not exist");
				}
			}
		}
	}
	
	public boolean removeImage(String filename) throws IOException
	{
		fileSystemStorageService.delete(filename);
		return (imageStateMap.remove(filename) != null);
	}
	
	public void trackImage(String filename)
	{
		imageStateMap.put(filename, System.currentTimeMillis());
	}
}
