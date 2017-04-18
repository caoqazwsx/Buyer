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
 * @Description: 配送人信息
 * @author zhangdaihao
 * @date 2017-04-05 17:13:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sender_info", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SenderInfoEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**senderAccount*/
	private String senderAccount;
	/**name*/
	private String name;
	/**telephone*/
	private String telephone;
	
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
	 *@return: java.lang.String  senderAccount
	 */
	@Column(name ="SENDER_ACCOUNT",nullable=false,length=255)
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  name
	 */
	@Column(name ="NAME",nullable=false,length=255)
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
	 *@return: java.lang.String  telephone
	 */
	@Column(name ="TELEPHONE",nullable=false,length=255)
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
}
