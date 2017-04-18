package com.zhao.buyer.entity;

import java.math.BigDecimal;
import java.util.Date;



/**   
 * @Title: Entity
 * @Description: 评价
 * @author zhangdaihao
 * @date 2017-04-05 17:10:55
 * @version V1.0   
 *
 */

public class CommentEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**commentAccount*/
	private String commentAccount;
	/**shopId*/
	private Integer shopId;
	/**food*/
	private String food;
	/**sendGrade*/
	private Integer sendGrade;
	/**shopGrade*/
	private Integer shopGrade;
	/**commentText*/
	private String commentText;
	/**reply*/
	private String reply;
	/**commentTime*/
	private String commentTime;
	/**formId*/
	private Integer formId;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	

	public Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  id
	 */
	public void setId(Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  commentAccount
	 */

	public String getCommentAccount(){
		return this.commentAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  commentAccount
	 */
	public void setCommentAccount(String commentAccount){
		this.commentAccount = commentAccount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  shopId
	 */

	public Integer getShopId(){
		return this.shopId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  shopId
	 */
	public void setShopId(Integer shopId){
		this.shopId = shopId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  food
	 */

	public String getFood(){
		return this.food;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  food
	 */
	public void setFood(String food){
		this.food = food;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  sendGrade
	 */

	public Integer getSendGrade(){
		return this.sendGrade;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  sendGrade
	 */
	public void setSendGrade(Integer sendGrade){
		this.sendGrade = sendGrade;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  shopGrade
	 */

	public Integer getShopGrade(){
		return this.shopGrade;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  shopGrade
	 */
	public void setShopGrade(Integer shopGrade){
		this.shopGrade = shopGrade;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  commentText
	 */

	public String getCommentText(){
		return this.commentText;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  commentText
	 */
	public void setCommentText(String commentText){
		this.commentText = commentText;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  reply
	 */

	public String getReply(){
		return this.reply;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  reply
	 */
	public void setReply(String reply){
		this.reply = reply;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  commentTime
	 */

	public String getCommentTime(){
		return this.commentTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  commentTime
	 */
	public void setCommentTime(String commentTime){
		this.commentTime = commentTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  formId
	 */

	public Integer getFormId(){
		return this.formId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  formId
	 */
	public void setFormId(Integer formId){
		this.formId = formId;
	}
}
