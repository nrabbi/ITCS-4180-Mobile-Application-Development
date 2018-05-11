// Group 20 : Nazmul Rabbi and Dyrell Cole
// ITCS 4180 : In Class Assignment #07
// Contact.java
// 3/20/18

package com.example.nrabbi.inclass07;

public class Contact  {
    String name, email, phone, dept;
    int image;

    public Contact(String name, String email, String phone, String dept, int image) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dept = dept;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}
