package com.example.myapplication;

import java.io.Serializable;

public class ArmyUser implements Serializable {


    private int userID;
    private String birth;
    private String userName;
    private int uClassCode;
    private int unitCode;
    private String unitName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public ArmyUser(int userID, String birth, String userName, int uClassCode, int unitCode, String unitName) {
        this.userID = userID;
        this.birth = birth;
        this.userName = userName;
        this.uClassCode = uClassCode;
        this.unitCode = unitCode;
        this.unitName = unitName;
    }

    @Override
    public String toString() {
        return "ArmyUser{" +
                "userID=" + userID +
                ", birth=" + birth +
                ", userName='" + userName + '\'' +
                ", uClassCode=" + uClassCode +
                ", unitCode=" + unitCode +
                '}';
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getuClassCode() {
        return uClassCode;
    }

    public void setuClassCode(int uClassCode) {
        this.uClassCode = uClassCode;
    }

    public int getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(int unitCode) {
        this.unitCode = unitCode;
    }
}
