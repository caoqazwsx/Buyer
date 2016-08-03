package com.zhao.buyer.itemmodel;

/**
 * Complain entity. @author MyEclipse Persistence Tools
 */

public class Complain  {

	// Fields

	private Integer id;
	private String buyerAccount;
	private Long formId;
	private Integer shopId;
	private String complainText;
	private String handleText;
	private String complainState;
	private String time;

	// Constructors

	/** default constructor */
	public Complain() {
	}

	/** minimal constructor */
	public Complain(String buyerAccount, Long formId, Integer shopId,
			String complainText, String complainState, String time) {
		this.buyerAccount = buyerAccount;
		this.formId = formId;
		this.shopId = shopId;
		this.complainText = complainText;
		this.complainState = complainState;
		this.time = time;
	}

	/** full constructor */
	public Complain(String buyerAccount, Long formId, Integer shopId,
			String complainText, String handleText, String complainState,
			String time) {
		this.buyerAccount = buyerAccount;
		this.formId = formId;
		this.shopId = shopId;
		this.complainText = complainText;
		this.handleText = handleText;
		this.complainState = complainState;
		this.time = time;
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

	public Long getFormId() {
		return this.formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public Integer getShopId() {
		return this.shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getComplainText() {
		return this.complainText;
	}

	public void setComplainText(String complainText) {
		this.complainText = complainText;
	}

	public String getHandleText() {
		return this.handleText;
	}

	public void setHandleText(String handleText) {
		this.handleText = handleText;
	}

	public String getComplainState() {
		return this.complainState;
	}

	public void setComplainState(String complainState) {
		this.complainState = complainState;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}