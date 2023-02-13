package com.example.bookaticket.Model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.bookaticket.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Entity
public class BookInstance {

    @PrimaryKey
    @NotNull
    public String id="";
    public String bookInfoID="";
    public Long lastUpdated=null;
    public String stationID="";
    public String userEmail="";

    public BookInstance() {

    }
    public BookInstance(String bookInfoID, String id,
//                        Long lastUpdated,
                        String stationID, String userEmail) {
        this.bookInfoID = bookInfoID;
        this.id = id;
//        this.lastUpdated = lastUpdated;
        this.stationID = stationID;
        this.userEmail = userEmail;
    }

    public static BookInstance fromJson(Map<String, Object> data) {
        String bookInfoID = (String) data.get("bookInfoID");
        String id = (String) data.get("id");
        String stationID = (String) data.get("stationID");
        String userEmail = (String) data.get("userEmail");
        BookInstance bi = new BookInstance(bookInfoID, id, stationID, userEmail);
        try{
            Timestamp time = (Timestamp) data.get("lastUpdated");
            bi.setLastUpdated(time.getSeconds());
        }catch (Exception e){

        }
//        Long lastUpdated = ((Timestamp) data.get("lastUpdated")).getSeconds();
        return bi;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("bookInfoID", getBookInfoID());
        json.put("stationID", getStationID());
        json.put("userEmail", getUserEmail());
        json.put("lastUpdated", FieldValue.serverTimestamp());
        return json;
    }

    public static Long getLocalLastUpdated(Long time) {
        SharedPreferences sharedPref= MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong("book_instance_local_last_update", 0);
    }

    public static void setLocalLastUpdated(Long time){
        SharedPreferences sharedPref= MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("book_instance_local_last_update", time);
        editor.commit();
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public static Long getLocalLastUpdated() {
        return new Long(0);
    }

    public String getBookInfoID() {
        return bookInfoID;
    }

    public String getId() {
        return id;
    }

    public String getStationID() {
        return stationID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setBookInfoID(@NotNull String bookInfoID) {
        this.bookInfoID = bookInfoID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
