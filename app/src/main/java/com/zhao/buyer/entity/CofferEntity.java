package com.zhao.buyer.entity;

import java.math.BigDecimal;
import java.util.Date;



/**   
 * @Title: Entity
 * @Description: 钱包
 * @author zhangdaihao
 * @date 2017-04-05 17:10:20
 * @version V1.0   
 *
 */

public class CofferEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**account*/
	private String account;
	/**balance*/
	private Double balance;
	
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
	 *@return: java.lang.String  account
	 */

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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  balance
	 */

	public Double getBalance(){
		return this.balance;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  balance
	 */
	public void setBalance(Double balance){
		this.balance = balance;
	}
}
