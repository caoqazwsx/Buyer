package com.zhao.buyer.itemmodel;

import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by zhao on 2016/4/27.
 */
public class Cart {
    private static Cart cart;
    private int shop_id;
    private int cart_food_kind;
    private ArrayList<CartFoodItem> cart_food_list;
    private JSONArray discounts;
   // private ArrayList<Food> food_list;
    private double total_price;
    private double payPrice;

    public Cart(){
        cart_food_list = new ArrayList<CartFoodItem>();
        //food_list = new ArrayList<Food>();
        cart_food_kind = 0;
        total_price = 0;
        payPrice = 0;
        shop_id = 0;
        discounts = null;

    }

    public static synchronized Cart getCart(){
        if(cart == null){
            cart = new Cart();
        }
        return cart;
    }

    public boolean isEmpty(){
        if(cart_food_list.size()==0) return true;
        else return false;
    }

    public void add(ArrayList<CartFoodItem> cart_food_list){

        Log.d("Cart", ""+cart_food_list.size());
        for(int i = 0;i<cart_food_list.size();i++){
            add(cart_food_list.get(i));
        }
    }

    public void add(Food foodItem){
        if( cart_food_kind == 0){
            shop_id = foodItem.getShopId();
        }
        int pos = isFoodExist(foodItem);
        if(pos == -1){
            cart_food_list.add(new CartFoodItem(foodItem.getId(),foodItem.getFoodName(),foodItem.getPrice(),foodItem.getRemain()));
            cart_food_kind++;
        }else{
            cart_food_list.get(pos).add();

        }
        total_price = total_price + foodItem.getPrice();
        payPrice = payPrice + foodItem.getPrice();
        discountListener();
        Log.d("Cart  total_price:",""+total_price);
        Log.d("Cart  food_kind:",""+cart_food_list.size());
    }

    public void add(CartFoodItem cartFoodItem){
        int pos = isFoodExist(cartFoodItem);
        if(pos == -1) {
            cart_food_list.add(cartFoodItem);
            cart_food_kind++;
        } else{
            cart_food_list.get(pos).add();
        }
        total_price = total_price + cartFoodItem.getFoodPrice();
        payPrice = payPrice + cartFoodItem.getFoodPrice();
        discountListener();
        Log.d("Cart  total_price:",""+total_price);
        Log.d("Cart  food_kind:",""+cart_food_list.size());
    }

    public int reduce(Food foodItem){
        int pos = isFoodExist(foodItem);
        if(pos == -1){
           return -1;
        }else{
            if(cart_food_list.get(pos).reduce() == 0){
                cart_food_list.remove(pos);
                cart_food_kind--;
            }
        }
        total_price = total_price - foodItem.getPrice();
        payPrice = payPrice - foodItem.getPrice();
        if( total_price == 0) clear();
        discountListener();
        Log.d("Cart  total_price:",""+total_price);
        Log.d("Cart  food_kind:",""+cart_food_list.size());
        return 0;
    }

    public int reduce(CartFoodItem cartFoodItem){
        if(cartFoodItem.reduce() == 0) {
            cart_food_list.remove(cartFoodItem);
            cart_food_kind--;
            total_price = total_price - cartFoodItem.getFoodPrice();
            payPrice = payPrice - cartFoodItem.getFoodPrice();
            if(total_price == 0) {
                clear();
            }
            discountListener();
            return -1;
        }
        total_price = total_price - cartFoodItem.getFoodPrice();
        payPrice = payPrice - cartFoodItem.getFoodPrice();
        discountListener();
        Log.d("Cart  total_price:",""+total_price);
        Log.d("Cart  food_kind:",""+cart_food_list.size());
        return 0;
    }

    public int isFoodExist(Food foodItem){
        for(int i = 0;i < cart_food_list.size(); i++){
            if(cart_food_list.get(i).getFoodId() == foodItem.getId()) return i;
        }
        return -1;
    }
    public int isFoodExist(CartFoodItem cartFoodItem){

        for(int i = 0;i < cart_food_list.size(); i++){
            if(cart_food_list.get(i).getFoodId() == cartFoodItem.getFoodId()) return i;
        }
        return -1;
    }

   public void addDiscount(CartFoodItem cartFoodItem){
       cart_food_list.add(cartFoodItem);
       payPrice = total_price + cartFoodItem.getFoodPrice();
   }

    public void removeDiscount(CartFoodItem cartFoodItem){
        cart_food_list.remove(cartFoodItem);
        payPrice = total_price - cartFoodItem.getFoodPrice();
    }

    private void discountListener() {
        if (discounts == null || discounts.length() == 0) return;
        else {
            try {

                for (int i = 0; i < discounts.length(); i++) {
                    JSONArray discount = discounts.getJSONArray(i);
                    if(i == 0&&discount.getDouble(0) > total_price){
                        int pos = isFoodExist(new CartFoodItem(0));
                        if (pos != -1) {
                            removeDiscount(cart_food_list.get(pos));
                        }
                        return;
                    }
                    if (discount.getDouble(0) <= total_price) {
                        int pos = isFoodExist(new CartFoodItem(0));
                        if (pos != -1) {
                            removeDiscount(cart_food_list.get(pos));
                            addDiscount(new CartFoodItem(0, "满" + discount.getDouble(0) + "减" + discount.getDouble(1),
                                    0 - discount.getDouble(1),0));
                        } else {
                            addDiscount(new CartFoodItem(0, "满" + discount.getDouble(0) + "减" + discount.getDouble(1),
                                    0 - discount.getDouble(1),0));
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("Cart", e.toString());
                return;
            }
        }
    }


    public void clear(){
        cart_food_list = new ArrayList<CartFoodItem>();
        //food_list = new ArrayList<Food>();
        cart_food_kind = 0;
        total_price = 0;
        payPrice = 0;
        shop_id = 0;
    }




    public void setDiscount(JSONArray discounts){
        this.discounts = discounts;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getCart_food_kind() {
        return cart_food_kind;
    }

    public ArrayList<CartFoodItem> getCart_food_list() {
        return cart_food_list;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setCart_food_kind(int cart_food_kind) {
        this.cart_food_kind = cart_food_kind;
    }

    public void setCart_food_list(ArrayList<CartFoodItem> cart_food_list) {
        this.cart_food_list = cart_food_list;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public double getPayPrice() {
        return payPrice;
    }
}
