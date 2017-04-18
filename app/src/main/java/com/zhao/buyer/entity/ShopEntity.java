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
 * @Description: 商铺
 * @author zhangdaihao
 * @date 2017-04-05 17:13:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "shop", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ShopEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**sellerAccount*/
	private String sellerAccount;
	/**shopName*/
	private String shopName;
	/**shopIcon*/
	private String shopIcon;
	/**sales*/
	private Integer sales;
	/**botPrice*/
	private Double botPrice;
	/**deliveryTimes*/
	private Integer deliveryTimes;
	/**specialOffer*/
	private String specialOffer;
	/**deliveryService*/
	private String deliveryService;
	/**serviceTime*/
	private String serviceTime;
	/**telephone*/
	private String telephone;
	/**location*/
	private String location;
	/**discount*/
	private String discount;
	/**address*/
	private String address;
	/**state*/
	private String state;
	/**verifyState*/
	private String verifyState;
	
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
	 *@return: java.lang.String  sellerAccount
	 */
	@Column(name ="SELLER_ACCOUNT",nullable=false,length=20)
	public String getSellerAccount(){
		return this.sellerAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  sellerAccount
	 */
	public void setSellerAccount(String sellerAccount){
		this.sellerAccount = sellerAccount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  shopName
	 */
	@Column(name ="SHOP_NAME",nullable=false,length=30)
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
	 *@return: java.lang.String  shopIcon
	 */
	@Column(name ="SHOP_ICON",nullable=true,length=255)
	public String getShopIcon(){
		return this.shopIcon;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  shopIcon
	 */
	public void setShopIcon(String shopIcon){
		this.shopIcon = shopIcon;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  sales
	 */
	@Column(name ="SALES",nullable=true,precision=10,scale=0)
	public Integer getSales(){
		return this.sales;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  sales
	 */
	public void setSales(Integer sales){
		this.sales = sales;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  botPrice
	 */
	@Column(name ="BOT_PRICE",nullable=true,precision=22)
	public Double getBotPrice(){
		return this.botPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  botPrice
	 */
	public void setBotPrice(Double botPrice){
		this.botPrice = botPrice;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  deliveryTimes
	 */
	@Column(name ="DELIVERY_TIMES",nullable=true,precision=10,scale=0)
	public Integer getDeliveryTimes(){
		return this.deliveryTimes;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  deliveryTimes
	 */
	public void setDeliveryTimes(Integer deliveryTimes){
		this.deliveryTimes = deliveryTimes;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  specialOffer
	 */
	@Column(name ="SPECIAL_OFFER",nullable=true,length=255)
	public String getSpecialOffer(){
		return this.specialOffer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  specialOffer
	 */
	public void setSpecialOffer(String specialOffer){
		this.specialOffer = specialOffer;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  deliveryService
	 */
	@Column(name ="DELIVERY_SERVICE",nullable=true,length=255)
	public String getDeliveryService(){
		return this.deliveryService;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  deliveryService
	 */
	public void setDeliveryService(String deliveryService){
		this.deliveryService = deliveryService;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  serviceTime
	 */
	@Column(name ="SERVICE_TIME",nullable=true,length=255)
	public String getServiceTime(){
		return this.serviceTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  serviceTime
	 */
	public void setServiceTime(String serviceTime){
		this.serviceTime = serviceTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  telephone
	 */
	@Column(name ="TELEPHONE",nullable=true,length=255)
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
	 *@return: java.lang.String  location
	 */
	@Column(name ="LOCATION",nullable=false,length=255)
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  discount
	 */
	@Column(name ="DISCOUNT",nullable=true,length=255)
	public String getDiscount(){
		return this.discount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  discount
	 */
	public void setDiscount(String discount){
		this.discount = discount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  address
	 */
	@Column(name ="ADDRESS",nullable=false,length=255)
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
	 *@return: java.lang.String  state
	 */
	@Column(name ="STATE",nullable=false,length=255)
	public String getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  state
	 */
	public void setState(String state){
		this.state = state;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  verifyState
	 */
	@Column(name ="VERIFY_STATE",nullable=true,length=255)
	public String getVerifyState(){
		return this.verifyState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  verifyState
	 */
	public void setVerifyState(String verifyState){
		this.verifyState = verifyState;
	}
}
