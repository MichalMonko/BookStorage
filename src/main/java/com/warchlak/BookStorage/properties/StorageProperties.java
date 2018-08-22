package com.warchlak.BookStorage.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("storage")
public class StorageProperties
{
	private String storageLocation;
	
	public String getStorageLocation()
	{
		return storageLocation;
	}
	
	public void setStorageLocation(String storageLocation)
	{
		this.storageLocation = storageLocation;
	}
}
