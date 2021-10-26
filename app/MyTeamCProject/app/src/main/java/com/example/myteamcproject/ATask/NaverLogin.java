package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;

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

public class NaverLogin extends AsyncTask<Void, Void, MemberDTO> {
    String id, email, name, phone;

    /*생성자 만들기*/
    public NaverLogin(String naver_id, String naver_email, String naver_name, String naver_phone) {
        this.id = naver_id;
        this.name = naver_email;
        this.email = naver_name;
        this.phone = naver_phone;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;


    @Override
    protected MemberDTO doInBackground(Void... voids) {
        try{
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            //문자열 및 데이터 추가
            builder.addTextBody("naver_id", id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("naver_email", email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("naver_name", name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("naver_phone", phone, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("naver", "naver", ContentType.create("Multipart/related", "UTF-8"));

            /*전송*/
            String postURL = ipConfig + "/anNaverLogin";

            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("hanul");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            loginDTO = readMessage(inputStream);

            inputStream.close();

        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }else if(httpResponse != null){
                httpResponse = null;
            }else if(httpPost != null) {
                httpPost = null;
            }else if(httpClient != null){
                httpClient= null;
            }

            return loginDTO;
        }
    }

    public MemberDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream,"UTF-8"));
        String naver_id = "";
        String naver_email = "";
        String naver_name = "";
        String naver_phone = "";
        String naver = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String Readerstr = reader.nextName();
            if (Readerstr.equals("id")) {
                naver_id = reader.nextString();
            }else if (Readerstr.equals("email")) {
                naver_email = reader.nextString();
            }else if (Readerstr.equals("name")) {
                naver_name = reader.nextString();
            } else if(Readerstr.equals("phone")){
                naver_phone = reader.nextString();
            }else if(Readerstr.equals("naver")){
                naver = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new MemberDTO(naver_id, naver_email, naver_name, naver_phone, naver);

    }
}
