package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.foodDTO;
import static com.example.myteamcproject.Common.CommonMethod.foodlist;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myteamcproject.Mypage.FoodDTO;

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
import java.util.List;

public class FoodATask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "main:FoodATask:";

    int numb;
    String id, writedate, morning, lunch, dinner, content
            , m_filename, m_filepath, l_filename, l_filepath
            ,d_filename, d_filepath, reqC, imgpath1, imgpath2, imgpath3;
    FoodDTO dto;

    public FoodATask(String reqC, String id){
        this.reqC = reqC;
        this.id = id;
    }

    public FoodATask(String reqC, String id, String writedate){
        this.reqC = reqC;
        this.id = id;
        this.writedate = writedate;
    }

    public FoodATask(String reqC, String writedate, String id, String mornig, String lunch, String dinner, String content, String imgpath1, String imgpath2, String imgpath3) {
        this.reqC = reqC;
        this.writedate = writedate;
        this.id = id;
        this.morning = mornig;
        this.lunch = lunch;
        this.dinner = dinner;
        this.content = content;
        this.m_filepath = imgpath1;
        this.l_filepath = imgpath2;
        this.d_filepath = imgpath3;
    }


    public FoodATask(String reqC, String id, FoodDTO dto) {
        this.reqC = reqC;
        this.id = id;
        this.dto = dto;
    }


    // ????????? ???????????? ????????? : ????????? ????????? ???,???
    HttpClient httpClient;  // ??????????????? ??????
    HttpPost httpPost;      // ?????????????????? ?????? ??????
    HttpResponse httpResponse; // ??????????????? ??????
    HttpEntity httpEntity;  // ?????? ??????

    //?????? ?????? ?????? ??????
    String body;


    // doInBackground ???????????? ?????? ??? ?????????
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(Void... voids) {

        try {
            // MultipartEntityBuilder ?????? : ????????? ?????????
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            if(reqC.equals("mlist")){
                builder.addTextBody("writedate", writedate, ContentType.create("Multipart/related", "UTF-8"));
            }else if(reqC.equals("foodMinsert")){
                builder.addTextBody("morning", dto.getMorning(), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("lunch", dto.getLunch(), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("dinner", dto.getDinner(), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("content", dto.getContent(), ContentType.create("Multipart/related", "UTF-8"));
                if(!dto.getImgrealpath1().isEmpty()){
                    builder.addPart("m_filepath", new FileBody(new File(dto.getImgrealpath1())));
                }
                if(!dto.getImgrealpath1().isEmpty()){
                    builder.addPart("l_filepath", new FileBody(new File(dto.getImgrealpath2())));
                }
                if (!dto.getImgrealpath1().isEmpty()) {
                    builder.addPart("d_filepath", new FileBody(new File(dto.getImgrealpath3())));
                }
            }else if(reqC.equals("foodMupdate")){
                builder.addTextBody("morning", morning, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("lunch", lunch, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("dinner", dinner, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("content", content, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("writedate", writedate, ContentType.create("Multipart/related", "UTF-8"));
            }



            // ?????? url ??????
            String postURL = "";
            if(reqC.equals("mlist")){
                postURL = ipConfig + "/foodmlist.my";
            }else if(reqC.equals("foodMinsert")){
                postURL = ipConfig + "/foodminsert.my";
            }else if(reqC.equals("mAlllist")){
                postURL = ipConfig + "/foodmAlllist.my";
            }else if(reqC.equals("foodMupdate")){
                postURL = ipConfig + "/foodMupdate.my";
            }//if

            //DB??? ????????? ?????? http ?????? ???????????? ??????
            httpClient = AndroidHttpClient.newInstance("Food");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);

            //DB?????? ????????? ?????? ????????? ????????? body??? ?????? ???.
            body = EntityUtils.toString(httpResponse.getEntity());

            //body??? ?????? ?????? readMessage ???????????? ?????? List<ExerciseDTO>??? ????????????
            if(!body.equals("")){
                if(reqC.equals("mlist")){
                    foodDTO = makedto(body);
                }else if(reqC.equals("mAlllist")){
                    foodlist = readMessage(body);
                }else if(reqC.equals("foodMupdate")){
                    foodDTO = makedto(body);
                }
            }//if


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

        return body;
    }

    // doInBackground ?????? ?????? ?????? ??????
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("main:", "onPostExecute: result => " + result);
    }//onPostExecute

    public FoodDTO makedto(String body) throws IOException {
        FoodDTO dto = null;
        try {
            JSONObject row = new JSONObject(body);
            dto = new FoodDTO();
            dto.setNumb(Integer.parseInt(row.getString("numb")));
            dto.setId(row.getString("id"));
            dto.setWritedate(row.getString("writedate"));
            dto.setMorning(row.getString("morning"));
            dto.setLunch(row.getString("lunch"));
            dto.setDinner(row.getString("dinner"));
            dto.setContent(row.getString("content"));
            dto.setM_filename(row.getString("m_filename"));
            dto.setM_filepath(row.getString("m_filepath"));
            dto.setL_filename(row.getString("l_filename"));
            dto.setL_filepath(row.getString("l_filepath"));
            dto.setD_filename(row.getString("d_filename"));
            dto.setD_filepath(row.getString("d_filepath"));
        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        // ??????????????? ???????????? ??????
//        Log.d("main:", "readMessage: " + dto.getId() );

        return dto;

    }

    public List<FoodDTO> readMessage(String body) throws IOException {
        //?????? ?????? ?????? list ??????
        List<FoodDTO> list = new ArrayList<FoodDTO>();
        try {
            //body??? ?????? ?????? json????????? ??????
            JSONArray jArray = new JSONArray(body);

            //for?????? ?????? ?????? list<dto> ????????? ????????????.
            for(int i=0; i<jArray.length();i++) {
                JSONObject row = jArray.getJSONObject(i);
                FoodDTO dto = new FoodDTO();
                dto.setNumb(Integer.parseInt(row.getString("numb")));
                dto.setId(row.getString("id"));
                dto.setWritedate(row.getString("writedate"));
                dto.setMorning(row.getString("morning"));
                dto.setLunch(row.getString("lunch"));
                dto.setDinner(row.getString("dinner"));
                dto.setContent(row.getString("content"));
                dto.setM_filename(row.getString("m_filename"));
                dto.setM_filepath(row.getString("m_filepath"));
                dto.setL_filename(row.getString("l_filename"));
                dto.setL_filepath(row.getString("l_filepath"));
                dto.setD_filename(row.getString("d_filename"));
                dto.setD_filepath(row.getString("d_filepath"));
                list.add(dto);
            }

            Log.d(TAG, "readMessage: listsize => " + list.size());

        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        // ??????????????? ???????????? ??????
        Log.d("main:", "readMessage: " + list.get(0).getId());

        //????????? list??? ????????????.
        return list;

    }

}
