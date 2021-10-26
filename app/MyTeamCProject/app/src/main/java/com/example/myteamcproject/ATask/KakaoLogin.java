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
import java.nio.charset.Charset;

public class KakaoLogin extends AsyncTask<Void, Void, MemberDTO> {
    private static final String TAG = "카카오 로그인";
    long kakao_id;
    String kakao_email, kakao_name;
    String kakao = "kakao";

    public KakaoLogin(long kakao_id, String kakao_email, String kakao_name) {
        this.kakao_id = kakao_id;
        this.kakao_name = kakao_name;
        this.kakao_email = kakao_email;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected MemberDTO doInBackground(Void... voids) {
        /*MultipartEntityBuilder 생성 : 반드시 선언*/
        try{
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            /*문자열 추가*/
            builder.addTextBody("kakao_id", String.valueOf(kakao_id), ContentType.create("Multipart/related","UTF-8"));
            builder.addTextBody("kakao_email", kakao_name, ContentType.create("Multipart/related","UTF-8"));
            builder.addTextBody("kakao_name", kakao_email, ContentType.create("Multipart/related","UTF-8"));
            builder.addTextBody("kakao", kakao, ContentType.create("Multipart/related","UTF-8"));

            /*전송*/
            String postURL = ipConfig + "/anKakaoLogin";

            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("hanul");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            loginDTO = readMessage(inputStream);
            inputStream.close();

        }catch(Exception e){
            Log.d(TAG, "doInBackground: " + e.getMessage());
            e.getMessage();
        }finally {  //마지막에 무조건 수행해야할 것
            if(httpEntity != null){
                httpEntity = null;
            }else if(httpResponse != null){
                httpResponse = null;
            }else if(httpPost != null) {
                httpPost = null;
            }else if(httpClient != null){
                httpClient= null;
            }

         }
        return loginDTO;

    }

    public MemberDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream,"UTF-8"));
        String kakao_id = "";
        //String kakao_email ="";
        String kakao_name ="";
        String social_type ="";

        reader.beginObject();
        while (reader.hasNext()) {
            String Readerstr = reader.nextName();

            if (Readerstr.equals("id")) {
                kakao_id = reader.nextString();
            }else if(Readerstr.equals("name")) {
                kakao_name = reader.nextString();
            }else if(Readerstr.equals("kakao")) {
                kakao_email = reader.nextString();
            }else {
                reader.skipValue();
            }


        }
        reader.endObject();
        MemberDTO dto = new MemberDTO(kakao_id, kakao_name, kakao_email);
        return dto;

    }


}//doing
