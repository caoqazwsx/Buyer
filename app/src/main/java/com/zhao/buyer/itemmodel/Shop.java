package com.zhao.buyer.itemmodel;

/**
 * Shop entity. @author MyEclipse Persistence Tools
 */

public class Shop  {

	// Fields

	private Integer id;
	private String sellerAccount;
	private String shopName;
	private String shopIcon;
	private Integer sales;
	private Double botPrice;
	private Integer deliveryTimes;
	private String specialOffer;
	private String deliveryService;
	private String serviceTime;
	private String telephone;
	private String location;
	private String discount;
	private String address;
	private int distance;
	private String state;

	// Constructors

	/** default constructor */
	public Shop() {
	}

	/** minimal constructor */
	public Shop(String sellerAccount, String shopName, String location,
				String discount, String address) {
		this.sellerAccount = sellerAccount;
		this.shopName = shopName;
		this.location = location;
		this.discount = discount;
		this.address = address;
	}

	/** full constructor */
	public Shop(String sellerAccount, String shopName, String shopIcon,
				Integer sales, Double botPrice, Integer deliveryTimes,
				String specialOffer, String deliveryService, String serviceTime,
				String telephone, String location, String discount, String address,
				String state) {
		this.sellerAccount = sellerAccount;
		this.shopName = shopName;
		this.shopIcon = shopIcon;
		this.sales = sales;
		this.botPrice = botPrice;
		this.deliveryTimes = deliveryTimes;
		this.specialOffer = specialOffer;
		this.deliveryService = deliveryService;
		this.serviceTime = serviceTime;
		this.telephone = telephone;
		this.location = location;
		this.discount = discount;
		this.address = address;
		this.state = state;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSellerAccount() {
		return this.sellerAccount;
	}

	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopIcon() {
		return this.shopIcon;
	}

	public void setShopIcon(String shopIcon) {
		this.shopIcon = shopIcon;
	}

	public Integer getSales() {
		return this.sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Double getBotPrice() {
		return this.botPrice;
	}

	public void setBotPrice(Double botPrice) {
		this.botPrice = botPrice;
	}

	public Integer getDeliveryTimes() {
		return this.deliveryTimes;
	}

	public void setDeliveryTimes(Integer deliveryTimes) {
		this.deliveryTimes = deliveryTimes;
	}

	public String getSpecialOffer() {
		return this.specialOffer;
	}

	public void setSpecialOffer(String specialOffer) {
		this.specialOffer = specialOffer;
	}

	public String getDeliveryService() {
		return this.deliveryService;
	}

	public void setDeliveryService(String deliveryService) {
		this.deliveryService = deliveryService;
	}

	public String getServiceTime() {
		return this.serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDiscount() {
		return this.discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}