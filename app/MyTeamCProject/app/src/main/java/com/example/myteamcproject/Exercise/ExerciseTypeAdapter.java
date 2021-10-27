package com.example.myteamcproject.Exercise;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.R;

import java.util.ArrayList;

public class ExerciseTypeAdapter extends RecyclerView.Adapter<ExerciseTypeAdapter.ViewHolder>
        implements OnExerciseTypeItemClickListener{

    private static final String TAG = "ExerciseTypeAdapter";

    // 3.listener 선언(메인)
    OnExerciseTypeItemClickListener listener;

    // 메인에서 넘겨 받는것
    ArrayList<ExerciseDTO> dtos;
    Context context;
    LayoutInflater inflater;
    FragmentManager fragmentManager;

    // 생성자
    public ExerciseTypeAdapter(ArrayList<ExerciseDTO> dtos, Context context, FragmentManager fragmentManager) {
        this.dtos = dtos;
        this.context = context;
        this.fragmentManager = fragmentManager;

        inflater = LayoutInflater.from(this.context);
    }

    // 1.화면을 인플레이트 시킨다. clickListener 를 달고 들어간다.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.exercise_type_view,
                parent, false);

        return new ViewHolder(itemView, this);
    }

    // 인플레이트 시킨 화면에 데이터를 셋팅시킨다.
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);

        ExerciseDTO dto = dtos.get(position);
        // viewHolder에 만들어 놓은 setDto에 선택된 dto를 넘긴다.
        holder.setDto(dto);

        //이미지를 클릭하면 운동상세페이지 띄우기.
        holder.img_ext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   //FragEx로 이동
                ExerciseDTO dto = getItem(position);

                Bundle bundle = new Bundle(); //번들을 통해 값 전달
                String e_type = dto.getE_type();
                bundle.putString("e_type", e_type);   //번들에 넘길 값 저장
                FragEx fragEx = new FragEx(fragmentManager);   //FragEx 선언
                fragEx.setArguments(bundle);    //번들을 FragEx로 보낼 준비


                FragmentTransaction transaction = fragmentManager.beginTransaction();


                transaction.replace(R.id.main_frag, fragEx).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

               }

        });

        //텍스트를 클릭하면 운동상세페이지 띄우기
        holder.tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   //FragEx로 이동
                ExerciseDTO dto = getItem(position);

                Bundle bundle = new Bundle(); //번들을 통해 값 전달
                String e_type = dto.getE_type();
                //String e_filepath = "";
                bundle.putString("e_type", e_type);   //번들에 넘길 값 저장
                //bundle.putString("운동사진", e_filepath);
                FragEx fragEx = new FragEx();   //FragEx 선언
                fragEx.setArguments(bundle);    //번들을 FragEx로 보낼 준비


                FragmentTransaction transaction = fragmentManager.beginTransaction();


                transaction.replace(R.id.main_frag, fragEx).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

            }

        });

    }//onBindViewHolder

    @Override
    public int getItemCount() {

        return dtos.size();
    }

    ////// arrayList<ExerciseDTO> 에 추가 매소드 //////////////
    public void addDto(ExerciseDTO dto){
        dtos.add(dto);
    }

    // 7.메인에서 요구한 dto를 넘겨준다.
    public ExerciseDTO getItem(int position){
        return dtos.get(position);
    }

    // 4.메인에서 클릭했을때 어댑터에 선언한 3번에서 선언한 listener와 연결
    public void setOnItemClickListener(OnExerciseTypeItemClickListener listener){
        this.listener = listener;
    }

    //인터페이스에서 정의된 메소드 구현
    // 5.ViewHolder의 clickListener를 실행해 클릭한 위치를 메인에게 알려준다.
    @Override
    public void onItemClick(ViewHolder holderm, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holderm, view, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        // exercise_view 에 있는 모든 위젯을 정의한다.
        TextView tv_type;
        ImageView img_ext;
        LinearLayout parentLayout;

        // exercise_view 에 정의한 아이디와 연결시킨다 (생성자)
        // 아래에 itemView 가 하나의 exercise_view 라고 생각
        public ViewHolder(@NonNull View itemView, OnExerciseTypeItemClickListener listener) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            tv_type = itemView.findViewById(R.id.tv_type);
            img_ext = itemView.findViewById(R.id.img_ext);

            // 2.화면에 clickListener 를 달아준다.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,
                                view, position);
                    }
                }//onClick
            });

            img_ext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,
                                view, position);
                    }

                }
            });

            tv_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,
                                view, position);
                    }

                }
            });

        }
        // exercise_view 에 데이터를 셋팅시키는 함수를 만든다
        public void setDto(ExerciseDTO dto){
            tv_type.setText(dto.getE_type());
            String filepath = ipConfig + "/resources/"  + dto.getE_filepath() + dto.getThumbnail();
            Log.d(TAG, "setDto: " + filepath);
            Glide.with(itemView).load(filepath).into(img_ext);
        }

    }
}