package com.lunarstore.model;

import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	private int total;
	private int feeShip;
	private int discount;
	private int paymentMethod;
	private int paymentStatus;
	private int status;
	@Column(columnDefinition = "nvarchar(500)")
	private String shipAddress;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	@ManyToOne
	@JoinColumn(name = "voucher_id")
	private Voucher voucher;
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;

	public Order() {

	}

	public Order(Integer id, Date createdAt, int total, int feeShip, int discount, int paymentMethod, int paymentStatus,
			int status, String shipAddress, Account account, Voucher voucher, List<OrderDetail> orderDetails) {
		this.id = id;
		this.createdAt = createdAt;
		this.total = total;
		this.feeShip = feeShip;
		this.discount = discount;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.status = status;
		this.shipAddress = shipAddress;
		this.account = account;
		this.voucher = voucher;
		this.orderDetails = orderDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getFeeShip() {
		return feeShip;
	}

	public void setFeeShip(int feeShip) {
		this.feeShip = feeShip;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
