package com.zhao.buyer.itemmodel;

/**
 * DeliveryAddress entity. @author MyEclipse Persistence Tools
 */

public class DeliveryAddress  {

	// Fields

	private Integer id;
	private String buyerAccount;
	private String contact;
	private String telephone;
	private String address;
	private String no;
	private String location;

	// Constructors

	/** default constructor */
	public DeliveryAddress() {
	}

	/** full constructor */
	public DeliveryAddress(String buyerAccount, String contact,
						   String telephone, String address, String no, String location) {
		this.buyerAccount = buyerAccount;
		this.contact = contact;
		this.telephone = telephone;
		this.address = address;
		this.no = no;
		this.location = location;
	}


	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBuyerAccount() {
		return this.buyerAccount;
	}

	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}