package com.example.mybin.Membership;

import java.io.Serializable;

public class User implements Serializable {
    private String userID ;     // 아이디
    private String password;    // 비밀번호
    private String phoneNumber; // 휴대폰번호
    private String address;     // 주소
    private boolean cleaner;    // 환경미화원 여부
    private Long rankPoint;     // 랭킹 포인트

    public User() {}

    public User(String userID , String password, String phoneNumber, String address, boolean cleaner) {
        this.userID  = userID ;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.cleaner = cleaner;
        this.rankPoint = 0L;
    }

    public String getUserId() {
        return userID ;
    }

    public void setUserId(String userid) {
        this.userID  = userID ;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isCleaner() {
        return cleaner;
    }

    public void setCleaner(boolean cleaner) {
        this.cleaner = cleaner;
    }
    public Long getRankPoint() {
        return rankPoint;
    }

    public void setRankPoint(int rankPoint) {
        this.rankPoint = (long) rankPoint;
    }

    public void addRankPoint(Long points) {
        if (this.rankPoint == null) {
            this.rankPoint = 0L;
        }
        this.rankPoint += points;
    }


    @Override
    public String toString() {
        return "User{" +
                "userID ='" + userID + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", cleaner=" + cleaner + '\'' +
                ", rankPoint=" + rankPoint +
                '}';
    }
}
