// Nazmul Rabbi & Dyrell Cole
// ITCS 4180 : In Class Assignment 10
// User.java

package com.example.nrabbi.inclass10;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    String email;
    String phone;
    String dept;
    String avatar;

    public User() {

    }

    public User(String name, String email, String phone, String dept, String avatar) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dept = dept;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
