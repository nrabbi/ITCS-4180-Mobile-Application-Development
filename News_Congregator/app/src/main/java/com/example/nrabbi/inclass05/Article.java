// Nazmul Rabbi & Dyrell Cole
// Article.java
// In Class Assignment # 5
// Group 20

package com.example.nrabbi.inclass05;

import java.io.Serializable;

public class Article implements Serializable {
    String author, title, description, url, urlToImage, publishedAt;

    public Article() {

    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
