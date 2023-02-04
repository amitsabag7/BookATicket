package com.example.bookaticket.Model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.bookaticket.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Entity
public class Station {

    @PrimaryKey
    @NotNull
    public String id="";
    public Double xLocation=0.0;
    public Double yLocation=0.0;
    public String name="";
    public Long lastUpdated=null;

    public Station() {

    }
    public Station(String id,GeoPoint location,String name) {
        this.id = id;
        this.xLocation = location.getLatitude();
        this.yLocation = location.getLongitude();
        this.name = name;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();

    }

    public String getId() {
        return id;
    }

    public GeoPoint getLocation() {
        return new GeoPoint(xLocation,yLocation);
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(GeoPoint location) {
        this.xLocation = location.getLatitude();
        this.yLocation= location.getLongitude();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getxLocation() {
        return xLocation;
    }

    public void setxLocation(Double xLocation) {
        this.xLocation = xLocation;
    }

    public Double getyLocation() {
        return yLocation;
    }

    public void setyLocation(Double yLocation) {
        this.yLocation = yLocation;
    }

    static final String ID = "id";
    static final String LOCATION = "location";
    static final String NAME = "name";
    static final String COLLECTION = "stations";
    static final String LAST_UPDATED= "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "stations_local_last_update";

    public static Station fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String name = (String)json.get(NAME);
        GeoPoint location = (GeoPoint)json.get(LOCATION);
        Station st = new Station(id,location,name);

        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            st.setLastUpdated(time.getSeconds());
        } catch (Exception e) {

        }
        return st;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
