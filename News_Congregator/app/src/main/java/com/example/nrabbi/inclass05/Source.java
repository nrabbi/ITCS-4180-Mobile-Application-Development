// Nazmul Rabbi & Dyrell Cole
// Source.java
// In Class Assignment # 5
// Group 20

package com.example.nrabbi.inclass05;

import java.io.Serializable;
import java.util.ArrayList;

public class Source implements Serializable {
    String id, name, description, url, category, language, country;
    UrlsToLogos urlsToLogos;
    ArrayList<String> sortBysAvailable;

    @Override
    public String toString() {
        return "Source{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Source() {

    }
}
