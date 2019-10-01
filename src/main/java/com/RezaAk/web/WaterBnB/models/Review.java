package com.RezaAk.web.WaterBnB.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="reviews")
public class Review {
	
	//private static long incrementingID = 0;
	
	 @Id
	 @GeneratedValue
	 private Long id;
	 
	 @Column
	 @Size(min=1)
	 private String review;
	 
	 @Column
	 @Size(min=1, max=1)
	 private String rating;
	 
	 @ManyToOne(fetch=FetchType.LAZY)
	 @JoinColumn(name="pool_id")
	 private Pool pool;
	 
	 @ManyToOne(fetch=FetchType.LAZY)
	 @JoinColumn(name="author_id")
	 private User author;
	 
	 public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@Column(updatable=false)
	 private Date createdAt;
	 
	 @Column
	 private Date updatedAt;
	 
	 public Review() {
		 //incrementingID++;
		 //this.setId(incrementingID);
	 }
}
