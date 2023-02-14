package com.example.bookaticket.Model;

public class Comment {

    public String bookInfoID;
    public String id;
    public Long lastUpdated;
    public String userEmail;
    public int rate;
    public String text;

    public Comment(String bookInfoID, String userEmail, int rate, String text) {
        this.bookInfoID = bookInfoID;
        this.id = null;
        this.lastUpdated = lastUpdated;
        this.userEmail = userEmail;
        this.rate = rate;
        this.text = text;
    }

    public String getBookInfoID() {
        return bookInfoID;
    }

    public void setBookInfoID(String bookInfoID) {
        this.bookInfoID = bookInfoID;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}