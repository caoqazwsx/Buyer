package com.zhao.buyer.entity;

import java.math.BigDecimal;
import java.util.Date;



/**   
 * @Title: Entity
 * @Description: 投诉
 * @author zhangdaihao
 * @date 2017-04-05 17:11:17
 * @version V1.0   
 *
 */

@SuppressWarnings("serial")
public class ComplainEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**buyerAccount*/
	private String buyerAccount;
	/**formId*/
	private Integer formId;
	/**shopId*/
	private Integer shopId;
	/**complainText*/
	private String complainText;
	/**handleText*/
	private String handleText;
	/**complainState*/
	private String complainState;
	/**time*/
	private String time;
	
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
	 *@return: java.lang.String  complainText
	 */

	public String getComplainText(){
		return this.complainText;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  complainText
	 */
	public void setComplainText(String complainText){
		this.complainText = complainText;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  handleText
	 */

	public String getHandleText(){
		return this.handleText;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  handleText
	 */
	public void setHandleText(String handleText){
		this.handleText = handleText;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  complainState
	 */

	public String getComplainState(){
		return this.complainState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  complainState
	 */
	public void setComplainState(String complainState){
		this.complainState = complainState;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  time
	 */

	public String getTime(){
		return this.time;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  time
	 */
	public void setTime(String time){
		this.time = time;
	}
}
