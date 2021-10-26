package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.gflist;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myteamcproject.Gift.GiftDTO;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Gift_userATask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "GiftATask";

    int g_numb, g_point;
    String id, g_title;
    String reqC;
    int gs_numb;

    //나중에 사용하기 위한 생성자
    public Gift_userATask(String reqC, String g_title) {
        this.reqC = reqC;
        this.g_title = g_title;
    }

    //반드시 선언해야 할것들
    HttpClient httpClient;  // 클라이언트 객체
    HttpPost httpPost;      // 클라이언트에 붙일 본문
    HttpResponse httpResponse; // 서버에서의 응답
    HttpEntity httpEntity;  // 응답 내용

    //값을 담기 위한 변수
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

            // 전송 url 작성
            String postURL = "";
            if(reqC.equals("buy")){

                builder.addTextBody("g_title", g_title, ContentType.create("Multipart/related", "UTF-8"));
                postURL = ipConfig + "/giftbuy.gf";
            }

            Log.d(TAG, "doInBackground: " + postURL);

            //DB와 연동을 위한 http 객체 순서대로 실행
            httpClient = AndroidHttpClient.newInstance("gift_user");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);

            //DB에서 뽑아온 값을 문자열 형태로 body에 담는 다.
            body = EntityUtils.toString(httpResponse.getEntity());

            //body에 담은 값을 readMessage 메소드로 보내 List<GiftDTO>로 변환한다
            if(reqC.equals("buy")){
                postURL = ipConfig + "/giftbuy.gf";
            }else{
                gflist = (List<GiftDTO>) readMessage(body);
            }

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

        return null;
    }

    // doInBackground 끝난후에 오는부분
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d("main:", "onPostExecute: result => " + result);
    }

    public List<GiftDTO> readMessage(String body) throws IOException {
        //값을 담기 위한 list 선언
        List<GiftDTO> list = new ArrayList<GiftDTO>();

        try {
            //body에 담은 값을 json형태로 변환
            JSONArray jArray = new JSONArray(body);

            //for문을 돌려 값을 list<dto> 형태로 변환한다.
            for(int i=0; i<jArray.length();i++) {
                JSONObject row = jArray.getJSONObject(i);
                GiftDTO dto = new GiftDTO();
                dto.setGs_filename(row.getString("gs_filename"));
                dto.setGs_filepath(row.getString("gs_filepath"));
                dto.setGs_name(row.getString("gs_name"));
                dto.setPonint(Integer.parseInt(row.getString("point")));
                list.add(dto);
            }
        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        // 핸들러에게 메시지를 요청
        Log.d("main:", "readMessage: " + list.get(0).getGs_name() );

        //완성된 list를 반환한다.
        return list;

    }
}
