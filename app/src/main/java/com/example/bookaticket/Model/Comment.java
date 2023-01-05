package com.example.bookaticket.Model;

import com.example.bookaticket.R;

public class Comment {
    public int userAvatarPath;
    public String user;
    public int starsRate;
    public String text;

    public Comment(String user, int starsRate, String text, int userAvatarPath) {
        this.userAvatarPath = userAvatarPath;
        this.user = user;
        this.starsRate = starsRate;
        this.text = text;
    }
}
