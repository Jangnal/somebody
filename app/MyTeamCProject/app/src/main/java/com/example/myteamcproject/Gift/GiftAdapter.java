package com.example.myteamcproject.Gift;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.R;

import java.util.ArrayList;

public class GiftAdapter extends BaseAdapter {  //알트엔터로 임플리트 상속 시켜주자
    private static final String TAG = "main:GiftAdapter";
    //메인에서 보내주는것
    Context context;
    ArrayList<GiftDTO> dtos;

    //화면을 붙이기 위한 객체 생성
    LayoutInflater inflater;

    View view;


    public GiftAdapter(Context context, ArrayList<GiftDTO> dtos) {
        this.context = context;
        this.dtos = dtos;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //알트엔터로 케스트


    }
    //하나의 dto 추가하기
    public void addDto(GiftDTO dto) {dtos.add(dto);}


    //ArrayList에 저장된 dto 갯수
    @Override
    public int getCount() {
        return dtos.size();
    }


    //ArrayList 특정위치에 있는 dto 가져오기
    @Override
    public GiftDTO getItem(int position) {
        return dtos.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    //화면 생성
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Log.d(TAG, "getView: " + position);

        GiftViewHolder viewHolder;

        //캐시된 뷰가 없을 시 새로 뷰 생성
        if (view == null) {
            //화면을 생성해서 붙인다.
            view = inflater.inflate(R.layout.giftview, viewGroup, false);

            viewHolder = new GiftViewHolder();
            viewHolder.giftName = view.findViewById(R.id.giftName);
            viewHolder.giftPoint = view.findViewById(R.id.giftPoint);
            viewHolder.gfImage = view.findViewById(R.id.gfImage);

            view.setTag(viewHolder);
        } else {
            viewHolder = (GiftViewHolder) view.getTag();
        }

        //선택된 dto 데이터 가져오기
        GiftDTO dto = dtos.get(position);
        String  gs_name= dto.getGs_name();
        int point = dto.getPonint();

        //화면에 데이터 연결하기
        viewHolder.giftName.setText(gs_name);
        viewHolder.giftPoint.setText(String.valueOf(point));
        ImageView gfimg = viewHolder.gfImage;
        String filepath = ipConfig + "/resources/"  + dto.getGs_filepath() + dto.getGs_filename();
        Log.d(TAG, "setDto: " + filepath);
        gfimg.setImageResource(R.drawable.ic_home);
        Glide.with(view).load(filepath).into(gfimg);
        return view;
    }

        //giftview에 있는 요소를 모두 정의
        public class GiftViewHolder {
            public ImageView gfImage;
            public TextView giftName, giftPoint;


        }

    }