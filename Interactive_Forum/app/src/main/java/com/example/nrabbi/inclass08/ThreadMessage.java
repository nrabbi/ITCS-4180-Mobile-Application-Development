// Nazmul Rabbi & Dyrell Cole
// ITCS 4180 : In Class Assignment 8
// ThreadMessage.java
// Group 20

package com.example.nrabbi.inclass08;

import java.io.Serializable;

public class ThreadMessage implements Serializable{
    String user_fname;
    String user_lname;
    String user_id;
    String id;
    String title;
    String created_at;


    @Override
    public String toString() {
        return "ThreadMessage{" +
                "user_first_name='" + user_fname + '\'' +
                ", user_lname='" + user_lname + '\'' +
                ", user_id='" + user_id + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
