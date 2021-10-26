package com.example.myteamcproject.Mypage;


import static com.example.myteamcproject.Common.CommonMethod.foodDTO;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.ATask.FoodATask;
import com.example.myteamcproject.R;

import java.util.concurrent.ExecutionException;

public class FragMfooddate extends Fragment {

    private static final String TAG = "FragMfooddate";

    private Button backbtn, changbtn;

    int year;
    int month;
    int day;
    String writedate;
    private View view;

    public FragMfooddate(int year, int month, int day) {
        this.year = year;
        this.month = month + 1;
        this.day = day;

        writedate = year + "/" + (month + 1) + "/" + day;
    }

    ImageView mornig_img, lunch_img, dinner_img;
    TextView mornig_text, lunch_text, dinner_text, todate_text;

    public FragMfooddate(String writedate) {
        this.writedate = writedate;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mfood_date, container, false);

        foodDTO = null;

        FoodATask foodATask = new FoodATask("mlist", loginDTO.getId(), writedate);

        try {
            foodATask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        mornig_img = view.findViewById(R.id.mornig_img);
        lunch_img = view.findViewById(R.id.lunch_img);
        dinner_img = view.findViewById(R.id.dinner_img);
        mornig_text = view.findViewById(R.id.mornig_text);
        lunch_text = view.findViewById(R.id.lunch_text);
        dinner_text = view.findViewById(R.id.dinner_text);
        todate_text = view.findViewById(R.id.todate_text);

        if(foodDTO != null){
        mornig_text.setText(foodDTO.getMorning());
        lunch_text.setText(foodDTO.getLunch());
        dinner_text.setText(foodDTO.getDinner());
        todate_text.setText(year + "년 " + month + "월 " + day + "일");




        }else{
            todate_text.setText(year + "년 " + month + "월 " + day + "일");
            Toast.makeText(getContext(), "아직 작성되지 않았습니다", Toast.LENGTH_SHORT).show();
        }//if

        backbtn= view.findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragMfood fragMfood = new FragMfood();
                transaction.replace(R.id.main_frag, fragMfood);
                transaction.commit();
            }
        });

        changbtn= view.findViewById(R.id.changbtn);

        changbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FrageMyfoodupdate frageMyfoodupdate = new FrageMyfoodupdate();
                Bundle bundle = new Bundle();
                bundle.putString("writedate", year + "/ " + month + "/" + day);
                frageMyfoodupdate.setArguments(bundle);
                transaction.replace(R.id.main_frag, frageMyfoodupdate);
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String filepath = ipConfig + "/resources/";
        Log.d(TAG, "setDto: " + filepath);

        if(foodDTO != null){
            if(foodDTO.getM_filepath() != null && !foodDTO.getM_filepath().trim().equals("") ){
                Glide.with(view).load(filepath + foodDTO.getM_filepath()).into(mornig_img);
            }//
            if(foodDTO.getL_filepath() != null && !foodDTO.getL_filepath().trim().equals("") ){
                Glide.with(view).load(filepath + foodDTO.getL_filepath()).into(lunch_img);
            }//
            if(foodDTO.getD_filepath() != null && !foodDTO.getD_filepath().trim().equals("") ){
                Glide.with(view).load(filepath + foodDTO.getD_filepath()).into(dinner_img);
            }//
        }else{
            mornig_img.setImageResource(R.drawable.health);
            lunch_img.setImageResource(R.drawable.health);
            dinner_img.setImageResource(R.drawable.health);
        }//if
    }//Resume

}
