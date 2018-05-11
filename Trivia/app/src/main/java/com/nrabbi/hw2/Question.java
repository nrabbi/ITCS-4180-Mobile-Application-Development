// ITCS 4180 : Homework 2
// Question.java
// Nazmul Rabbi, Dyrell Cole

package com.nrabbi.hw2;

import java.io.Serializable;
import java.util.Arrays;

public class Question implements Serializable {
    private int id;
    private String text;
    private String image;
    private String[] choices;
    private int answer;

    public Question() {}

    public Question(int id, String text, String image, String[] choices, int answer) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.choices = choices;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public String[] getChoices() {
        return choices;
    }

    public int getAnswer() {
        return answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answer=" + answer +
                ", choices=" + Arrays.toString(choices) +
                ", image='" + image + '\'' +
                ", text='" + text + '\'' +
                ", id=" + id +
                '}';
    }
}
