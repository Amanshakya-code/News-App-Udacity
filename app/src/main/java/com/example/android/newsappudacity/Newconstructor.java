package com.example.android.newsappudacity;

public class Newconstructor {
    private String title;
    private String discription;
    private String url;
    private String webdateandtime;
    private String author;

    public Newconstructor(String mtitle, String mdiscription, String murl, String mwebdateandtime,String mauthor) {
        this.title = mtitle;
        this.discription = mdiscription;
        this.webdateandtime = mwebdateandtime;
        this.url = murl;
        this.author=mauthor;
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
    public String getAuthor(){
        return author;
    }

}
