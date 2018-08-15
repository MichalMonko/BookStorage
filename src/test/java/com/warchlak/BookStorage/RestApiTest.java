package com.warchlak.BookStorage;

import com.warchlak.BookStorage.entity.Book;
import com.warchlak.BookStorage.mockBeans.entity.MockBookFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestApiTest
{
	
	@Autowired
	private WebApplicationContext applicationContext;
	
	private MockMvc mockMvc;
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("UTF-8"));
	
	@Before
	public void initializeRestTemplateAndMockRestService()
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
	}
	
	@Test
	public void assert_single_book_returned_when_get_with_id() throws Exception
	{
		mockMvc.perform(get("/api/book/1"))
		       .andDo(print())
		       .andExpect(content().contentType(contentType))
		       .andExpect(jsonPath("id", is(1)))
		       .andExpect(jsonPath("title", is("Java Book")));
	}
	
	@Test
	public void assert_not_found_returned_when_get_with_invalid_id() throws Exception
	{
		mockMvc.perform(get("/api/book/1000000"))
		       .andExpect(status().isNotFound());
	}
	
	@Test
	public void assert_bad_request_when_bad_id_format() throws Exception
	{
		mockMvc.perform(get("/api/book/bookNumber1000"))
		       .andExpect(status().isBadRequest());
	}
	
	@Test
	public void assert_books_returned_in_json_when_get_performed() throws Exception
	{
		mockMvc.perform(get("/api/book/"))
		       .andDo(print())
		       .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		       .andExpect(jsonPath("$", hasSize(2)))
		       .andExpect(jsonPath("$.[0].title", is("Java Book")))
		       .andExpect(jsonPath("$.[1].title", is("Hello World Book")))
		       .andExpect(jsonPath("$.[2]").doesNotExist());
	}
	
	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void assert_return_status_isCreated_when_post_book() throws Exception
	{
		String requestBody = json().build().writeValueAsString(MockBookFactory.getMockBook());
		
		mockMvc.perform((post("/api/book"))
				.content(requestBody)
				.contentType(contentType))
		       .andExpect(status().isCreated());
	}
	
	@Test
	public void assert_badRequest_when_posting_null() throws Exception
	{
		String requestBody = json().build().writeValueAsString(null);
		
		mockMvc.perform((post("/api/book"))
				.content(requestBody)
				.contentType(contentType))
		       .andExpect(status().isBadRequest());
	}
	
	@Test
	public void assert_bad_request_when_put_without_body() throws Exception
	{
		mockMvc.perform(put("/api/book"))
		       .andExpect(status().isBadRequest());
	}
	
	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void assert_ok_when_put_with_book_as_body() throws Exception
	{
		Book book = MockBookFactory.getMockBook();
		book.setTitle("Edited Title");
		book.setId(1);
		
		String jsonBook = json().build().writeValueAsString(book);
		
		mockMvc.perform(put("/api/book")
				.content(jsonBook).contentType(contentType))
		       .andDo(print())
		       .andExpect(status().isOk());
	}
	
	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void assert_not_found_when_put_with_bad_id() throws Exception
	{
		Book book = MockBookFactory.getMockBook();
		book.setId(1000);
		
		String jsonBook = json().build().writeValueAsString(book);
		
		mockMvc.perform(put("/api/book")
				.content(jsonBook).contentType(contentType))
		       .andDo(print())
		       .andExpect(status().isNotFound());
	}
	
	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void assert_status_ok_when_delete_with_id() throws Exception
	{
		mockMvc.perform(delete("/api/book/1"))
		       .andExpect(status().isOk());
	}
	
	@Test
	public void assert_not_found_when_delete_with_invalid_id() throws Exception
	{
		mockMvc.perform(delete("/api/book/23232332"))
		       .andExpect(status().isNotFound());
		
	}
	
	
}
