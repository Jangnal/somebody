package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.exlist;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myteamcproject.Exercise.ExerciseDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ExerciseATask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "ExerciseATask";
    int e_num;
    String e_type, e_name, e_content;
    int e_calorie;
    String e_filename, e_filepath;
    int e_point;
    String reqC;

    public ExerciseATask(String reqC) {
        this.reqC = reqC;
    }
    public ExerciseATask(String reqC, String e_type) {
        this.reqC = reqC;
        this.e_type = e_type;
    }

    String state = "";
    // 반드시 선언해야 할것들 : 무조건 해야함 복,붙
    HttpClient httpClient;  // 클라이언트 객체
    HttpPost httpPost;      // 클라이언트에 붙일 본문
    HttpResponse httpResponse; // 서버에서의 응답
    HttpEntity httpEntity;  // 응답 내용
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

        if(reqC.equalsIgnoreCase("ex")){
            builder.addTextBody("e_type", e_type, ContentType.create("Multipart/related", "UTF-8"));
        }

        // 전송
        String postURL = "";
        if(reqC.equals("type")){
            postURL = ipConfig + "/androEXTlist.me";
        }else if(reqC.equals("ex")){
            postURL = ipConfig + "/androEXlist.me";
        }

        Log.d(TAG, "doInBackground: " + postURL);
        InputStream inputStream = null;
        httpClient = AndroidHttpClient.newInstance("Execise");
        httpPost = new HttpPost(postURL);
        httpPost.setEntity(builder.build());
        httpResponse = httpClient.execute(httpPost); // 응답
        body = EntityUtils.toString(httpResponse.getEntity());

            // 하나의 오브젝트 가져올때
            exlist = (List<ExerciseDTO>) readMessage(body);

        } catch (IOException e) {
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

        return null;
    }

    // doInBackground 끝난후에 오는부분
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d("main:", "onPostExecute: result => " + result);
    }

    public List<ExerciseDTO> readMessage(String body) throws IOException {
        List<ExerciseDTO> list = new ArrayList<ExerciseDTO>();
        try {
            JSONArray jArray = new JSONArray(body);
            for(int i=0; i<jArray.length();i++) {
                JSONObject row = jArray.getJSONObject(i);
                ExerciseDTO dto = new ExerciseDTO();
                if(reqC.equals("type")){
                    dto.setE_type(row.getString("e_type"));
                    dto.setE_filepath(row.getString("e_filepath"));
                    dto.setE_filename(row.getString("e_filename"));
                    dto.setThumbnail(row.getString("thumbnail"));
                }else if(reqC.equals("ex")){
                    dto.setE_num(row.getInt("e_num"));
                    dto.setE_name(row.getString("e_name"));
                    dto.setE_content(row.getString("e_content"));
                    dto.setE_type(row.getString("e_type"));
                    dto.setE_calorie(row.getInt("e_calorie"));
                    dto.setE_point(row.getInt("e_point"));
                    dto.setE_filepath(row.getString("e_filepath"));
                    dto.setE_filename(row.getString("e_filename"));
                    dto.setThumbnail(row.getString("thumbnail"));
                }
                list.add(dto);
            }

        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        // 핸들러에게 메시지를 요청
        Log.d("main:", "readMessage: " + list.get(0).getE_filepath() );

        return list;

    }
}
