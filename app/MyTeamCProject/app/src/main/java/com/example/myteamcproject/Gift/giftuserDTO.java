package com.example.myteamcproject.Gift;

import java.io.Serializable;

public class giftuserDTO implements Serializable {

    int g_numb, g_point;
    String id, g_title;

    public giftuserDTO() {

    }

    public giftuserDTO(int g_numb, int g_point, String id, String g_title) {
        this.g_numb = g_numb;
        this.g_point = g_point;
        this.id = id;
        this.g_title = g_title;
    }

    public int getG_numb() {
        return g_numb;
    }

    public void setG_numb(int g_numb) {
        this.g_numb = g_numb;
    }

    public int getG_point() {
        return g_point;
    }

    public void setG_point(int g_point) {
        this.g_point = g_point;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getG_title() {
        return g_title;
    }

    public void setG_title(String g_title) {
        this.g_title = g_title;
    }
}
