package com.warchlak.BookStorage.mockBeans.entity;

import com.warchlak.BookStorage.entity.Book;
import com.warchlak.BookStorage.entity.Image;

public class MockBookFactory
{
	public static Book getMockBook()
	{
		return new Book("New Book", "Book that should be added", "Book,New"
				, new Image("placeholder", "placeholder"));
	}
}
