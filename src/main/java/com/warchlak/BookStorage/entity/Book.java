package com.warchlak.BookStorage.entity;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@Column
	private String tags;
	
	@OneToOne(mappedBy = "book")
	private ImageLinkHolder imageLinkHolder;
	
	protected Book()
	{
	}
	
	public Book(String title, String description, String tags, ImageLinkHolder imageLinkHolder)
	{
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.imageLinkHolder = imageLinkHolder;
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
	
	public ImageLinkHolder getImageLinkHolder()
	{
		return imageLinkHolder;
	}
	
	public void setImageLinkHolder(ImageLinkHolder imageLinkHolder)
	{
		this.imageLinkHolder = imageLinkHolder;
	}
}
