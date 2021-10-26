package com.example.myteamcproject.Gift;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myteamcproject.R;

import java.util.ArrayList;

public class MainGift extends AppCompatActivity {
    private static final String TAG = "main:MainGift";

    GridView gridgift;
    GiftAdapter adapter;

    //메인에서 넘겨줄것
    ArrayList<GiftDTO> dtos;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.frag_gift);

        //디마이스 사이즈 구하기
        Point size = getDeviceSize();
        Log.d(TAG, "onCreate: sizeX => " + size.x);
        Log.d(TAG, "onCreate: sizey => " + size.y);

        dtos = new ArrayList<>();
        gridgift = findViewById(R.id.gridgift);

        //어댑터 객체 생성
        //for(int i = 0; i <list.size(); i++) {
         //   adapter.addDto(list.get(i));

           // adapter = new SingerAdapter(MainActivity.this, dtos);1
            //adapter.addDto(new SingerDTO("블랙핑크", "010-1111-1111",
                 //   25, R.drawable.singer1));

         //만든 어탭터를 리스트뷰에 붙인다.
            gridgift.setAdapter(adapter);



        }


    private Point getDeviceSize(){
        // Activity 에서 사용
        Display display = getWindowManager().getDefaultDisplay();
        // Fragment 에서 사용

        Point size = new Point();
        display.getRealSize(size);
        int width = size.x;
        int height = size.y;

        return size;
    }

}
