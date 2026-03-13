package com.lunarstore.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String email;
	String password;
	@Column(columnDefinition = "nvarchar(50)")
	String fullname;
	Boolean gender;
	Boolean admin;
	
	public Account(Integer id, String email, String password, String fullname, Boolean gender, Boolean admin,
			List<Address> addresses, List<Order> orders, List<Favourite> favourites, List<CartDetail> cartDetails) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.gender = gender;
		this.admin = admin;
		this.addresses = addresses;
		this.orders = orders;
		this.favourites = favourites;
		this.cartDetails = cartDetails;
	}
	public Account() {
	
	}
	
	@OneToMany(mappedBy = "account")
	List<Address> addresses;
	@OneToMany(mappedBy = "account")
	List<Order> orders;
	@OneToMany(mappedBy = "account")
	List<Favourite> favourites;
	@OneToMany(mappedBy = "account")
	List<CartDetail> cartDetails;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public List<Favourite> getFavourites() {
		return favourites;
	}
	public void setFavourites(List<Favourite> favourites) {
		this.favourites = favourites;
	}
	public List<CartDetail> getCartDetails() {
		return cartDetails;
	}
	public void setCartDetails(List<CartDetail> cartDetails) {
		this.cartDetails = cartDetails;
	}
	
}
