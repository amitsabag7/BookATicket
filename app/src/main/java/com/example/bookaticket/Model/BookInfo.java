package com.example.bookaticket.Model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.bookaticket.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.GeoPoint;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Entity
public class BookInfo implements Serializable {

    @PrimaryKey
    @NotNull
    private String id="";
    private String title="";
    private String subtitle="";
    private String author="";
    private String publisher="";
    private String publishedDate="";
    private String description="";
    private Long pageCount=new Long(0);
    private String thumbnail="";
    private String previewLink="";
    private String infoLink="";
    private String buyLink="";

    public void setId(@NotNull String id) {
        this.id = id;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public Long lastUpdated=null;

    static final String ID = "id";
    static final String TITLE = "title";
    static final String SUBTITLE = "subtitle";
    static final String AUTHOR = "author";
    static final String PUBLISHER= "publisher";
    static final String PUBLISHED_DATE = "publishedDate";
    static final String DESCRIPTION= "description";
    static final String PAGE_COUNT= "pageCount";
    static final String THUMBNAIL= "thumbnail";
    static final String PREVIEW_LINK= "previewLink";
    static final String INFO_LINK= "infoLink";
    static final String LAST_UPDATED= "lastUpdated";
    static final String BUY_LINK= "buyLink";

    public static BookInfo fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String title = (String)json.get(TITLE);
        String subtitle = (String)json.get(SUBTITLE);
        String author = (String)json.get(AUTHOR);
        String publisher = (String)json.get(PUBLISHER);
        String publishedDate = (String)json.get(PUBLISHED_DATE);
        String description = (String)json.get(DESCRIPTION);
        Long pageCount = (Long)json.get(PAGE_COUNT);
        String thumbnail = (String)json.get(THUMBNAIL);
        String previewLink = (String)json.get(PREVIEW_LINK);
        String infoLink = (String)json.get(INFO_LINK);
        String buyLink = (String)json.get(BUY_LINK);
        BookInfo bookInfo = new BookInfo(id, title, subtitle, author, publisher, publishedDate,
                description, pageCount, thumbnail, previewLink, infoLink, buyLink);

        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            bookInfo.setLastUpdated(time.getSeconds());
        } catch (Exception e) {

        }
        return bookInfo;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(TITLE, getTitle());
        json.put(SUBTITLE, getSubtitle());
        json.put(AUTHOR, getAuthor());
        json.put(PUBLISHER, getPublisher());
        json.put(PUBLISHED_DATE, getPublishedDate());
        json.put(DESCRIPTION, getDescription());
        json.put(PAGE_COUNT, getPageCount());
        json.put(THUMBNAIL, getThumbnail());
        json.put(PREVIEW_LINK, getPreviewLink());
        json.put(INFO_LINK, getInfoLink());
        json.put(BUY_LINK, getBuyLink());
        json.put("lastUpdated", FieldValue.serverTimestamp());
        return json;
    }

    public static Long getLocalLastUpdated() {
        SharedPreferences sharedPref= MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong("book_info_local_last_update", 0);
    }

    public static void setLocalLastUpdated(Long time){
        SharedPreferences sharedPref= MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("book_info_local_last_update", time);
        editor.commit();
    }

    // creating getter and setter methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

//    public String authorsToString() {
//        if(authors.size() == 0) {
//            return "";
//        }
//
//        StringBuilder authorsList = new StringBuilder();
//
//        for (String author : authors) {
//            authorsList.append(author).append(", ");
//        }
//        return authorsList.toString();
//    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }


    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public BookInfo(){}

    // for from json with id
    public BookInfo(String id, String title, String subtitle, String author, String publisher,
                    String publishedDate, String description, Long pageCount, String thumbnail,
                    String previewLink, String infoLink, String buyLink) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
        this.buyLink = buyLink;
    }

    // for new one, without id
    public BookInfo(String title, String subtitle, String author, String publisher,
                    String publishedDate, String description, Long pageCount, String thumbnail,
                    String previewLink, String infoLink, String buyLink) {
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
        this.buyLink = buyLink;
    }

    public BookInfo (String title, String imgPath, String writer, String description){
        this.title = title;
        this.subtitle = null;
        this.author = null;
        this.publisher = writer;
        this.publishedDate = null;
        this.description = description;
        this.pageCount = new Long(0);
        this.thumbnail = imgPath;
        this.previewLink = null;
        this.infoLink = null;
        this.buyLink = null;
    }
}
