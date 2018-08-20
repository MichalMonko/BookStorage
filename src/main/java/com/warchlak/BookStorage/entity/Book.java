package com.warchlak.BookStorage.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;

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
	
	@Column
	private String tags;
	
	@Column
	private double price;
	
	@Column(name = "image_link")
	@Nullable
	private String imageLink;
	
	protected Book()
	{
	}
	
	public Book(String title, String description, String tags,double price, @Nullable String imageLink)
	{
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.price = price;
		this.imageLink = imageLink;
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
	
	public String getTags()
	{
		return tags;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public void setPrice(double price)
	{
		this.price = price;
	}
	
	public void setTags(String tags)
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
