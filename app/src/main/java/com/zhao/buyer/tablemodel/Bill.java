package com.zhao.buyer.tablemodel;

/**
 * Bill entity. @author MyEclipse Persistence Tools
 */

public class Bill {

	// Fields

	private Integer id;
	private String account;
	private String matchAccount;
	private Long formId;
	private Double incomeAndExpenses;
	private String time;
	private String note;

	// Constructors

	/** default constructor */
	public Bill() {
	}
	
	/** full constructor */
	public Bill(String account, String matchAccount, Long formId,
				Double incomeAndExpenses, String time, String note) {
		
		this.account = account;
		this.matchAccount = matchAccount;
		this.formId = formId;
		this.incomeAndExpenses = incomeAndExpenses;
		this.time = time;
		this.note = note;
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

	public String getMatchAccount() {
		return this.matchAccount;
	}

	public void setMatchAccount(String matchAccount) {
		this.matchAccount = matchAccount;
	}

	public Long getFormId() {
		return this.formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public Double getIncomeAndExpenses() {
		return this.incomeAndExpenses;
	}

	public void setIncomeAndExpenses(Double incomeAndExpenses) {
		this.incomeAndExpenses = incomeAndExpenses;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}