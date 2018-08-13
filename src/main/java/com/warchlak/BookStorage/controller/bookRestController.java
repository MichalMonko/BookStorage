package com.warchlak.BookStorage.controller;

import com.warchlak.BookStorage.entity.Book;
import com.warchlak.BookStorage.service.BookService;
import com.warchlak.BookStorage.util.NullChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class bookRestController
{
	
	private final BookService bookService;
	
	@Autowired
	public bookRestController(BookService bookService)
	{
		this.bookService = bookService;
	}
	
	@GetMapping
	public List<Book> getAllBooks()
	{
		return bookService.findAll();
	}
	
	@GetMapping("/{id}")
	public Book getBookById(@PathVariable("id") int bookId)
	{
		return bookService.findOneById(bookId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void saveBook(@RequestBody Book book)
	{
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
