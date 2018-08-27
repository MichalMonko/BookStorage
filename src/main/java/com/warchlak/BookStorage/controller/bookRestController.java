package com.warchlak.BookStorage.controller;

import com.warchlak.BookStorage.ExceptionHandling.customExceptions.RestBadRequestException;
import com.warchlak.BookStorage.configuration.EnglishMessageSource;
import com.warchlak.BookStorage.entity.Book;
import com.warchlak.BookStorage.service.BookService;
import com.warchlak.BookStorage.util.NullChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class bookRestController
{
	
	private final BookService bookService;
	
	private final EnglishMessageSource messageSource;
	
	@Autowired
	public bookRestController(BookService bookService, EnglishMessageSource messageSource)
	{
		this.bookService = bookService;
		this.messageSource = messageSource;
	}
	
	@GetMapping
	public List<Book> getAllBooks()
	{
		return bookService.findAll();
	}
	
	@GetMapping(params = {"pageNumber", "pageSize"})
	public Page<Book> getPage(@RequestParam("pageNumber") Integer pageNumber,
	                          @RequestParam("pageSize") Integer pageSize)
	{
		boolean isPageRequestValid = (pageNumber != null && pageSize != null);
		if (!isPageRequestValid)
		{
			throw new RestBadRequestException(messageSource.getCustomMessage("exception.page.pageNumberOrPageSizeIsNull"));
		}
		
		return bookService.getPage(PageRequest.of(pageNumber, pageSize));
	}
	
	@GetMapping(params = {"pageNumber", "pageSize", "tags"})
	public Page<Book> getPageWithTags(@RequestParam("pageNumber") Integer pageNumber,
	                                  @RequestParam("pageSize") Integer pageSize,
	                                  @RequestParam("tags") String tags,
	                                  @RequestParam(value = "lookupMethod", required = false, defaultValue = "any") String lookupMethod)
	{
		boolean isPageRequestValid = (pageNumber != null && pageSize != null);
		if (!isPageRequestValid)
		{
			throw new RestBadRequestException(messageSource.getCustomMessage("exception.page.pageNumberOrPageSizeIsNull"));
		}
		if (lookupMethod.toLowerCase().equals("all"))
		{
			return bookService.getPageByAllTags(tags, PageRequest.of(pageNumber, pageSize));
		}
		return bookService.getPageByAnyTag(tags, PageRequest.of(pageNumber, pageSize));
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public Book getBookById(@PathVariable("id") int bookId)
	{
		return bookService.findOneById(bookId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void saveBook(@RequestBody Book book)
	{
		System.out.println(book);
		NullChecker.checkForNull(book);
		bookService.save(book);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public void updateBook(@RequestBody Book book)
	{
		bookService.update(book);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteBook(@PathVariable("id") int bookId)
	{
		bookService.deleteById(bookId);
	}
}
