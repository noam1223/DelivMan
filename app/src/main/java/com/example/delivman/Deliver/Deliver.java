package com.example.delivman.Deliver;

import android.widget.EditText;

import java.io.Serializable;

public class Deliver implements Serializable {
    private String DeliverName;
    private String  DeliverLastName;
    private String DeliverPhoneNum;
    private String DeliverPassword;

    private int totalMoney;
    private int totalTasks;


    public Deliver() {
    }

    public Deliver(String deliverName, String deliverLastName, String deliverPhoneNum, String deliverPassword) {
        this.DeliverName = deliverName;
        this.DeliverLastName = deliverLastName;
        this.DeliverPhoneNum = deliverPhoneNum;
        this.DeliverPassword = deliverPassword;

        this.totalMoney = 0;
        this.totalTasks = 0;

    }

    public String getDeliverName() {
        return DeliverName;
    }

    public void setDeliverName(String deliverName) {
        DeliverName = deliverName;
    }

    public String getDeliverLastName() {
        return DeliverLastName;
    }

    public void setDeliverLastName(String deliverLastName) {
        DeliverLastName = deliverLastName;
    }

    public String getDeliverPhoneNum() {
        return DeliverPhoneNum;
    }

    public void setDeliverPhoneNum(String deliverPhoneNum) {
        DeliverPhoneNum = deliverPhoneNum;
    }

    public String getDeliverPassword() {
        return DeliverPassword;
    }

    public void setDeliverPassword(String deliverPassword) {
        DeliverPassword = deliverPassword;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }


    public void addToPriceAmount(int addCash){

        totalMoney += addCash;

    }



    public void addToTasksAmount(int addTask){

        totalTasks += addTask;

    }






    @Override
    public String toString() {
        return "Deliver{" +
                "DeliverName='" + DeliverName + '\'' +
                ", DeliverLastName='" + DeliverLastName + '\'' +
                ", DeliverPhoneNum='" + DeliverPhoneNum + '\'' +
                ", DeliverPassword='" + DeliverPassword + '\'' +
                ", totalMoney=" + totalMoney +
                ", totalTasks=" + totalTasks +
                '}';
    }
}
