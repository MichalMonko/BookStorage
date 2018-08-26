package com.warchlak.BookStorage.util;

public class ExtensionExtractor
{
	public static String getExtension(String filename)
	{
		if (filename != null)
		{
			String[] fileNameSplit = filename.split("\\.");
			if (fileNameSplit.length != 2)
			{
				return null;
			}
			else
			{
				return fileNameSplit[1];
			}
		}
		return null;
	}
}
