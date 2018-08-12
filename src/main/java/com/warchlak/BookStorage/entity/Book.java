package com.warchlak.BookStorage.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@Column
	private String tags;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonBackReference
	private Image image;
	
	protected Book()
	{
	}
	
	public Book(String title, String description, String tags, Image image)
	{
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.image = image;
	}
	
	public int getId()
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
	
	public void setTags(String tags)
	{
		this.tags = tags;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public void setImage(Image image)
	{
		this.image = image;
	}
}
