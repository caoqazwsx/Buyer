package com.zhao.buyer.tablemodel;

/**
 * SenderInfo entity. @author MyEclipse Persistence Tools
 */

public class SenderInfo  {

	// Fields

	private Integer id;
	private String senderAccount;
	private String name;
	private String telephone;

	// Constructors

	/** default constructor */
	public SenderInfo() {
	}

	/** full constructor */
	public SenderInfo(String senderAccount, String name, String telephone) {
		this.senderAccount = senderAccount;
		this.name = name;
		this.telephone = telephone;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSenderAccount() {
		return this.senderAccount;
	}

	public void setSenderAccount(String senderAccount) {
		this.senderAccount = senderAccount;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}