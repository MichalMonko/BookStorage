package com.warchlak.BookStorage.repository;

import com.warchlak.BookStorage.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer>
{
	List<Book> findAllByTitleIgnoreCase(String title);
	
	Page<Book> findDistinctBooksByTagsInIgnoreCase(List<String> requestedTags, Pageable pageable);

//	Page<Book> findBooksByAllTags(@Param("tags") String tags, Pageable pageRequest);
	
	@Query(value = "SELECT book.* FROM book_tags JOIN book ON book_tags.book_id = book.id WHERE book_tags.tag IN (:tags) " +
			"GROUP BY book.id HAVING COUNT(*)=:tagsNumber",
			countQuery = "SELECT count(book) FROM book_tags JOIN book ON book_tags.book_id = book.id WHERE book_tags.tag IN (:tags) " +
					"GROUP BY book.id HAVING COUNT(*)=:tagsNumber"
			, nativeQuery = true)
	Page<Book> findBooksMatchingAllTags(@Param("tags") List<String> tagList, @Param("tagsNumber") Long tagsNumber, Pageable pageable);
	
}
