package com.warchlak.BookStorage;

import com.warchlak.BookStorage.ExceptionHandling.customExceptions.InvalidIdState;
import com.warchlak.BookStorage.ExceptionHandling.customExceptions.MyResourceNotFoundException;
import com.warchlak.BookStorage.entity.Book;
import com.warchlak.BookStorage.mockBeans.entity.MockBookFactory;
import com.warchlak.BookStorage.service.BookService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookServiceTest
{
	private final static int BOOKS_NUMBER = 2;
	
	@Autowired
	private BookService service;
	
	@Test
	public void assert_two_books_found_when_find_all()
	{
		Assert.assertEquals(service.findAll().size(), BOOKS_NUMBER);
	}
	
	@Test
	public void assert_book_found_when_search_by_id()
	{
		Book book = service.findOneById(1);
		Assert.assertNotNull("Service returned null book instead of a book" +
				"when findOneById() performed.", book);
		Assert.assertEquals("Java Book".toLowerCase(), book.getTitle().toLowerCase());
	}
	
	@Test(expected = MyResourceNotFoundException.class)
	public void assert_exception_thrown_when_invalid_id_in_find_by_id()
	{
		service.findOneById(10000000);
	}
	
	@Test(expected = MyResourceNotFoundException.class)
	public void assert_exception_thrown_when_negative_id_in_find_by_id()
	{
		service.findOneById(-1);
	}
	
	@Test
	public void assert_book_saved_correctly()
	{
		Book book = service.save(MockBookFactory.getMockBook());
		Assert.assertEquals(MockBookFactory.getMockBook().getTitle(), book.getTitle());
		Book returnedBook = service.findOneById(book.getId());
		Assert.assertSame(book, returnedBook);
	}
	
	@Test(expected = InvalidIdState.class)
	public void assert_exception_when_saving_book_with_specified_id()
	{
		Book book = MockBookFactory.getMockBook();
		book.setId(100);
		
		service.save(book);
	}
	
	@Test(expected = InvalidIdState.class)
	public void assert_exception_when_saving_book_with_id_already_taken()
	{
		Book book = MockBookFactory.getMockBook();
		book.setId(1);
		
		service.save(book);
	}
	
	@Test
	public void assert_book_updated()
	{
		Book book = service.findOneById(1);
		Assert.assertEquals(book.getTitle().toLowerCase(), "Java Book".toLowerCase());
		
		book.setTitle("Java Book Adjusted");
		book = service.update(book);
		
		Assert.assertEquals(book.getTitle().toLowerCase(), "Java Book Adjusted".toLowerCase());
	}
	
	@Test(expected = MyResourceNotFoundException.class)
	public void assert_exception_when_updating_book_which_does_not_exist()
	{
		Book book = MockBookFactory.getMockBook();
		book.setId(10000);
		service.update(book);
	}
	
	@Test(expected = InvalidIdState.class)
	public void assert_exception_when_updating_book_with_null_id()
	{
		Book book = MockBookFactory.getMockBook();
		service.update(book);
	}
	
	@Test
	public void assert_books_list_size_get_smaller_when_book_deleted()
	{
		service.deleteById(1);
		Assert.assertEquals(service.findAll().size(), BOOKS_NUMBER - 1);
	}
	
	@Test(expected = MyResourceNotFoundException.class)
	public void assert_exception_when_trying_to_access_deleted_book()
	{
		service.deleteById(1);
		service.findOneById(1);
	}
	
	@Test(expected = MyResourceNotFoundException.class)
	public void assert_exception_when_delete_with_invalid_id()
	{
		service.deleteById(10000000);
	}
}
