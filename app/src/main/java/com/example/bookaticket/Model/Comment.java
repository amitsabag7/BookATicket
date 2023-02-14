package com.example.bookaticket.Model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.bookaticket.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Comment {

    @PrimaryKey
    public String id="";
    public String bookInfoID="";
    public Long lastUpdated=null;
    public String userEmail="";
    public int rate=0;
    public String text="";

    public Comment(){}

    public Comment(String bookInfoID, String id, String userEmail, int rate, String text) {
        this.bookInfoID = bookInfoID;
        this.id = id;
        this.userEmail = userEmail;
        this.rate = rate;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getBookInfoID() {
        return bookInfoID;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getRate() {
        return rate;
    }

    public String getText() {
        return text;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBookInfoID(String bookInfoID) {
        this.bookInfoID = bookInfoID;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static Long getLocalLastUpdated() {
        SharedPreferences sharedPref= MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong("comment_local_last_update", 0);
    }

    public static void setLocalLastUpdated(Long time){
        SharedPreferences sharedPref= MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("comment_local_last_update", time);
        editor.commit();
    }

    public static Comment fromJson(Map<String, Object> data) {
        String id = (String) data.get("id");
        String bookInfoID = (String) data.get("bookInfoID");
        String userEmail = (String) data.get("userEmail");
        int rate = (int) data.get("rate");
        String text = (String) data.get("text");
        Comment c = new Comment(id, bookInfoID, userEmail, rate, text);
        try{
            Timestamp time = (Timestamp) data.get("lastUpdated");
            c.setLastUpdated(time.getSeconds());
        }catch (Exception e){

        }
//        Long lastUpdated = ((Timestamp) data.get("lastUpdated")).getSeconds();
        return c;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("bookInfoID", getBookInfoID());
        json.put("rate", getRate());
        json.put("userEmail", getUserEmail());
        json.put("text", getText());
        json.put("lastUpdated", FieldValue.serverTimestamp());
        return json;
    }
}
