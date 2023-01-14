package com.example.bookaticket.Model;

import com.google.firebase.firestore.GeoPoint;

import java.util.Map;

public class Station {
    private String id;
    private GeoPoint location;
    private String name;

    public Station() {

    }
    public Station(String id,GeoPoint location,String name) {
        this.id = id;
        this.location = location;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    static final String ID = "id";
    static final String LOCATION = "location";
    static final String NAME = "name";
    static final String COLLECTION = "stations";

    public static Station fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String name = (String)json.get(NAME);
        GeoPoint location = (GeoPoint)json.get(LOCATION);
        Station st = new Station(id,location,name);
        return st;
    }
}
