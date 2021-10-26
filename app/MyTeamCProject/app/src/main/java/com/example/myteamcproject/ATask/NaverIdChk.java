package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

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

public class NaverIdChk extends AsyncTask<Void,Void,String> {
    String id;

    public NaverIdChk(String naver_id) {
        this.id = naver_id;
    }
    String state = null;

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {
        /*DB와 연결해서 USER_ID가 있는지 없는지 유무를 확인한다.*/
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            builder.addTextBody("naver_id", id, ContentType.create("Multipart/related","UTF-8"));

            String postURL = ipConfig + "/anNaverIdChk";

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
            Log.d("NaverIdChk", "NaverIdChk : doInBackground: " + e.getMessage());
            e.printStackTrace();
            e.getMessage();
        }finally {
            httpEntity = null;
            httpResponse = null;
            httpPost = null;
            httpClient = null;
        }
        return state;
    }
}
