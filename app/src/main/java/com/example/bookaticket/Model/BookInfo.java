package com.example.bookaticket.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class BookInfo implements Serializable {
    private String title;
    private String subtitle;
    private ArrayList<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private int pageCount;
    private String thumbnail;
    private String previewLink;
    private String infoLink;
        private String buyLink;

    public static BookInfo fromJson(Map<String, Object> data) {
        String title = (String) data.get("title");
        String subtitle = (String) data.get("subtitle");
        ArrayList<String> authors = (ArrayList<String>) data.get("authors");
        String publisher = (String) data.get("publisher");
        String publishedDate = (String) data.get("publishedDate");
        String description = (String) data.get("description");
        int pageCount = ((Long) Objects.requireNonNull(data.get("pageCount"))).intValue();
        String thumbnail = (String) data.get("thumbnail");
        String previewLink = (String) data.get("previewLink");
        String infoLink = (String) data.get("infoLink");
        String buyLink = (String) data.get("buyLink");
        return new BookInfo(title, subtitle, authors, publisher, publishedDate, description, pageCount, thumbnail, previewLink, infoLink, buyLink);
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

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String authorsToString() {
        if(authors.size() == 0) {
            return "";
        }

        StringBuilder authorsList = new StringBuilder();

        for (String author : authors) {
            authorsList.append(author).append(", ");
        }
        return authorsList.toString();
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
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

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
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

    // creating a constructor class for our BookInfo
    public BookInfo(String title, String subtitle, ArrayList<String> authors, String publisher,
                    String publishedDate, String description, int pageCount, String thumbnail,
                    String previewLink, String infoLink, String buyLink) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
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
        this.authors = null;
        this.publisher = writer;
        this.publishedDate = null;
        this.description = description;
        this.pageCount = 0;
        this.thumbnail = imgPath;
        this.previewLink = null;
        this.infoLink = null;
        this.buyLink = null;
    }
}
