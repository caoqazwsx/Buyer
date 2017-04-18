package com.zhao.buyer.entity;

import java.math.BigDecimal;
import java.util.Date;



/**   
 * @Title: Entity
 * @Description: 订单
 * @author zhangdaihao
 * @date 2017-04-05 17:12:48
 * @version V1.0   
 *
 */

public class FormEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**payPrice*/
	private Double payPrice;
	/**shopId*/
	private Integer shopId;
	/**buyerAccount*/
	private String buyerAccount;
	/**shopName*/
	private String shopName;
	/**formFood*/
	private String formFood;
	/**formState*/
	private String formState;
	/**contact*/
	private String contact;
	/**telephone*/
	private String telephone;
	/**formAddress*/
	private String formAddress;
	/**addressLocation*/
	private String addressLocation;
	/**payMethod*/
	private String payMethod;
	/**submitTime*/
	private String submitTime;
	/**sendTime*/
	private String sendTime;
	/**note*/
	private String note;
	/**sendState*/
	private String sendState;
	/**senderAccount*/
	private String senderAccount;
	
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  payPrice
	 */

	public Double getPayPrice(){
		return this.payPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  payPrice
	 */
	public void setPayPrice(Double payPrice){
		this.payPrice = payPrice;
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
	 *@return: java.lang.String  buyerAccount
	 */

	public String getBuyerAccount(){
		return this.buyerAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  buyerAccount
	 */
	public void setBuyerAccount(String buyerAccount){
		this.buyerAccount = buyerAccount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  shopName
	 */

	public String getShopName(){
		return this.shopName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  shopName
	 */
	public void setShopName(String shopName){
		this.shopName = shopName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  formFood
	 */

	public String getFormFood(){
		return this.formFood;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  formFood
	 */
	public void setFormFood(String formFood){
		this.formFood = formFood;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  formState
	 */

	public String getFormState(){
		return this.formState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  formState
	 */
	public void setFormState(String formState){
		this.formState = formState;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  contact
	 */

	public String getContact(){
		return this.contact;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  contact
	 */
	public void setContact(String contact){
		this.contact = contact;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  telephone
	 */

	public String getTelephone(){
		return this.telephone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  telephone
	 */
	public void setTelephone(String telephone){
		this.telephone = telephone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  formAddress
	 */

	public String getFormAddress(){
		return this.formAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  formAddress
	 */
	public void setFormAddress(String formAddress){
		this.formAddress = formAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  addressLocation
	 */

	public String getAddressLocation(){
		return this.addressLocation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  addressLocation
	 */
	public void setAddressLocation(String addressLocation){
		this.addressLocation = addressLocation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  payMethod
	 */

	public String getPayMethod(){
		return this.payMethod;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  payMethod
	 */
	public void setPayMethod(String payMethod){
		this.payMethod = payMethod;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  submitTime
	 */

	public String getSubmitTime(){
		return this.submitTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  submitTime
	 */
	public void setSubmitTime(String submitTime){
		this.submitTime = submitTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  sendTime
	 */

	public String getSendTime(){
		return this.sendTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  sendTime
	 */
	public void setSendTime(String sendTime){
		this.sendTime = sendTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  note
	 */

	public String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  note
	 */
	public void setNote(String note){
		this.note = note;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  sendState
	 */

	public String getSendState(){
		return this.sendState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  sendState
	 */
	public void setSendState(String sendState){
		this.sendState = sendState;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  senderAccount
	 */

	public String getSenderAccount(){
		return this.senderAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  senderAccount
	 */
	public void setSenderAccount(String senderAccount){
		this.senderAccount = senderAccount;
	}
}
