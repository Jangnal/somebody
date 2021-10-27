package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.explaylist;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myteamcproject.Common.CommonMethod;
import com.example.myteamcproject.Exercise.UserExerciseDTO;

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


public class ExInsertATask extends AsyncTask<Void, Void, String> {
    private static final String TAG = "ExInsertATask";

    String reqC;
    String id, e_name;

    public ExInsertATask(String reqC) {
        this.reqC = reqC;
    }

    public ExInsertATask(String reqC, String id, String e_name) {
        this.reqC = reqC;
        this.id = id;
        this.e_name = e_name;
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

            if (reqC.equals("exi")) {
                builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("e_name", e_name, ContentType.create("Multipart/related", "UTF-8"));

            }

            // 전송
            String postURL = "";
            if (reqC.equals("exi")) {
                postURL = ipConfig + "/androEXIlist.me";
            }

            Log.d(TAG, "doInBackground: " + postURL);
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("ExerciseI");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); // 응답
            body = EntityUtils.toString(httpResponse.getEntity());

            // 하나의 오브젝트 가져올때
            //explaylist = (ArrayList<UserExerciseDTO>) readMessage(body);

        } catch (IOException e) {
            Log.d(TAG, "doInBackground: " + e.getStackTrace() + ", msg : " + e.getMessage());

        } finally {
            if (httpEntity != null) {
                httpEntity = null;
            } 
            if (httpResponse != null) {
                httpResponse = null;
            }
            if (httpPost != null) {
                httpPost = null;
            }
            if (httpClient != null) {
                httpClient = null;
            }

        }

        return "com";
    }

    // doInBackground 끝난후에 오는부분
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d("main:", "onPostExecute: result => " + result);
    }

    private List<UserExerciseDTO> readMessage(String body) throws IOException, JSONException {
        List<UserExerciseDTO> list = new ArrayList<UserExerciseDTO>();
        try {
            JSONArray jArray = new JSONArray(body);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject row = jArray.getJSONObject(i);
                UserExerciseDTO dto = new UserExerciseDTO();
                if (reqC.equals("exi")) {
                    dto.setId(row.getString("u_numb"));
                    dto.setId(row.getString("id"));
                    dto.setE_name(row.getString("e_name"));
                    dto.setU_date(row.getString("u_date"));
                    dto.setE_count(row.getInt("e_count"));
                    dto.setU_calorie(row.getInt("u_calorie"));
                    dto.setU_point(row.getInt("u_point"));
                    dto.setU_complete(row.getString("u_complete"));
                    dto.setIsChecked(row.getString("isChecked"));
                }
                list.add(dto);
            }

        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        return list;

    }//readMessage
}




