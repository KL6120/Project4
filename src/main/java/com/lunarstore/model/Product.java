package com.lunarstore.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@Column(columnDefinition = "nvarchar(50)")
	@NotBlank(message = "Chưa nhập tên sản phẩm")
	String name;
	String slug;
	@Column(columnDefinition = "nvarchar(max)")
	String description;
	@Column(columnDefinition = "nvarchar(255)")
	String image;
	@NotNull(message = "Chưa nhập giá trị sản phẩm")
	@Positive(message = "Số tiền phải lớn hơn 0")
	@Column(nullable = false)
	Integer price;
	@NotNull(message = "Chưa nhập số lượng sản phẩm")
	@Positive(message = "Số lượng phải lớn hơn 0")
	@Column(nullable = false)
	Integer quantity;
	Boolean active;

	@ManyToOne
	@JoinColumn(name = "category_id")
	Category category;
	@OneToMany(mappedBy = "product")
	List<Favourite> favourites;
	@OneToMany(mappedBy = "product")
	List<CartDetail> cartDetails;

	public Product() {

	}

	public Product(Integer id, String name, String slug, String description, String image, Integer price,
			Integer quantity, Boolean active, Category category, List<Favourite> favourites,
			List<CartDetail> cartDetails) {
		this.id = id;
		this.name = name;
		this.slug = slug;
		this.description = description;
		this.image = image;
		this.price = price;
		this.quantity = quantity;
		this.active = active;
		this.category = category;
		this.favourites = favourites;
		this.cartDetails = cartDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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
