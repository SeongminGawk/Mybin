package com.example.mybin.Membership;


import java.io.Serializable;

public class SignInDTO implements Serializable {
    private String userID;  //아이디
    private String password;    //비밀번호

    // 기본 생성자
    public SignInDTO() {}

    // 매개변수가 있는 생성자
    public SignInDTO(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUsername() {
        return userID;
    }

    public void setUsername(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignInDTO{" +
                "userID='" + userID + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}