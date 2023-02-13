package com.example.bookaticket.Model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class BookInstance {


    public String bookInfoID;
    public String id;
    public Long lastUpdated;
    public String stationID;
    public String userEmail;

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

    public static void setLocalLastUpdated(Long time) {

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
}
