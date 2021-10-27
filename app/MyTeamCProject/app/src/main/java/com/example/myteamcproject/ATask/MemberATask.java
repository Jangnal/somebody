package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.MyattList;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myteamcproject.Member.MemberDTO;
import com.example.myteamcproject.Mypage.AttendanceDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MemberATask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "main:MemberATask";

    int bmi, weight, height, point;
    String id, email, name, phone, gender, reqC, password;
    MemberDTO dto;
    String imageRealPathA, imageDbPathA;

    public MemberATask(String reqC, String id) {
        this.reqC = reqC;
        this.id = id;
    }

    public MemberATask(String reqC, String id, String password) {
        this.reqC = reqC;
        this.id = id;
        this.password = password;
    }

    public MemberATask(String reqC, MemberDTO dto, String imageRealPathA, String imageDbPathA) {

        this.dto = dto;
        this.imageRealPathA = imageRealPathA;
        this.imageDbPathA = imageDbPathA;
    }

   public MemberATask(String reqC, String id, String email, String name, String gender
            , String phone, int bmi, int weight, int height, int point) {
        this.reqC = reqC;
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.bmi = bmi;
        this.weight = weight;
        this.height = height;
        this.point = point;
    }

    // 반드시 선언해야 할것들 : 무조건 해야함 복,붙
    HttpClient httpClient;  // 클라이언트 객체
    HttpPost httpPost;      // 클라이언트에 붙일 본문
    HttpResponse httpResponse; // 서버에서의 응답
    HttpEntity httpEntity;  // 응답 내용

    //값을 담기 위한 변수
    String body;

    // doInBackground 하기전에 설정 및 초기화
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // MultipartEntityBuilder 생성 : 무조건 해야함
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            if(reqC.equals("insert")){
                builder.addTextBody("id", dto.getId(), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("password", dto.getPassword(), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("email", dto.getEmail(), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("name", dto.getName(), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("phone", dto.getPhone(), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("height", String.valueOf(dto.getHeight()), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("weight", String.valueOf(dto.getWeight()), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("BMI", String.valueOf(dto.getBmi()), ContentType.create("Multipart/related", "UTF-8"));
                if (imageRealPathA != null) {
                    builder.addPart("imgpath", new FileBody(new File(imageRealPathA)));
                }//if
            }else if(reqC.equals("login")){
                builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("password", password, ContentType.create("Multipart/related", "UTF-8"));
            }else if(reqC.equals("myupdate")){
                builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("email", email, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("name", name, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("phone", phone, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("gender", gender, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("bmi", String.valueOf(bmi), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("weight", String.valueOf(weight), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("height", String.valueOf(height), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("point", String.valueOf(point), ContentType.create("Multipart/related", "UTF-8"));
            }else if(reqC.equals("AttrInsert") || reqC.equals("AttrSelect")){
                builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            }

            // 전송 url 작성
            String postURL = "";
            if(reqC.equals("insert")){
                postURL = ipConfig + "/insert.me";
            }else if(reqC.equals("login")){
                postURL = ipConfig + "/login.me";
            }else if(reqC.equals("myupdate")){
                postURL = ipConfig + "/myupdate.me";
            }else if(reqC.equals("AttrInsert")){
                postURL = ipConfig + "/AttrInsert.me";
            }else if(reqC.equals("AttrSelect")){
                postURL = ipConfig + "/AttrSelect.me";
            }//if

            //DB와 연동을 위한 http 객체 순서대로 실행
            httpClient = AndroidHttpClient.newInstance("Member");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);

            //DB에서 뽑아온 값을 문자열 형태로 body에 담는 다.
            body = EntityUtils.toString(httpResponse.getEntity());

            //body에 담은 값을 readMessage 메소드로 보내 List<DTO>로 변환한다
            if(!body.equals("")){
                if(reqC.equals("myupdate") || reqC.equals("login")){
                    loginDTO = makeMemberdto(body);
                }else if(reqC.equals("AttrSelect")){
                    MyattList = MakeAttList(body);
                }
            }//if

        }catch (IOException e){
            Log.d(TAG, "doInBackground: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }
        }
        return body;
    }

    // doInBackground 끝난 후에 오는 부분
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("main:", "onPostExecute: result => " + result);
    }//onPostExecute

    public MemberDTO makeMemberdto(String body) throws IOException {
        MemberDTO dto = null;
        try {
            JSONObject row = new JSONObject(body);
            dto = new MemberDTO();
            dto.setNumb(Integer.parseInt(row.getString("numb")));
            dto.setId(row.getString("id"));
            dto.setEmail(row.getString("email"));
            dto.setName(row.getString("name"));
            dto.setPhone(row.getString("phone"));
            if(!row.isNull("gender")){
                dto.setGender(row.getString("gender"));
            }
            dto.setBmi((float)row.getDouble("bmi"));
            dto.setWeight(Integer.parseInt(row.getString("weight")));
            dto.setHeight(Integer.parseInt(row.getString("height")));
            dto.setPoint(Integer.parseInt(row.getString("point")));
            if(!row.isNull("member_c_file_name")){
                dto.setMember_c_file_name(row.getString("member_c_file_name"));
                dto.setMember_c_file_path(row.getString("member_c_file_path"));
            }
            dto.setAdmin(row.getString("admin"));
        } catch (Exception e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        // 핸들러에게 메시지를 요청
//        Log.d("main:", "readMessage: " + dto.getId() );

        return dto;

    }

    public List<MemberDTO> readMessage(String body) throws IOException {
        //값을 담기 위한 list 선언
        List<MemberDTO> list = new ArrayList<MemberDTO>();
        try {
            //body에 담은 값을 json형태로 변환
            JSONArray jArray = new JSONArray(body);

            //for문을 돌려 값을 list<dto> 형태로 변환한다.
            for(int i=0; i<jArray.length();i++) {
                JSONObject row = jArray.getJSONObject(i);
                MemberDTO dto = new MemberDTO();
                dto.setNumb(Integer.parseInt(row.getString("numb")));
                dto.setId(row.getString("id"));
                dto.setEmail(row.getString("email"));
                dto.setName(row.getString("name"));
                dto.setPhone(row.getString("phone"));
                dto.setGender(row.getString("gender"));
                dto.setBmi(Integer.parseInt(row.getString("bmi")));
                dto.setWeight(Integer.parseInt(row.getString("weight")));
                dto.setHeight(Integer.parseInt(row.getString("height")));
                dto.setPoint(Integer.parseInt(row.getString("point")));

                list.add(dto);
            }

            Log.d(TAG, "readMessage: listsize => " + list.size());

        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        // 핸들러에게 메시지를 요청
        Log.d("main:", "readMessage: " + list.get(0).getId());

        //완성된 list를 반환한다.
        return list;

    }

    private List<AttendanceDTO> MakeAttList(String body) {
    //값을 담기 위한 list 선언
        List<AttendanceDTO> list = new ArrayList<AttendanceDTO>();
        try {
            //body에 담은 값을 json형태로 변환
            JSONArray jArray = new JSONArray(body);

            //for문을 돌려 값을 list<dto> 형태로 변환한다.
            for(int i=0; i<jArray.length();i++) {
                JSONObject row = jArray.getJSONObject(i);
                AttendanceDTO dto = new AttendanceDTO();
                dto.setAtt_id(Integer.parseInt(row.getString("Att_id")));
                dto.setAtt_point(row.getString("att_point"));
                dto.setAtt_memo(row.getString("att_memo"));
                dto.setAtt_continuity(row.getString("att_continuity"));
                dto.setAtt_Date(row.getString("att_Date"));
                list.add(dto);
            }

            Log.d(TAG, "readMessage: listsize => " + list.size());

        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        // 핸들러에게 메시지를 요청
        Log.d("main:", "readMessage: " + list.get(0).getId());

        //완성된 list를 반환한다.
        return list;
    }

}
