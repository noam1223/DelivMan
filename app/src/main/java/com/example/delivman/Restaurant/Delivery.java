package com.example.delivman.Restaurant;

public class Delivery {

    private String phoneNumber;
    private String address;
    private String floor;
    private String apartment;
    private String price;
    private String deliveryMan;
    private String status;
    private String restaurantName;

    public Delivery() {

    }


    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getDeliveryMan() {
        return deliveryMan;
    }
    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRestaurantName() {
        return restaurantName;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    public String getFloor() {
        return floor;
    }
    public void setFloor(String floor) {
        this.floor = floor;
    }
    public String getApartment() {
        return apartment;
    }
    public void setApartment(String apartment) {
        this.apartment = apartment;
    }



    public Delivery(String phoneNumber, String address, String price, String floor, String apartment) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.price = price;
        this.floor = floor;
        this.apartment = apartment;
    }


    @Override
    public String toString() {
        return "Delivery{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", floor='" + floor + '\'' +
                ", apartment='" + apartment + '\'' +
                ", price='" + price + '\'' +
                ", deliveryMan='" + deliveryMan + '\'' +
                ", status='" + status + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
