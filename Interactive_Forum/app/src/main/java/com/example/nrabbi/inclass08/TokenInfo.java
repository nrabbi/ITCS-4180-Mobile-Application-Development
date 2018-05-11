// Nazmul Rabbi & Dyrell Cole
// ITCS 4180 : In Class Assignment 8
// TokenInfo.java
// Group 20

package com.example.nrabbi.inclass08;

import java.io.Serializable;

public class TokenInfo implements Serializable{
    String token;
    String user_email;
    String user_fname;
    String user_lname;
    String user_role;
    int user_id;

    @Override
    public String toString() {
        return "TokenInfo{" +
                "token='" + token + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_fname='" + user_fname + '\'' +
                ", user_lname='" + user_lname + '\'' +
                ", user_role='" + user_role + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
