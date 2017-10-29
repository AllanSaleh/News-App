package com.example.allan.newsapp;

public class News {
    private String image;
    private String webTitle;
    private String sectionName;
    private String authorName;
    private String webPublicationDate;
    private String webURL;

    public News(String webTitle, String sectionName, String webPublicationDate, String webURL, String image, String authorName) {
        this.image = image;
        this.webTitle = webTitle;
        this.sectionName = sectionName;
        this.authorName = authorName;
        this.webPublicationDate = webPublicationDate;
        this.webURL = webURL;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }
}
