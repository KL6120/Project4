package com.lunarstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int provideId;
	private int districtId;
	private int wardCode;
	@Column(columnDefinition = "nvarchar(250)")
	private String address;
	@Column(columnDefinition = "nvarchar(500)")
	private String fullAddress;
	private Boolean isDefault;
	private Boolean actived;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	public Address() {
	}

	public Address(Integer id, int provideId, int districtId, int wardCode, String address, String fullAddress,
			Boolean isDefault, Boolean actived, Account account) {
		this.id = id;
		this.provideId = provideId;
		this.districtId = districtId;
		this.wardCode = wardCode;
		this.address = address;
		this.fullAddress = fullAddress;
		this.isDefault = isDefault;
		this.actived = actived;
		this.account = account;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getProvideId() {
		return provideId;
	}

	public void setProvideId(int provideId) {
		this.provideId = provideId;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public int getWardCode() {
		return wardCode;
	}

	public void setWardCode(int wardCode) {
		this.wardCode = wardCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getActived() {
		return actived;
	}

	public void setActived(Boolean actived) {
		this.actived = actived;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
