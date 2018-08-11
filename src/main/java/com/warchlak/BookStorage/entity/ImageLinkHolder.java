package com.warchlak.BookStorage.entity;

import javax.persistence.*;

@Entity
public class ImageLinkHolder
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private Book book;
	
	@Column(name = "full_size_path")
	private String imageFullSizePath;
	
	@Column(name = "miniature_size_path")
	private String imageMiniaturePath;
	
	protected ImageLinkHolder(){}
	
	public ImageLinkHolder(Book book, String imageFullSizePath, String imageMiniaturePath)
	{
		this.book = book;
		this.imageFullSizePath = imageFullSizePath;
		this.imageMiniaturePath = imageMiniaturePath;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public Book getBook()
	{
		return book;
	}
	
	public void setBook(Book book)
	{
		this.book = book;
	}
	
	public String getImageFullSizePath()
	{
		return imageFullSizePath;
	}
	
	public void setImageFullSizePath(String imageFullSizePath)
	{
		this.imageFullSizePath = imageFullSizePath;
	}
	
	public String getImageMiniaturePath()
	{
		return imageMiniaturePath;
	}
	
	public void setImageMiniaturePath(String imageMiniaturePath)
	{
		this.imageMiniaturePath = imageMiniaturePath;
	}
}
