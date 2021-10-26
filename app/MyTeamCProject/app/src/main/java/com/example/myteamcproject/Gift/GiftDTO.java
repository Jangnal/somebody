package com.example.myteamcproject.Gift;

import java.io.Serializable;

public class GiftDTO implements Serializable {
    String gs_name, gs_content,gs_filename,gs_filepath;
    int gs_numb, ponint;

    public GiftDTO(String gs_name, String gs_content, String gs_filename, String gs_filepath, int gs_numb, int ponint) {
        this.gs_name = gs_name;
        this.gs_content = gs_content;
        this.gs_filename = gs_filename;
        this.gs_filepath = gs_filepath;
        this.gs_numb = gs_numb;
        this.ponint = ponint;
    }

    public GiftDTO() {

    }

    public String getGs_name() {
        return gs_name;
    }

    public void setGs_name(String gs_name) {
        this.gs_name = gs_name;
    }

    public String getGs_content() {
        return gs_content;
    }

    public void setGs_content(String gs_content) {
        this.gs_content = gs_content;
    }

    public String getGs_filename() {
        return gs_filename;
    }

    public void setGs_filename(String gs_filename) {
        this.gs_filename = gs_filename;
    }

    public String getGs_filepath() {
        return gs_filepath;
    }

    public void setGs_filepath(String gs_filepath) {
        this.gs_filepath = gs_filepath;
    }

    public int getGs_numb() {
        return gs_numb;
    }

    public void setGs_numb(int gs_numb) {
        this.gs_numb = gs_numb;
    }

    public int getPonint() {
        return ponint;
    }

    public void setPonint(int ponint) {
        this.ponint = ponint;
    }

}
