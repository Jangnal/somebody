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

public class KakaoJoin extends AsyncTask<Void, Void, String> {
    private static final String TAG = "KakaoLogin";
    long kakao_id;
    String kakao_name;
    String kakao = "kakao";

    public KakaoJoin(long kakao_id, String kakao_name) {
        this.kakao_id = kakao_id;
        this.kakao_name = kakao_name;
    }

    // 데이터베이스에 삽입결과 0보다크면 삽입성공, 같거나 작으면 실패
    String state = "";

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {
        try{
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            /*문자열 추가*/
            builder.addTextBody("kakao_id", String.valueOf(kakao_id), ContentType.create("Multipart/related","UTF-8"));
            builder.addTextBody("kakao_name", kakao_name, ContentType.create("Multipart/related","UTF-8"));
            builder.addTextBody("kakao", kakao, ContentType.create("Multipart/related","UTF-8"));

            String postURL = ipConfig + "/anKakaoJoin";

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
            Log.d(TAG, "doInBackground: " + e.getMessage());
            e.printStackTrace();
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

    @Override
    protected void onPostExecute(String result) {

    }

}
