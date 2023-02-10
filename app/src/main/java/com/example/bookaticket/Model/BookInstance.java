package com.example.bookaticket.Model;

import com.google.firebase.Timestamp;

import java.util.Map;

public class BookInstance {
    public String bookInfoID;
    public String id;
    public Long lastUpdated;
    public String stationID;
    public String userEmail;

    public BookInstance(String bookInfoID, String id, Long lastUpdated, String stationID, String userEmail) {
        this.bookInfoID = bookInfoID;
        this.id = id;
        this.lastUpdated = lastUpdated;
        this.stationID = stationID;
        this.userEmail = userEmail;
    }

    public static BookInstance fromJson(Map<String, Object> data) {
        String bookInfoID = (String) data.get("bookInfoID");
        String id = (String) data.get("id");
        String stationID = (String) data.get("stationID");
        String userEmail = (String) data.get("userEmail");
        Long lastUpdated = ((Timestamp) data.get("lastUpdated")).getSeconds();
        return new BookInstance(bookInfoID, id, lastUpdated, stationID, userEmail);
    }
}
