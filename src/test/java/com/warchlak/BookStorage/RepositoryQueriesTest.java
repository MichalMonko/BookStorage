package com.warchlak.BookStorage;

import com.warchlak.BookStorage.entity.Book;
import com.warchlak.BookStorage.entity.Image;
import com.warchlak.BookStorage.repository.BookRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryQueriesTest
{
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	BookRepository bookRepository;
	
	@Test
	public void contextLoads()
	{
		Assert.assertNotNull(applicationContext);
	}
	
	@Test
	public void assert_one_book_returned_when_proper_id_specified()
	{
		Book book = bookRepository.getOne(0);
		Assert.assertNotNull(book);
	}
	
	@Test
	public void assert_no_book_present_in_optional_when_invalid_id()
	{
		Optional<Book> bookWrapper = bookRepository.findById(10000000);
		Assert.assertFalse("book is found although it should not", bookWrapper.isPresent());
	}
	
	@Test
	public void assert_book_is_present_in_database_after_adding_it()
	{
		Book book = new Book("New Book", "Book that should be added", "Book,New"
				, new Image("placeholder", "placeholder"));
		
		bookRepository.saveAndFlush(book);
		
		Optional<Book> bookWrapper = bookRepository.findById(book.getId());
		Assert.assertTrue(bookWrapper.isPresent());
		Assert.assertEquals(book.getTitle(), bookWrapper.get().getTitle());
	}
	
	
}
