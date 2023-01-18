package com.example.bookaticket.Model;

import android.media.Image;

public class User {

    public String userName;
    public String homeTown;
    public String email;
    public String profileImg;

    public User(String userName,String homeTown,String email, String profileImg){
        this.userName = userName;
        this.homeTown = homeTown;
        this.email = email;
        this.profileImg = profileImg;
    }
}
