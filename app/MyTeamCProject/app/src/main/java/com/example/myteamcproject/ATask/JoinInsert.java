package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myteamcproject.Member.MemberDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class JoinInsert extends AsyncTask<Void, Void, String> {

    private static final String TAG = "JoinInsert";
    // 생성자를 만든다
    String id, password, email, name, phone;
    int height, weight;
    String imageRealPathA, imageDbPathA;
    MemberDTO dto;
    public JoinInsert(String id, String password, String name, String email,
                      String phone, int height, int weight) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.height = height;
        this.weight = weight;
    }

    String state = "";
    // 반드시 선언해야 할것들 : 무조건 해야함 복,붙
    HttpClient httpClient;  // 클라이언트 객체
    HttpPost httpPost;      // 클라이언트에 붙일 본문
    HttpResponse httpResponse; // 서버에서의 응답
    HttpEntity httpEntity;  // 응답 내용

    public JoinInsert(MemberDTO dto, String imageRealPathA, String imageDbPathA) {
        this.dto = dto;
        this.imageRealPathA = imageRealPathA;
        this.imageDbPathA = imageDbPathA;
    }

    // doInBackground 하기전에 설정 및 초기화
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // 실질적으로 일을 하는 부분 : 접근 못함(textView, button)
    @Override
    protected String doInBackground(Void... voids) {
        try {
            // MultipartEntityBuilder 생성 : 무조건 해야함
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 우리가 수정해야 하는 부분 : 서버에 보내는 데이터
            // 문자열 및 데이터 추가하기
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

            // 전송
            String postURL = ipConfig + "/androidjoin.me";

            Log.d(TAG, "doInBackground: " + postURL);
            // 그대로 사용
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("join");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); // 응답
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답 : 문자열 형태
            BufferedReader bufferedReader = new
                    BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            state = stringBuilder.toString();

            inputStream.close();

        }catch (Exception e){
            e.getMessage();
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

        return state;
    }

    // doInBackground 끝난후에 오는부분
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d("main:", "onPostExecute: result => " + result);
    }
}
