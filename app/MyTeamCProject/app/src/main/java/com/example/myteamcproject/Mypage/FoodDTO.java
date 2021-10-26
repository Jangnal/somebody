package com.example.myteamcproject.Mypage;

import java.io.Serializable;

public class FoodDTO implements Serializable {
    int numb;
    String id, writedate, morning, lunch, dinner, content
            , m_filename, m_filepath, l_filename, l_filepath
            ,d_filename, d_filepath;
    String Imgrealpath1, Imgrealpath2, Imgrealpath3;

    public FoodDTO(){};


    public FoodDTO(int numb, String id, String writedate, String morning, String lunch, String dinner, String content, String m_filename, String m_filepath, String l_filename, String l_filepath, String d_filename, String d_filepath) {
        this.numb = numb;
        this.id = id;
        this.writedate = writedate;
        this.morning = morning;
        this.lunch = lunch;
        this.dinner = dinner;
        this.content = content;
        this.m_filename = m_filename;
        this.m_filepath = m_filepath;
        this.l_filename = l_filename;
        this.l_filepath = l_filepath;
        this.d_filename = d_filename;
        this.d_filepath = d_filepath;
    }

    public int getNumb() {
        return numb;
    }

    public void setNumb(int numb) {
        this.numb = numb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWritedate() {
        return writedate;
    }

    public void setWritedate(String writedate) {
        this.writedate = writedate;
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getM_filename() {
        return m_filename;
    }

    public void setM_filename(String m_filename) {
        this.m_filename = m_filename;
    }

    public String getM_filepath() {
        return m_filepath;
    }

    public void setM_filepath(String m_filepath) {
        this.m_filepath = m_filepath;
    }

    public String getL_filename() {
        return l_filename;
    }

    public void setL_filename(String l_filename) {
        this.l_filename = l_filename;
    }

    public String getL_filepath() {
        return l_filepath;
    }

    public void setL_filepath(String l_filepath) {
        this.l_filepath = l_filepath;
    }

    public String getD_filename() {
        return d_filename;
    }

    public void setD_filename(String d_filename) {
        this.d_filename = d_filename;
    }

    public String getD_filepath() {
        return d_filepath;
    }

    public void setD_filepath(String d_filepath) {
        this.d_filepath = d_filepath;
    }

    public String getImgrealpath1() {
        return Imgrealpath1;
    }

    public void setImgrealpath1(String imgrealpath1) {
        Imgrealpath1 = imgrealpath1;
    }

    public String getImgrealpath2() {
        return Imgrealpath2;
    }

    public void setImgrealpath2(String imgrealpath2) {
        Imgrealpath2 = imgrealpath2;
    }

    public String getImgrealpath3() {
        return Imgrealpath3;
    }

    public void setImgrealpath3(String imgrealpath3) {
        Imgrealpath3 = imgrealpath3;
    }
}
