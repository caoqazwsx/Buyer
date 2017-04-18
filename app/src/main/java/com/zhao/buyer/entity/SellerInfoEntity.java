package com.zhao.buyer.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 商家信息
 * @author zhangdaihao
 * @date 2017-04-05 17:13:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "seller_info", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SellerInfoEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**account*/
	private String account;
	/**name*/
	private String name;
	/**idNumber*/
	private String idNumber;
	/**shopId*/
	private Integer shopId;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,precision=10,scale=0)
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
	 *@return: java.lang.String  account
	 */
	@Column(name ="ACCOUNT",nullable=false,length=20)
	public String getAccount(){
		return this.account;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  account
	 */
	public void setAccount(String account){
		this.account = account;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  name
	 */
	@Column(name ="NAME",nullable=false,length=20)
	public String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  name
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  idNumber
	 */
	@Column(name ="ID_NUMBER",nullable=false,length=20)
	public String getIdNumber(){
		return this.idNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  idNumber
	 */
	public void setIdNumber(String idNumber){
		this.idNumber = idNumber;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  shopId
	 */
	@Column(name ="SHOP_ID",nullable=true,precision=10,scale=0)
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
}
