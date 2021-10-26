package com.example.myteamcproject.Member;

import java.io.Serializable;

public class MemberDTO implements Serializable {
    private int numb;
    private String id, email, password, name, phone, gender;
    private String kakao, naver;
    private int point, height, weight;
    private float bmi;
    private String member_c_file_name, member_c_file_path;

    public MemberDTO(){};

    //아이디 중복 체크할 때
    public MemberDTO(String id, String name){
        this.id = id;
        this.name = name;
    }

    //네이버 로그인
    public MemberDTO(String naver_id, String naver_name, String naver_email, String naver_phone, String naver) {
        this.id = naver_id;
        this.email = naver_email;
        this.name = naver_name;
        this.phone = naver_phone;
        this.naver = naver;
    }

    //카카오 로그인
    public MemberDTO(String kakao_id, String kakao_email ,String kakao_name){
        this.id = kakao_id;
        this.email = kakao_email;
        this.name = kakao_name;
    }

    public MemberDTO(String id, String password, String email, String name, String phone, float bmi, int height, int weight) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.bmi = bmi;
        this.height = height;
        this.weight = weight;
    }

    public MemberDTO(int numb, String id, String password, String email, String name, String phone, String gender, float bmi, int point, int height, int weight, String member_c_file_name, String member_c_file_path) {
        this.numb = numb;
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.bmi = bmi;
        this.point = point;
        this.height = height;
        this.weight = weight;
        this.member_c_file_name = member_c_file_name;
        this.member_c_file_path = member_c_file_path;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKakao() {
        return kakao;
    }

    public void setKakao(String kakao) {
        this.kakao = kakao;
    }

    public String getNaver() {
        return naver;
    }

    public void setNaver(String naver) {
        this.naver = naver;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getMember_c_file_name() {
        return member_c_file_name;
    }

    public void setMember_c_file_name(String member_c_file_name) {
        this.member_c_file_name = member_c_file_name;
    }

    public String getMember_c_file_path() {
        return member_c_file_path;
    }

    public void setMember_c_file_path(String member_c_file_path) {
        this.member_c_file_path = member_c_file_path;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }
}
