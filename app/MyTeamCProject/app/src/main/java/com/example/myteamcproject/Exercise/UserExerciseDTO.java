package com.example.myteamcproject.Exercise;

import java.io.Serializable;

public class UserExerciseDTO implements Serializable {
    int u_numb;
    String id;
    String e_name;
    String u_date;
    int e_count;
    int u_calorie;
    int u_point;
    String u_complete;
    String isChecked;

    public UserExerciseDTO(){

    }

    public UserExerciseDTO(int u_numb, String id, String e_name, String u_date, int e_count, int u_calorie, int u_point, String u_complete, String isChecked) {
        this.u_numb = u_numb;
        this.id = id;
        this.e_name = e_name;
        this.u_date = u_date;
        this.e_count = e_count;
        this.u_calorie = u_calorie;
        this.u_point = u_point;
        this.u_complete = u_complete;
        this.isChecked = isChecked;
    }

    public int getU_numb() {
        return u_numb;
    }

    public void setU_numb(int u_numb) {
        this.u_numb = u_numb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getU_date() {
        return u_date;
    }

    public void setU_date(String u_date) {
        this.u_date = u_date;
    }

    public int getE_count() {
        return e_count;
    }

    public void setE_count(int e_count) {
        this.e_count = e_count;
    }

    public int getU_calorie() {
        return u_calorie;
    }

    public void setU_calorie(int u_calorie) {
        this.u_calorie = u_calorie;
    }

    public int getU_point() {
        return u_point;
    }

    public void setU_point(int u_point) {
        this.u_point = u_point;
    }

    public String getU_complete() {
        return u_complete;
    }

    public void setU_complete(String u_complete) {
        this.u_complete = u_complete;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
}
