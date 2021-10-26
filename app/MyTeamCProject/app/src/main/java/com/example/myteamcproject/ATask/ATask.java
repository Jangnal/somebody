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

public class ATask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "main: ";
    String text;
    public ATask(String text) {
        this.text = text;
    }

    String state = "";
    //반드시 선언해야 할것들 : 무조건 해야함
    HttpClient httpClient;      // 클라이언트 객체
    HttpPost httpPost;          // 클라이언트에 붙일 본문
    HttpResponse httpResponse;  // 서버에서의 응답
    HttpEntity httpEntity;      // 응답 내용

    // doInBackground 하기전에 설정 및 초기화
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
        //MultipartEntityBuilder 생성 : 무조건 해야함
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.setCharset(Charset.forName("UTF-8"));

        //우리가 수정해야 하는 부분 : 서버에 보내는 데이터
        //문자열 및 데이터 추가하기
        builder.addTextBody("text",text, ContentType.create("Multipart/related", "UTF-8"));

        //전송
        String postURL = ipConfig + "/project/test.me";

        InputStream inputStream = null;
        httpClient = AndroidHttpClient.newInstance("android");
        httpPost = new HttpPost(postURL);
        httpPost.setEntity(builder.build());
        httpResponse = httpClient.execute(httpPost);    //응답
        httpEntity = httpResponse.getEntity();
        inputStream = httpEntity.getContent();

            // 응답 : 문자열 형태
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null){
                stringBuilder.append(line + "\n");
            }//while

            state = stringBuilder.toString();

            inputStream.close();

        }catch(Exception e){
            Log.d(TAG, "doInBackground: " + e.getMessage());
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }//if
            if(httpResponse != null){
                httpResponse = null;
            }//if
            if(httpPost != null){
                httpPost = null;
            }//if
            if(httpClient != null){
                httpClient = null;
            }//if
        }//try & catch & finally
        return state;
    }

    // doInBackground 끝난 후에 오는 부분
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(TAG, "onPostExecute: result : " + result.trim());
    }//onPostExecute

}
