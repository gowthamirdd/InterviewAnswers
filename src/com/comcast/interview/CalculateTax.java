package com.comcast.interview;

/**
 * Created by Gowthami on 4/1/15.
 */
public class CalculateTax {


    //Assumption is price is always in pennies and question says single item.
    public  Long calculate(int price, boolean isLuxury) throws RuntimeException {
        if(price <= 0){
            throw new RuntimeException("Price Cannot be '0' or less than '0'. The value user entered is : " + price);
        }
        long finalPrice;
            if(!isLuxury){
                finalPrice = (long) Math.abs((price*0.01)+price);
            } else{
                finalPrice = (long) Math.abs((price*0.09)+price);
            }

        return finalPrice;
    }



}