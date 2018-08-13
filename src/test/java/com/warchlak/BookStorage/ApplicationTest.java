package com.warchlak.BookStorage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({BookServiceTest.class, RepositoryQueriesTest.class})
@SpringBootTest
public class ApplicationTest
{
	@Test
	public void test_context_load()
	{
	}
}
