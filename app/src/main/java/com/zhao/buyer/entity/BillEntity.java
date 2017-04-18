package com.zhao.buyer.entity;

import java.math.BigDecimal;
import java.util.Date;



/**   
 * @Title: Entity
 * @Description: 账单
 * @author zhangdaihao
 * @date 2017-04-05 17:05:37
 * @version V1.0   
 *
 */

@SuppressWarnings("serial")
public class BillEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**account*/
	private String account;
	/**matchAccount*/
	private String matchAccount;
	/**formId*/
	private Integer formId;
	/**incomeAndExpenses*/
	private Double incomeAndExpenses;
	/**time*/
	private String time;
	/**note*/
	private String note;
	
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  matchAccount
	 */

	public String getMatchAccount(){
		return this.matchAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  matchAccount
	 */
	public void setMatchAccount(String matchAccount){
		this.matchAccount = matchAccount;
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  incomeAndExpenses
	 */

	public Double getIncomeAndExpenses(){
		return this.incomeAndExpenses;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  incomeAndExpenses
	 */
	public void setIncomeAndExpenses(Double incomeAndExpenses){
		this.incomeAndExpenses = incomeAndExpenses;
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
}
