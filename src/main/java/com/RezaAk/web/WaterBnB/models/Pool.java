package com.RezaAk.web.WaterBnB.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name="pools")
public class Pool {
	 @Id
	 @GeneratedValue
	 private Long id;
	 
	 @Column
	 @Size(min=1)
	 private String address;
	 
	 @Column
	 @Size(min=1)
	 private String description;
	 
	 @Column
	 @Size(min=1)
	 private String size;
	 
	 @Column
	 @Min(0)
	 private int nightlyCost;
	 
	 @OneToMany(mappedBy="pool", fetch=FetchType.LAZY)
	 private List<Review> reviews;
	 
	 @ManyToOne(fetch=FetchType.LAZY)
	 @JoinColumn(name="host_id")
	 private User host;
	 
	 @Column(updatable=false)
	 private Date createdAt;
	 
	 @Column
	 private Date updatedAt;
	 
	 public Pool() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getNightlyCost() {
		return nightlyCost;
	}

	public void setNightlyCost(int nightlyCost) {
		this.nightlyCost = nightlyCost;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
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

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAverageRating() {
		double numReviews = (double) this.reviews.size();
		if(numReviews == 0) {
			return 0.0;
		}
		else {
			double sum = 0.00;
			for(int i = 0; i < numReviews; i++) {
				sum += Double.parseDouble(this.reviews.get(i).getRating());
			}
			return sum / numReviews;
		}
	}
	
}
