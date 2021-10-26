package com.example.myteamcproject.Exercise;

import java.io.Serializable;

public class ExerciseDTO implements Serializable {
    int e_num;
    String e_type, e_name, e_content;
    int e_calorie;
    String e_filename, e_filepath;
    int e_point;
    String thumbnail;


    public ExerciseDTO(){

    };
    public ExerciseDTO(int e_num, String e_type, String e_name, String e_content, int e_calorie, String e_filename, String e_filepath, int e_point, String thumbnail) {
        this.e_num = e_num;
        this.e_type = e_type;
        this.e_name = e_name;
        this.e_content = e_content;
        this.e_calorie = e_calorie;
        this.e_filename = e_filename;
        this.e_filepath = e_filepath;
        this.e_point = e_point;
        this.thumbnail = thumbnail;
    }

    public int getE_num() {
        return e_num;
    }

    public void setE_num(int e_num) {
        this.e_num = e_num;
    }

    public String getE_type() {
        return e_type;
    }

    public void setE_type(String e_type) {
        this.e_type = e_type;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getE_content() {
        return e_content;
    }

    public void setE_content(String e_content) {
        this.e_content = e_content;
    }

    public int getE_calorie() {
        return e_calorie;
    }

    public void setE_calorie(int e_calorie) {
        this.e_calorie = e_calorie;
    }

    public String getE_filename() {
        return e_filename;
    }

    public void setE_filename(String e_filename) {
        this.e_filename = e_filename;
    }

    public String getE_filepath() {
        return e_filepath;
    }

    public void setE_filepath(String e_filepath) {
        this.e_filepath = e_filepath;
    }

    public int getE_point() {
        return e_point;
    }

    public void setE_point(int e_point) {
        this.e_point = e_point;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
