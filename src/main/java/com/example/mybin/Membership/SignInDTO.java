package com.example.mybin.Membership;


import java.io.Serializable;

public class SignInDTO implements Serializable {
    private String userID;  
    private String password;    

    public SignInDTO() {}

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
