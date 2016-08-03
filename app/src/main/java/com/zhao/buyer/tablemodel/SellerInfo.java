package com.zhao.buyer.tablemodel;

/**
 * SellerInfo entity. @author MyEclipse Persistence Tools
 */

public class SellerInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String account;
	private String address;
	private String telephone;
	private String name;
	private String idNumber;
	private Integer shopId;

	// Constructors

	/** default constructor */
	public SellerInfo() {
	}

	/** minimal constructor */
	public SellerInfo(String account, String address, String telephone,
			String name, String idNumber) {
		this.account = account;
		this.address = address;
		this.telephone = telephone;
		this.name = name;
		this.idNumber = idNumber;
	}

	/** full constructor */
	public SellerInfo(String account, String address, String telephone,
			String name, String idNumber, Integer shopId) {
		this.account = account;
		this.address = address;
		this.telephone = telephone;
		this.name = name;
		this.idNumber = idNumber;
		this.shopId = shopId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Integer getShopId() {
		return this.shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

}