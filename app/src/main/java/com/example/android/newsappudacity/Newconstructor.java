package com.example.android.newsappudacity;

import java.util.ArrayList;

public class Newconstructor {
    private String title;
    private String discription;
    private String url;
    private String webdateandtime;
    private ArrayList<String> authors;

    public Newconstructor(String mtitle, String mdiscription, String murl, String mwebdateandtime,ArrayList<String> mauthor) {
        this.title = mtitle;
        this.discription = mdiscription;
        this.webdateandtime = mwebdateandtime;
        this.url = murl;
        this.authors=mauthor;
    }

    public String getTitle() {
        return title;
    }

    public String getDiscription() {
        return discription;
    }

    public String getWebdateandtime() {
        return webdateandtime;
    }

    public String getUrl() {
        return url;
    }
    ArrayList<String> getAuthors() {
        return authors;
    }

}
