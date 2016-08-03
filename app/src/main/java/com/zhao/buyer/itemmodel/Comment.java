package com.zhao.buyer.itemmodel;

/**
 * Comment entity. @author MyEclipse Persistence Tools
 */

public class Comment  {

	// Fields

	private Integer id;
	private String commentAccount;
	private Integer shopId;
	private String food;
	private Integer sendGrade;
	private Integer shopGrade;
	private String commentText;
	private String reply;
	private String commentTime;
	private Long formId;

	// Constructors

	/** default constructor */
	public Comment() {
	}

	/** minimal constructor */
	public Comment(String commentAccount, Integer shopId, String food,
			Integer sendGrade, Integer shopGrade, String commentTime,
			Long formId) {
		this.commentAccount = commentAccount;
		this.shopId = shopId;
		this.food = food;
		this.sendGrade = sendGrade;
		this.shopGrade = shopGrade;
		this.commentTime = commentTime;
		this.formId = formId;
	}

	/** full constructor */
	public Comment(String commentAccount, Integer shopId, String food,
			Integer sendGrade, Integer shopGrade, String commentText,
			String reply, String commentTime, Long formId) {
		this.commentAccount = commentAccount;
		this.shopId = shopId;
		this.food = food;
		this.sendGrade = sendGrade;
		this.shopGrade = shopGrade;
		this.commentText = commentText;
		this.reply = reply;
		this.commentTime = commentTime;
		this.formId = formId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommentAccount() {
		return this.commentAccount;
	}

	public void setCommentAccount(String commentAccount) {
		this.commentAccount = commentAccount;
	}

	public Integer getShopId() {
		return this.shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getFood() {
		return this.food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public Integer getSendGrade() {
		return this.sendGrade;
	}

	public void setSendGrade(Integer sendGrade) {
		this.sendGrade = sendGrade;
	}

	public Integer getShopGrade() {
		return this.shopGrade;
	}

	public void setShopGrade(Integer shopGrade) {
		this.shopGrade = shopGrade;
	}

	public String getCommentText() {
		return this.commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public String getReply() {
		return this.reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getCommentTime() {
		return this.commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public Long getFormId() {
		return this.formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

}