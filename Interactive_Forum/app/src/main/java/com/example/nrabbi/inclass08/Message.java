// Nazmul Rabbi & Dyrell Cole
// ITCS 4180 : In Class Assignment 8
// Message.java
// Group 20

package com.example.nrabbi.inclass08;

public class Message {
    String user_fname;
    String user_lname;
    long user_id;
    long id;
    String message;
    String created_at;

    public Message(String user_fname, String user_lname, long user_id, long id, String message, String created_at) {
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.user_id = user_id;
        this.id = id;
        this.message = message;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Message{" +
                "user_fname='" + user_fname + '\'' +
                ", user_lname='" + user_lname + '\'' +
                ", user_id=" + user_id +
                ", id=" + id +
                ", message='" + message + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
