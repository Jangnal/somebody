package com.example.myteamcproject.Gift;

import java.io.Serializable;

public class CartDTO implements Serializable {
    String cart_title,id, filepath, filename, content;
    int point;

    public CartDTO() {

    }

    public CartDTO(String cart_title, String id, String filepath, int point) {
        this.cart_title = cart_title;
        this.id = id;
        this.filepath = filepath;
        this.point = point;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCart_title() {
        return cart_title;
    }

    public void setCart_title(String cart_title) {
        this.cart_title = cart_title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }


}
