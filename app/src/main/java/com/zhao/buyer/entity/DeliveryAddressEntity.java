package com.zhao.buyer.entity;

import java.math.BigDecimal;
import java.util.Date;



/**   
 * @Title: Entity
 * @Description: 收货地址
 * @author zhangdaihao
 * @date 2017-04-05 17:11:40
 * @version V1.0   
 *
 */

public class DeliveryAddressEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**buyerAccount*/
	private String buyerAccount;
	/**contact*/
	private String contact;
	/**telephone*/
	private String telephone;
	/**address*/
	private String address;
	/**no*/
	private String no;
	/**location*/
	private String location;
	
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
	 *@return: java.lang.String  address
	 */

	public String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  address
	 */
	public void setAddress(String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  no
	 */

	public String getNo(){
		return this.no;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  no
	 */
	public void setNo(String no){
		this.no = no;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  location
	 */

	public String getLocation(){
		return this.location;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  location
	 */
	public void setLocation(String location){
		this.location = location;
	}
}
