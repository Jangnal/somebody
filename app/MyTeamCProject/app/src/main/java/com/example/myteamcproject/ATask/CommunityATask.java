package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.coDto;
import static com.example.myteamcproject.Common.CommonMethod.colist;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

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
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommunityATask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "CommunityATask:";
    int c_numb;
    String c_title, c_content, c_writer, search, mysearch;
    Date c_date;
    int c_readcount;
    String c_filename, c_filepath, c_category, c_secret;
    String reqC, imgpath;

    public CommunityATask() {
    }

    public CommunityATask(String reqC){
        this.reqC = reqC;
    }


    public CommunityATask(String reqC, int c_numb){
        this.reqC = reqC;
        this.c_numb = c_numb;
    }

    public CommunityATask(String reqC, String search){
        this.reqC = reqC;
        this.search = search;
    }

    public CommunityATask(String reqC, int c_numb, int c_readcount){
        this.reqC = reqC;
        this.c_numb = c_numb;
        this.c_readcount = c_readcount;
    }

    public CommunityATask(String reqC, String co_title_c, String co_content_c, String imgpath, String id) {
        this.reqC = reqC;
        this.c_title = co_title_c;
        this.c_content = co_content_c;
        this.imgpath = imgpath;
        this.c_writer = id;
    }

    public CommunityATask(int c_numb_c, String reqC, String co_title_c, String co_content_c, String imgpath, String id) {
        this.c_numb = c_numb_c;
        this.reqC = reqC;
        this.c_title = co_title_c;
        this.c_content = co_content_c;
        this.imgpath = imgpath;
        this.c_writer = id;
    }

    public CommunityATask(String reqC, String co_title_c, String co_content_c, String imageDbPathA, String id, String imageRealPathA) {
        this.reqC = reqC;
        this.c_title = co_title_c;
        this.c_content = co_content_c;
        this.imgpath = imageRealPathA;
        this.c_writer = id;

    }
    String state = "";
    //반드시 선언해야 할것들 : 무조건 해야함
    HttpClient httpClient;      // 클라이언트 객체
    HttpPost httpPost;          // 클라이언트에 붙일 본문
    HttpResponse httpResponse;  // 서버에서의 응답
    HttpEntity httpEntity;      // 응답 내용
    String body;



    // doInBackground 하기전에 설정 및 초기화
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            //MultipartEntityBuilder 생성 : 무조건 해야함
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            Log.d(TAG, "doInBackground: " + reqC);

            if (reqC.equals("insert")) {
                // 문자열 및 데이터 추가하기
                builder.addTextBody("title", c_title, ContentType.create("Multipart/related", "UTF-8"));

                builder.addTextBody("content", c_content, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("writer", c_writer, ContentType.create("Multipart/related", "UTF-8"));
                if (imgpath != null) {
                    builder.addPart("imgpath", new FileBody(new File(imgpath)));
                }//if
            } else if (reqC.equals("coview")) {
                builder.addTextBody("c_numb", String.valueOf(c_numb), ContentType.create("Multipart/related", "UTF-8"));
            }//if
            else if (reqC.equals("delete")) {
                builder.addTextBody("c_numb", String.valueOf(c_numb), ContentType.create("Multipart/related", "UTF-8"));
            }
            else if (reqC.equals("update")) {
                builder.addTextBody("c_numb", String.valueOf(c_numb), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("title", c_title, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("content", c_content, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("writer", c_writer, ContentType.create("Multipart/related", "UTF-8"));
                if (imgpath != null) {
                    builder.addPart("imgpath", new FileBody(new File(imgpath)));
                }
            } else if (reqC.equals("search") || reqC.equals("mysearch")) {
                builder.addTextBody("search", search, ContentType.create("Multipart/related", "UTF-8"));
            }


        //전송
            String postURL = "";
            if(reqC.equals("list")){
                postURL = ipConfig + "/community.co";
            }else if(reqC.equals("insert")){
                postURL = ipConfig + "/insert.co";
            }else if(reqC.equals("coview")){
                postURL = ipConfig + "/co_view.co";
            }else if(reqC.equals("delete")){
                postURL = ipConfig + "/delete.co";
            }else if(reqC.equals("update")){
                postURL = ipConfig + "/update.co";
            }else if(reqC.equals("search")){
                postURL = ipConfig + "/community.co";
            }else if(reqC.equals("mysearch")){
                postURL = ipConfig + "/mysearch.co";
            }

            Log.d(TAG, "doInBackground: " + postURL);

            httpClient = AndroidHttpClient.newInstance("Comm");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);    //응답
            body = EntityUtils.toString(httpResponse.getEntity());

        // 하나의 오브젝트 가져올때
            if(reqC.equals("list")){
                    colist = (List<CommunityDTO>) readMessage(body);
            } else if(reqC.equals("coview")){
                    colist = (List<CommunityDTO>) readObject(body);
            } else if(reqC.equals("delete")){
                    colist = (List<CommunityDTO>) readMessage(body);
            } else if(reqC.equals("search")) {
                    colist = (List<CommunityDTO>) readMessage(body);
            } else if(reqC.equals("mysearch")) {
                colist = (List<CommunityDTO>) readMessage(body);
            }

        }catch(Exception e){
            Log.d(TAG, "doInBackground: " + e.getStackTrace() + ", msg : " + e.getMessage());
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
        return body;
    }

    // doInBackground 끝난 후에 오는 부분
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("main:", "onPostExecute: result => " + result);
    }//onPostExecute

    public List<CommunityDTO> readMessage(String body) throws IOException {
        List<CommunityDTO> list = new ArrayList<CommunityDTO>();
        try {
            JSONObject jobject = new JSONObject(body);
            JSONArray jArray = jobject.getJSONArray("list");
            for(int i=0; i<jArray.length();i++) {
                JSONObject row = jArray.getJSONObject(i);
                CommunityDTO dto = new CommunityDTO();
                dto.setC_numb(Integer.parseInt(row.getString("c_numb")));
                dto.setC_title(row.getString("c_title"));
                dto.setC_content(row.getString("c_content"));
                dto.setC_writer(row.getString("c_writer"));
                dto.setC_date(row.getString("c_date"));
                dto.setC_readcount(Integer.parseInt(row.getString("c_readcount")));
                if(!row.isNull("c_filename")){
                    dto.setC_filename(row.getString("c_filename"));
                    dto.setC_filepath(row.getString("c_filepath"));
                }

                dto.setC_category(row.getString("c_category"));
                list.add(dto);
            }

        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        // 핸들러에게 메시지를 요청
        //Log.d("main:", "readMessage: " + list.get(0).getC_writer() );

        return list;

    }

    public CommunityDTO readObject(String body) throws IOException {
        coDto = new CommunityDTO();
        try {
            JSONObject JSONObject = new JSONObject(body);
            coDto.setC_numb(Integer.parseInt(JSONObject.getString("c_numb")));
            coDto.setC_title(JSONObject.getString("c_title"));
            coDto.setC_content(JSONObject.getString("c_content"));
            coDto.setC_writer(JSONObject.getString("c_writer"));
            coDto.setC_date(JSONObject.getString("c_date"));
            coDto.setC_readcount(Integer.parseInt(JSONObject.getString("c_readcount")));
            if(!JSONObject.isNull("c_filename")){
                coDto.setC_filename(JSONObject.getString("c_filename"));
                coDto.setC_filepath(JSONObject.getString("c_filepath"));
            }
            coDto.setC_category(JSONObject.getString("c_category"));

        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }
        // 핸들러에게 메시지를 요청
        Log.d("main:", "readMessage: " + coDto.getC_writer() );

        return coDto;

    }
}
