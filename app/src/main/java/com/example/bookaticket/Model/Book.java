package com.example.bookaticket.Model;

import java.util.List;

public class Book {
    public String name;
    public String imgPath;
    public int year;
    public String writer;
    public String description;
    public List<Comment> comments;
    public boolean isReturned;

    public Book(String name, String imgPath, int year, String writer, String description,
                List<Comment> comments, boolean isReturned) {
        this.name = name;
        this.imgPath = imgPath;
        this.year = year;
        this.writer = writer;
        this.description = description;
        this.comments = comments;
        this.isReturned = isReturned;
    }
}
