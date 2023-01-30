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


    public String getName() {
        return name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public int getYear() {
        return year;
    }

    public String getWriter() {
        return writer;
    }

    public String getDescription() {
        return description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
