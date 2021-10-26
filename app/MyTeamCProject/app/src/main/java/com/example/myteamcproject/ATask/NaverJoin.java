package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class NaverJoin extends AsyncTask<Void,Void,String> {
    String id, email, name;
    String phone;
    String naver = "naver";

    /*생성자 생성*/
    public NaverJoin(String naver_id, String naver_email, String naver_name, String naver_phone, String naver) {
        this.id = naver_id;
        this.email = naver_email;
        this.name = naver_name;
        this.phone = naver_phone;
        this.naver = naver;
    }
    /*데이터 베이스에 삽입한 결과 : state*/
    String state = "";
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            /*문자열 추가*/
            builder.addTextBody("naver_id", id, ContentType.create("Multipart/related","UTF-8"));
            builder.addTextBody("naver_email", email, ContentType.create("Multipart/related","UTF-8"));
            builder.addTextBody("naver_name", name, ContentType.create("Multipart/related","UTF-8"));
            builder.addTextBody("naver_phone", phone, ContentType.create("Multipart/related","UTF-8"));
            builder.addTextBody("naver", naver, ContentType.create("Multipart/related","UTF-8"));

            String postURL = ipConfig + "/anNaverJoin";
            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("hanul");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            state = stringBuilder.toString();

            inputStream.close();

        }catch (Exception e){
            e.getMessage();
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

        return state;
    }
    @Override
    protected void onPostExecute(String result) {

    }
}
