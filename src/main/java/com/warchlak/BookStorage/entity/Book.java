package com.warchlak.BookStorage.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
public class Book
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@Column(name = "tag")
	@ElementCollection
	@CollectionTable(name = "book_tags", joinColumns = @JoinColumn(name = "book_id"))
	private List<String> tags;
	
	@Column
	private double price;
	
	@Column(name = "image_link")
	@Nullable
	private String imageLink;
	
	protected Book()
	{
	}
	
	public Book(String title, String description, String tags, double price, @Nullable String imageLink)
	{
		this.title = title;
		this.description = description;
		this.tags = new ArrayList<>();
		this.price = price;
		this.imageLink = imageLink;
		
		String[] tagArray = tags.split(",");
		this.tags.addAll(Arrays.asList(tagArray));
		this.tags = this.tags.stream().map(String::toLowerCase).collect(Collectors.toList());
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public void setPrice(double price)
	{
		this.price = price;
	}
	
	public List<String> getTags()
	{
		return tags;
	}
	
	public void setTags(List<String> tags)
	{
		this.tags = tags;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	@Nullable
	public String getImageLink()
	{
		return imageLink;
	}
	
	public void setImageLink(@Nullable String imageLink)
	{
		this.imageLink = imageLink;
	}
}
