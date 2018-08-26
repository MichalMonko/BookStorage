package com.warchlak.BookStorage.util;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class MediaTypeResolver
{
	private final static Map<String, MediaType> mediaTypesMap;
	
	static
	{
		mediaTypesMap = new HashMap<>();
		mediaTypesMap.put("jpg", MediaType.IMAGE_PNG);
		mediaTypesMap.put("png", MediaType.IMAGE_PNG);
		mediaTypesMap.put("gif", MediaType.IMAGE_GIF);
		mediaTypesMap.put("JPG", MediaType.IMAGE_PNG);
		mediaTypesMap.put("PNG", MediaType.IMAGE_PNG);
		mediaTypesMap.put("GIF", MediaType.IMAGE_GIF);
	}
	
	private MediaTypeResolver()
	{
	}
	
	public static MediaType getMediaType(String filename)
	{
		String extension = ExtensionExtractor.getExtension(filename);
		return mediaTypesMap.get(extension);
	}
	
	public static boolean isImage(String fileExtension)
	{
		return mediaTypesMap.containsKey(fileExtension);
	}
}
