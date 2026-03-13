package com.lunarstore.model;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "voucher")
public class Voucher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@NotBlank(message="Chưa nhập mã giảm giá")
	String code;
	@NotNull(message = "Chưa nhập % giảm giá")
	@Range(min = 1, max = 50, message = "Giá trị giảm giá chỉ được từ 1 - 50%")
	Integer discountPercent;
	@NotNull(message = "Chưa nhập số lượng giảm giá")
	@Positive(message = "Số lượng phải > 0")
	Integer quantity;
	
	@Temporal(TemporalType.DATE)
	Date createdAt;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Chưa nhập ngày bắt đầu")
	Date startedAt;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Chưa nhập ngày kết thúc")
	Date endAt;
	Boolean actived;
	@OneToMany(mappedBy = "voucher")
	List<Order> orders;
	
	public Voucher() {
		
	}

	public Voucher(Integer id, String code, Integer discountPercent, Integer quantity, Date createdAt, Date startedAt,
			Date endAt, Boolean actived, List<Order> orders) {
		this.id = id;
		this.code = code;
		this.discountPercent = discountPercent;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.startedAt = startedAt;
		this.endAt = endAt;
		this.actived = actived;
		this.orders = orders;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public Boolean getActived() {
		return actived;
	}

	public void setActived(Boolean actived) {
		this.actived = actived;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
}
