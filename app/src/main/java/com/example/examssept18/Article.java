package com.example.examssept18;

public class Article {

    private String sourceName;
    private String title;
    private String description;
    private String url;


    public Article(String sourceName, String title, String description, String url) {
        this.sourceName = sourceName;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public Article() {
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString(){
        return title +" - "+ description +" - "+ url;
    }

}