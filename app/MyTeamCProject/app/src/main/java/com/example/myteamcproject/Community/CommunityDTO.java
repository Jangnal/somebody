package com.example.myteamcproject.Community;

import java.io.Serializable;

public class CommunityDTO implements Serializable {
    int c_numb;
    String c_title, c_content, c_writer, c_date;
    int c_readcount;
    String c_filename, c_filepath, c_category, c_secret;

    public CommunityDTO () {}

    public CommunityDTO(int c_numb, String c_title, String c_content, String c_writer, String c_date, int c_readcount, String c_filename, String c_filepath, String c_category) {
        this.c_numb = c_numb;
        this.c_title = c_title;
        this.c_content = c_content;
        this.c_writer = c_writer;
        this.c_date = c_date;
        this.c_readcount = c_readcount;
        this.c_filename = c_filename;
        this.c_filepath = c_filepath;
        this.c_category = c_category;
    }

    public int getC_numb() {
        return c_numb;
    }

    public void setC_numb(int c_numb) {
        this.c_numb = c_numb;
    }

    public String getC_title() {
        return c_title;
    }

    public void setC_title(String c_title) {
        this.c_title = c_title;
    }

    public String getC_content() {
        return c_content;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content;
    }

    public String getC_writer() {
        return c_writer;
    }

    public void setC_writer(String c_writer) {
        this.c_writer = c_writer;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }

    public int getC_readcount() {
        return c_readcount;
    }

    public void setC_readcount(int c_readcount) {
        this.c_readcount = c_readcount;
    }

    public String getC_filename() {
        return c_filename;
    }

    public void setC_filename(String c_filename) {
        this.c_filename = c_filename;
    }

    public String getC_filepath() {
        return c_filepath;
    }

    public void setC_filepath(String c_filepath) {
        this.c_filepath = c_filepath;
    }

    public String getC_category() {
        return c_category;
    }

    public void setC_category(String c_category) {
        this.c_category = c_category;
    }

    public String getC_secret() {
        return c_secret;
    }

    public void setC_secret(String c_secret) {
        this.c_secret = c_secret;
    }

}
