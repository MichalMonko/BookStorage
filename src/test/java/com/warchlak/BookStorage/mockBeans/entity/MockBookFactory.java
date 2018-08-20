package com.warchlak.BookStorage.mockBeans.entity;

import com.warchlak.BookStorage.entity.Book;

public class MockBookFactory
{
	public static Book getMockBook()
	{
		return new Book("New Book", "Book that should be added", "Book,New"
				, 100.00,"http://www.examplebook.com/books/book1.png");
	}
}
