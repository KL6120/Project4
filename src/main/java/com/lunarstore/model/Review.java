package com.lunarstore.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "pro_id")
	private Product product;
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	@Column(name = "rating")
	private Integer rating;
	@Column(name = "comment")
	private String comment;
	@Column(name = "create_at")
	private LocalDateTime createAt = LocalDateTime.now();

	public Review() {
	}

	public Review(Integer id, Product product, Account account, Integer rating, String comment,
			LocalDateTime createAt) {
		super();
		this.id = id;
		this.product = product;
		this.account = account;
		this.rating = rating;
		this.comment = comment;
		this.createAt = createAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

}
