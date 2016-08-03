package com.zhao.buyer.itemmodel;

/**
 * Food entity. @author MyEclipse Persistence Tools
 */

public class Food {

	// Fields

	private Integer id;
	private Integer shopId;
	private String foodName;
	private Integer foodSales;
	private Double price;
	private Double marketPrice;
	private String note;
	private String foodIcon;
	private Integer remain;

	// Constructors

	/** default constructor */
	public Food() {
	}

	/** minimal constructor */
	public Food(Integer shopId, String foodName, Integer foodSales,
				Double price, Integer remain) {
		this.shopId = shopId;
		this.foodName = foodName;
		this.foodSales = foodSales;
		this.price = price;
		this.remain = remain;
	}

	/** full constructor */
	public Food(Integer shopId, String foodName, Integer foodSales,
				Double price, Double marketPrice, String note, String foodIcon,
				Integer remain) {
		this.shopId = shopId;
		this.foodName = foodName;
		this.foodSales = foodSales;
		this.price = price;
		this.marketPrice = marketPrice;
		this.note = note;
		this.foodIcon = foodIcon;
		this.remain = remain;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShopId() {
		return this.shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getFoodName() {
		return this.foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public Integer getFoodSales() {
		return this.foodSales;
	}

	public void setFoodSales(Integer foodSales) {
		this.foodSales = foodSales;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMarketPrice() {
		return this.marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFoodIcon() {
		return this.foodIcon;
	}

	public void setFoodIcon(String foodIcon) {
		this.foodIcon = foodIcon;
	}

	public Integer getRemain() {
		return this.remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

}