package com.example.bookaticket;

import androidx.lifecycle.ViewModel;

import com.example.bookaticket.Model.User;

public class UserViewModel extends ViewModel {
    private User user = new User();

    User getUser(){
        return user;
    }

    void setUser(User newUser){
       user.setUserName(newUser.getUserName());
       user.setEmail(newUser.getEmail());
       user.setHomeTown(newUser.getHomeTown());
       user.setProfileImg(newUser.getProfileImg());
    }
}
