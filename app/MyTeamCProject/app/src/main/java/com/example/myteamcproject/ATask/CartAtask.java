package com.example.myteamcproject.ATask;

import static android.content.ContentValues.TAG;
import static com.example.myteamcproject.Common.CommonMethod.cartDto;
import static com.example.myteamcproject.Common.CommonMethod.cartlist;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myteamcproject.Gift.CartDTO;

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

public class CartAtask extends AsyncTask<Void, Void, String> {

    private static final String Tag = "GiftATask";

    String cart_title, id;
    String reqC;
    int point;

    ArrayList<CartDTO> dtos;

    public CartAtask(String reqC,  String id) {
        this.reqC = reqC;
        this.id = id;
    }

    public CartAtask(String reqC){
        this.reqC = reqC;

    }

    public CartAtask(String id, ArrayList<CartDTO> dtos){
        this.id = id;
        this.dtos = dtos;

    }


    //나중에 사용하기 위한 생성자
    public CartAtask(String reqC, String id, String cart_title, int point) {
        this.reqC = reqC;
        this.id = id;
        this.cart_title = cart_title;
        this.point = point;
    }

    String state = "";
    //반드시 선언해야함
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
        try{
            // MultipartEntityBuilder 생성 : 무조건 해야함
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            Log.d(TAG, "doInBackground: " + reqC);

            //전송 url 작성
            if (reqC.equals("insert")){
                //spring dao에서 설정해둔 값3가지를 추가해두자
                builder.addTextBody("cart_title", cart_title, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("point", String.valueOf(point), ContentType.create("Multipart/related", "UTF-8"));
            } else if (reqC.equals("cartlist")) {
               // builder.addTextBody("cart_title", cart_title, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
                //builder.addTextBody("point", String.valueOf(point), ContentType.create("Multipart/related", "UTF-8"));
            }

            String postURL = "";
            if(reqC.equals("insert")){
                postURL = ipConfig + "/giftcart.gf";
            }else if(reqC.equals("cartlist")){
                postURL = ipConfig + "/cartselect.gf";
            }

            Log.d(TAG, "doInBackground: " + postURL);


            //DB와 연동을 위한 http 객체 순서대로 실행
            httpClient = AndroidHttpClient.newInstance("cart");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);

            //DB에서 뽑아온 값을 문자열 형태로 body에 담는다.
            body = EntityUtils.toString(httpResponse.getEntity());

            //body에 담은 값을 readMessage 메소드로 보내 List<GiftDTO>로 변환한다
            if (reqC.equals("insert")){
                return body;
            }else if(reqC.equals("cartlist")) {
                cartlist = readMessage(body);
            }

        }catch (IOException e){
            Log.d(TAG, "doInBackground: " + e.getStackTrace() + ",msg : " + e.getMessage());

        }finally {
            if(httpEntity != null) {
                httpEntity = null;
            }
            if(httpResponse != null) {
                httpResponse = null;
            }
            if(httpPost != null) {
                httpPost = null;
            }
            if (httpClient != null) {
                httpClient = null;
            }
        }

        return body;
    }

    // doInBackground 끝난후에 오는부분
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d("main:", "onPostExecute: result => " + result);
    }

    public List<CartDTO> readMessage(String body) throws IOException {
        //값을 담기 위한 list 선언
        List<CartDTO> list = new ArrayList<CartDTO>();
        try {
            //body에 담은 값을 json형태로 변환
            //JSONObject jobject = new JSONObject(body);
            JSONArray jArray = new JSONArray(body);
            //for문을 돌려 값을 list<dto> 형태로 변환한다.
            for(int i=0; i<jArray.length();i++) {
                JSONObject row = jArray.getJSONObject(i);
                CartDTO dto = new CartDTO();
                dto.setCart_title(row.getString("cart_title"));
                dto.setId(row.getString("id"));
                dto.setPoint(Integer.parseInt(row.getString("point")));
                dto.setFilepath(row.getString("filepath"));
                dto.setFilename(row.getString("filename"));
                dto.setContent(row.getString("content"));
                list.add(dto);
            }
        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        // 핸들러에게 메시지를 요청
        Log.d("main:", "readMessage: " + list.get(0).getCart_title());

        //완성된 list를 반환한다.

        return list;

    }

    public CartDTO readObject(String body) throws IOException {
        cartDto = new CartDTO();
        try {
            JSONObject JSONObject = new JSONObject(body);
            cartDto.setCart_title(JSONObject.getString("cart_title"));
            cartDto.setId(JSONObject.getString("id"));
            cartDto.setPoint(Integer.parseInt(JSONObject.getString("point")));
            //coDto.setC_date(JSONObject.getString("c_date"));
            //  coDto.setC_filename(JSONObject.getString("c_filename"));
            //coDto.setC_filepath(JSONObject.getString("c_filepath"));
            // coDto.setC_category(JSONObject.getString("c_category"));

        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }
        // 핸들러에게 메시지를 요청
        Log.d("main:", "readMessage: " + cartDto.getId() );

        return cartDto;

    }


}
