package com.example.myteamcproject.Exercise;

import static com.example.myteamcproject.Common.CommonMethod.exlist;
import static com.example.myteamcproject.Common.CommonMethod.explaylist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.R;

import java.util.ArrayList;

public class ExerciseManageAdapter2 extends
        RecyclerView.Adapter<ExerciseManageAdapter2.ViewHolder>
        implements OnExerciseManageItemClickListener2 {
    private static final String TAG = "ExerciseManageAdapter2";

    // 3.listener 선언(메인)
    OnExerciseManageItemClickListener2 listener;

    // 메인에서 넘겨 받는것
    ArrayList<UserExerciseDTO> dtos;
    ArrayList<ExerciseDTO> dtos1;
    Context context;
    LayoutInflater inflater;
    FragmentManager fragmentManager;

    // 생성자
    public ExerciseManageAdapter2(ArrayList<UserExerciseDTO> dtos, Context context, FragmentManager fragmentManager) {
        this.dtos = dtos;
        this.context = context;
        this.fragmentManager = fragmentManager;

        inflater = LayoutInflater.from(this.context);
    }
    public ExerciseManageAdapter2(ArrayList<UserExerciseDTO> dtos, ArrayList<ExerciseDTO> dtos1, Context context, FragmentManager fragmentManager) {
        this.dtos = dtos;
        this.dtos1 = dtos1;
        this.context = context;
        this.fragmentManager = fragmentManager;

        inflater = LayoutInflater.from(this.context);
    }

    // 1.화면을 인플레이트 시킨다. clickListener 를 달고 들어간다.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.exercise_manage_view,
                parent, false);

        return new ViewHolder(itemView, this);
    }

    // 인플레이트 시킨 화면에 데이터를 셋팅시킨다.
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);

        UserExerciseDTO dto = dtos.get(position);
        // viewHolder 에 만들어 놓은 setDto 에 선택된 dto 를 넘긴다.
        holder.setDto(dto);

        //텍스트를 클릭하면 운동페이지 띄우기.
        holder.tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explaylist.clear();
                explaylist.addAll(dtos);

                Bundle bundle = new Bundle(); //번들을 통해 값 전달

                bundle.putInt("pos", position);
                bundle.putSerializable("dto", exlist.get(position));
                FragExStart fragExStart = new FragExStart();   //FragExStart 선언
                fragExStart.setArguments(bundle);    //번들을 FragExStart 로 보낼 준비

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.main_frag, fragExStart).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

            }

        });

        //텍스트를 클릭하면 운동페이지 띄우기
        holder.tv_name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explaylist.clear();
                explaylist.addAll(dtos);

                Bundle bundle = new Bundle(); //번들을 통해 값 전달

                bundle.putInt("pos", position);
                bundle.putSerializable("dto", exlist.get(position));
                FragExStart fragExStart = new FragExStart();   //FragExStart 선언
                fragExStart.setArguments(bundle);    //번들을 FragExStart 로 보낼 준비

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.main_frag, fragExStart).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

            }
        });

    }//OnBindViewHolder

    @Override
    public int getItemCount() {
        return dtos.size();
    }

    ////// arrayList<UserExerciseDTO> 에 추가 매소드 //////////////
    public void addDto(UserExerciseDTO dto){
        dtos.add(dto);
    }

    // 7.메인에서 요구한 dto를 넘겨준다.
    public UserExerciseDTO getItem(int position){
        return dtos.get(position);
    }

    // 4.메인에서 클릭했을때 어댑터에 선언한 3번에서 선언한 listener와 연결
    public void setOnItemClickListener
    (OnExerciseManageItemClickListener2 listener){
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
        // exercise_manage_view 에 있는 모든 위젯을 정의한다.
        TextView tv_date;
        TextView tv_name1;
        LinearLayout parentLayout;

        // exercise_manage_view 에 정의한 아이디와 연결시킨다 (생성자)
        // 아래에 itemView 가 하나의 exercise_manage_view 라고 생각
        public ViewHolder(@NonNull View itemView, OnExerciseManageItemClickListener2 listener) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_name1 = itemView.findViewById(R.id.tv_name1);

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

            tv_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,
                                view, position);
                    }

                }
            });

            tv_name1.setOnClickListener(new View.OnClickListener() {
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

        public void setDto(UserExerciseDTO dto) {
            tv_name1.setText(dto.getE_name());
            tv_date.setText(dto.getU_date());
        }

    }//ViewHolder
}