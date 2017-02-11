package com.example.pawdaw.events;

/**
 * Created by pawdaw on 23/05/16.
 */
public class Event {

    private int id;
    private String title;
    private String subTitle;
    private String startTime;
    private String endTime;
    private String description;
    private String URL;
    private String imageURL;

    public Event(int id, String title, String subTitle, String startTime, String endTime, String description, String URL, String imageURL) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.URL = URL;
        this.imageURL = imageURL;
    }

    public Event( String title, String subTitle, String startTime, String endTime, String description, String URL, String imageURL) {

        this(-1,title,subTitle,startTime,endTime,description,URL,imageURL);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", description='" + description + '\'' +
                ", URL='" + URL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
