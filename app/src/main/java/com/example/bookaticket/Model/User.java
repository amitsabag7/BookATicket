package com.example.bookaticket.Model;

import java.util.HashMap;
import java.util.Map;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public static User fromJson(Map<String,Object> json){
        String userName = (String)json.get("userName");
        String homeTown = (String)json.get("homeTown");
        String email = (String)json.get("email");
        String profileImg = (String)json.get("profileImg");
        User user = new User(userName,homeTown,email,profileImg);

        return user;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put("userName", getUserName());
        json.put("homeTown", getHomeTown());
        json.put("email", getEmail());
        json.put("profileImg", getProfileImg());
        return json;
    }
}
