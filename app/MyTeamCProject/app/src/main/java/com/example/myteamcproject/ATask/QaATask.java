package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.qalist;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myteamcproject.Community.CommunityDTO;

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

public class QaATask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "QaATask";

    int reqC;

    // 반드시 선언해야 할것들 : 무조건 해야함 복,붙
    HttpClient httpClient;  // 클라이언트 객체
    HttpPost httpPost;      // 클라이언트에 붙일 본문
    HttpResponse httpResponse; // 서버에서의 응답
    HttpEntity httpEntity;  // 응답 내용
    String body;

    public QaATask(){

    }

    public QaATask(int reqcode){
        this.reqC = reqcode;
    }//QaATask

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

            builder.addTextBody("category", "oto", ContentType.create("Multipart/related", "UTF-8"));


            // 전송
            String postURL = ipConfig + "/qalist.sc";

            Log.d(TAG, "doInBackground: " + postURL);
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Q&A");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); // 응답
            body = EntityUtils.toString(httpResponse.getEntity());

            // 하나의 오브젝트 가져올때
            qalist = (List<CommunityDTO>) readMessage(body);

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

    public List<CommunityDTO> readMessage(String body) throws IOException {
        List<CommunityDTO> list = new ArrayList<CommunityDTO>();
        try {
            JSONArray jArray = new JSONArray(body);
            for(int i=0; i<jArray.length();i++) {
                JSONObject row = jArray.getJSONObject(i);
                CommunityDTO dto = new CommunityDTO();
                dto.setC_numb(row.getInt("c_numb"));
                dto.setC_title(row.getString("c_title"));
                dto.setC_content(row.getString("c_content"));
                dto.setC_writer(row.getString("c_writer"));
                dto.setC_date(row.getString("c_date"));
                dto.setC_readcount(row.getInt("c_readcount"));
                dto.setC_filename(row.getString("c_filename"));
                dto.setC_filepath(row.getString("c_filepath"));
                dto.setC_category(row.getString("c_category"));
                dto.setC_secret(row.getString("c_secret"));
                list.add(dto);
            }

        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        // 핸들러에게 메시지를 요청
        Log.d("main:", "readMessage: " + list.get(0).getC_title() );

        return list;

    }

}
