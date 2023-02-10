package com.example.bookaticket.Model;

public class Comment {

    public String bookInfoID;
    public String id;
    public Long lastUpdated;
    public String userEmail;
    public int rate;
    public String text;

    public Comment(String bookInfoID, String id, String userEmail, int rate, String text) {
        this.bookInfoID = bookInfoID;
        this.id = id;
        this.lastUpdated = lastUpdated;
        this.userEmail = userEmail;
        this.rate = rate;
        this.text = text;
    }
}
