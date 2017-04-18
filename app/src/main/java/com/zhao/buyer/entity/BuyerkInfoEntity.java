package com.zhao.buyer.entity;

import java.math.BigDecimal;
import java.util.Date;



/**   
 * @Title: Entity
 * @Description: 客户信息
 * @author zhangdaihao
 * @date 2017-04-05 17:09:13
 * @version V1.0   
 *
 */

@SuppressWarnings("serial")
public class BuyerkInfoEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**buyerAccount*/
	private String buyerAccount;
	/**collect*/
	private String collect;
	/**headIcon*/
	private String headIcon;
	
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
	 *@return: java.lang.String  collect
	 */

	public String getCollect(){
		return this.collect;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  collect
	 */
	public void setCollect(String collect){
		this.collect = collect;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  headIcon
	 */

	public String getHeadIcon(){
		return this.headIcon;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  headIcon
	 */
	public void setHeadIcon(String headIcon){
		this.headIcon = headIcon;
	}
}
