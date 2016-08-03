package com.zhao.buyer.tablemodel;

/**
 * BuyerInfo entity. @author MyEclipse Persistence Tools
 */

public class BuyerInfo {

	// Fields

	private Integer id;
	private String buyerAccount;
	private String collect;
	private String headIcon;

	// Constructors

	/** default constructor */
	public BuyerInfo() {
	}

	/** minimal constructor */
	public BuyerInfo(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

	/** full constructor */
	public BuyerInfo(String buyerAccount, String collect, String headIcon) {
		this.buyerAccount = buyerAccount;
		this.collect = collect;
		this.headIcon = headIcon;
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

	public String getCollect() {
		return this.collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}

	public String getHeadIcon() {
		return this.headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

}