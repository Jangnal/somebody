package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;


import com.example.myteamcproject.Member.MemberDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginSelect extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "LoginSelect";

    String id, password;

    public LoginSelect(String id, String password) {
        this.id = id;
        this.password = password;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    /*@Override  // 없어도 됨
    protected void onPreExecute() {

    }*/

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("password", password, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/androidlogin.me";


            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Login");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 하나의 오브젝트 가져올때
            loginDTO = readMessage(inputStream);

            inputStream.close();

        } catch (Exception e) {
            Log.d("main:LoginSelect", e.getMessage());
            e.printStackTrace();
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

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }

    public MemberDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        int numb = 0;
        String id = "", email = "", password = "", name = "", phone = "", gender = "";
        int point = 0, height = 0 , weight = 0;
        float bmi = 0;
        String member_c_file_name = "", member_c_file_path = "";


        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("numb")) {
                numb = reader.nextInt();
            } else if (readStr.equals("id")) {
                id = reader.nextString();
            } else if (readStr.equals("password")) {
                password = reader.nextString();
            } else if (readStr.equals("email")) {
                email = reader.nextString();
            } else if (readStr.equals("name")) {
                name = reader.nextString();
            }else if (readStr.equals("phone")) {
                phone = reader.nextString();
            }else if (readStr.equals("gender")) {
                gender = reader.nextString();
            }else if (readStr.equals("bmi")){
                bmi = (float) reader.nextDouble(); //String 변수를 숫자(int/float)로 변환
            }else if (readStr.equals("point")){
                point = reader.nextInt(); //String 변수를 숫자(int/float)로 변환
            }else if (readStr.equals("height")){
                height = reader.nextInt(); //String 변수를 숫자(int/float)로 변환
            }else if (readStr.equals("weight")){
                weight = reader.nextInt(); //String 변수를 숫자(int/float)로 변환
            }else if (readStr.equals("member_c_file_name")) {
                member_c_file_name = reader.nextString();
            }else if (readStr.equals("member_c_file_path")) {
                member_c_file_path = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        MemberDTO dto = new MemberDTO(numb, id, password, email, name, phone, gender, bmi, point, height, weight, member_c_file_name, member_c_file_path);

        Log.d("main:", "readMessage: " + dto.getId() + "," + dto.getPassword());
        return dto;

    }
}
