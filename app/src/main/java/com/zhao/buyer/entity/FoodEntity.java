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
 * @Description: 食物
 * @author zhangdaihao
 * @date 2017-04-05 17:12:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "food", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class FoodEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**shopId*/
	private Integer shopId;
	/**foodName*/
	private String foodName;
	/**foodSales*/
	private Integer foodSales;
	/**price*/
	private Double price;
	/**marketPrice*/
	private Double marketPrice;
	/**note*/
	private String note;
	/**foodIcon*/
	private String foodIcon;
	/**remain*/
	private Integer remain;
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  shopId
	 */
	@Column(name ="SHOP_ID",nullable=false,precision=10,scale=0)
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
	 *@return: java.lang.String  foodName
	 */
	@Column(name ="FOOD_NAME",nullable=false,length=30)
	public String getFoodName(){
		return this.foodName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  foodName
	 */
	public void setFoodName(String foodName){
		this.foodName = foodName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  foodSales
	 */
	@Column(name ="FOOD_SALES",nullable=false,precision=10,scale=0)
	public Integer getFoodSales(){
		return this.foodSales;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  foodSales
	 */
	public void setFoodSales(Integer foodSales){
		this.foodSales = foodSales;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  price
	 */
	@Column(name ="PRICE",nullable=false,precision=22)
	public Double getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  price
	 */
	public void setPrice(Double price){
		this.price = price;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  marketPrice
	 */
	@Column(name ="MARKET_PRICE",nullable=true,precision=22)
	public Double getMarketPrice(){
		return this.marketPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  marketPrice
	 */
	public void setMarketPrice(Double marketPrice){
		this.marketPrice = marketPrice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  note
	 */
	@Column(name ="NOTE",nullable=true,length=255)
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
	 *@return: java.lang.String  foodIcon
	 */
	@Column(name ="FOOD_ICON",nullable=true,length=255)
	public String getFoodIcon(){
		return this.foodIcon;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  foodIcon
	 */
	public void setFoodIcon(String foodIcon){
		this.foodIcon = foodIcon;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  remain
	 */
	@Column(name ="REMAIN",nullable=false,precision=10,scale=0)
	public Integer getRemain(){
		return this.remain;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  remain
	 */
	public void setRemain(Integer remain){
		this.remain = remain;
	}
}
