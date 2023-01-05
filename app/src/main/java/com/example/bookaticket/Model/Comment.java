package com.example.bookaticket.Model;

public class Comment {
    public String user;
    public int starsRate;
    public String text;

    public Comment(String user, int starsRate, String text) {
        this.user = user;
        this.starsRate = starsRate;
        this.text = text;
    }
}
