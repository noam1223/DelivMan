package com.example.delivman.Restaurant;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private String RestaurantName;
    private String MangerPhoneNumber;
    private String RestaurantAdress;
    private String RestaurantPhoneNumber;
    private String Email;
    private String RestaurantPassWord;

    public Restaurant() {

    }

    public Restaurant(String restaurantName, String mangerPhoneNumber, String restaurantAdress, String restaurantPhoneNumber, String email, String restaurantPassWord) {
        RestaurantName = restaurantName;
        MangerPhoneNumber = mangerPhoneNumber;
        RestaurantAdress = restaurantAdress;
        RestaurantPhoneNumber = restaurantPhoneNumber;
        Email = email;
        RestaurantPassWord = restaurantPassWord;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getMangerPhoneNumber() {
        return MangerPhoneNumber;
    }

    public void setMangerPhoneNumber(String mangerPhoneNumber) {
        MangerPhoneNumber = mangerPhoneNumber;
    }

    public String getRestaurantAdress() {
        return RestaurantAdress;
    }

    public void setRestaurantAdress(String restaurantAdress) {
        RestaurantAdress = restaurantAdress;
    }

    public String getRestaurantPhoneNumber() {
        return RestaurantPhoneNumber;
    }

    public void setRestaurantPhoneNumber(String restaurantPhoneNumber) {
        RestaurantPhoneNumber = restaurantPhoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRestaurantPassWord() {
        return RestaurantPassWord;
    }

    public void setRestaurantPassWord(String restaurantPassWord) {
        RestaurantPassWord = restaurantPassWord;
    }


    @Override
    public String toString() {
        return "Restaurant{" +
                "RestaurantName='" + RestaurantName + '\'' +
                ", MangerPhoneNumber='" + MangerPhoneNumber + '\'' +
                ", RestaurantAdress='" + RestaurantAdress + '\'' +
                ", RestaurantPhoneNumber='" + RestaurantPhoneNumber + '\'' +
                ", Email='" + Email + '\'' +
                ", RestaurantPassWord='" + RestaurantPassWord + '\'' +
                '}';
    }
}
