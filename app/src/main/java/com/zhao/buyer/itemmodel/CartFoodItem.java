package com.zhao.buyer.itemmodel;

/**
 * Created by zhao on 2016/4/27.
 */
public class CartFoodItem {
    private int foodId;
    private String foodName;
    private int foodNum;
    private int remain;
    private double foodTotalPrice;
    private double foodPrice;


    public CartFoodItem(int food_id){
        this.foodId = food_id;
    }

    public CartFoodItem(int food_id,String food_name,double foodPrice,int remain){
        this.foodId = food_id;
        this.foodName =food_name;
        this.foodNum = 1;
        this.foodPrice = foodPrice;
        this.foodTotalPrice = foodPrice;
        this.remain = remain;


    }
    public CartFoodItem(int foodId,String foodName,int foodNum,double foodPrice,double foodTotalPrice){
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodNum = foodNum;
        this.foodPrice = foodPrice;
        this.foodTotalPrice = foodTotalPrice;


    }


    public void add(){
        if(foodNum < remain) {
            foodNum++;
            foodTotalPrice = foodTotalPrice + foodPrice;
        }
    }

    public int reduce(){
        foodNum--;
        if (foodNum <= 0){
            return 0;
        }
        foodTotalPrice = foodTotalPrice - foodPrice;
        return 1;
    }

    public int getFoodId(){
        return foodId;
    }
    public String getFoodName(){
        return foodName;
    }
    public int getFoodNum(){
        return foodNum;
    }
    public double getFoodPrice(){
        return foodPrice;
    }
    public double getFoodTotalPrice(){
        return foodTotalPrice;
    }
    public int getRemain(){
        return remain;
    }
    public void setFoodId(int foodId){
        this.foodId = foodId;
    }
    public void setFoodName(String foodName){
        this.foodName = foodName;
    }
    public void setFoodTotalPrice(double foodTotalPrice){
        this.foodTotalPrice = foodTotalPrice;
    }
    public void setFoodNum(int foodNum){
        this.foodNum = foodNum;
    }
    public void setFoodPrice(double foodPrice){
        this.foodPrice = foodPrice;
    }
    public void setRemain(int remain){this.remain = remain;}

}
