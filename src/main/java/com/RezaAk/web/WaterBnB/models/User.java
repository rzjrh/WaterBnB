package com.RezaAk.web.WaterBnB.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;


@Entity
@Table(name="users")
public class User {
 
 @Id
 @GeneratedValue
 private Long id;
 
 @Column
 @Size(min=1)
 private String firstName;
 
 @Column
 @Size(min=1)
 private String lastName;
 
 @Column
 private String email;
 
 @Column
 @Size(min=5, message="Password must be greater than 5 characters")
 private String password;
 
 @Transient
 @Size(min=10, message="Password must be at least 10 characters!")
 private String passwordConfirmation;
 
 @Column(updatable=false)
 private Date createdAt;
 
 @Column
 private Date updatedAt;
 
 @ManyToMany(fetch = FetchType.EAGER)
 @JoinTable(
     name = "users_roles", 
     joinColumns = @JoinColumn(name = "user_id"), 
     inverseJoinColumns = @JoinColumn(name = "role_id"))
 private List<Role> roles;
 
 @OneToMany(mappedBy="host", fetch=FetchType.LAZY)
 private List<Pool> pools;
 
 @OneToMany(mappedBy="author", fetch=FetchType.LAZY)
 private List<Review> reviews;
 
 public User() {}
 
 public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public String getFullName() {
	return this.firstName + " " + this.lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}
 
 public Long getId() {
     return id;
 }
 public void setId(Long id) {
     this.id = id;
 }
 public String getPassword() {
     return password;
 }
 public void setPassword(String password) {
     this.password = password;
 }
 public String getPasswordConfirmation() {
     return passwordConfirmation;
 }
 public void setPasswordConfirmation(String passwordConfirmation) {
     this.passwordConfirmation = passwordConfirmation;
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
 public List<Role> getRoles() {
     return roles;
 }
 public void setRoles(List<Role> list) {
     this.roles = list;
 }
 
 public boolean isAdmin() {
	 for(Role role: this.roles) {
		 if(role.getName().equals("ROLE_ADMIN")) {
			 return true;
		 }
	 }
	 return false;
 }
 
 public boolean isSuperAdmin() {
	 for(Role role: this.roles) {
		 if(role.getName().equals("ROLE_SUPERADMIN")) {
			 return true;
		 }
	 }
	 return false;	 
}
 
 public boolean equals(User other) {
	 return this.id == other.getId();
 }
 
 @PrePersist
 protected void onCreate(){
     this.createdAt = new Date();
 }
 @PreUpdate
 protected void onUpdate(){
     this.updatedAt = new Date();
 }

public List<Pool> getPools() {
	return pools;
}

public void setPools(List<Pool> pools) {
	this.pools = pools;
}

public List<Review> getReviews() {
	return reviews;
}

public void setReviews(List<Review> reviews) {
	this.reviews = reviews;
}


 
 
 
}