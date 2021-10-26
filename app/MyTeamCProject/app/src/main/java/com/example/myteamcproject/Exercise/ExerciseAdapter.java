package com.example.myteamcproject.Exercise;

import static com.example.myteamcproject.Common.CommonMethod.exlist;
import static com.example.myteamcproject.Common.CommonMethod.explaylist;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExerciseAdapter extends
        RecyclerView.Adapter<ExerciseAdapter.ViewHolder>
        implements OnExerciseItemClickListener{
    private static final String TAG = "ExerciseAdapter";

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    // 3.listener 선언(메인)
    OnExerciseItemClickListener listener;

    // 메인에서 넘겨 받는것
    ArrayList<ExerciseDTO> dtos;
    Context context;
    LayoutInflater inflater;
    FragmentManager fragmentManager;

    View tv_ex2;

    // 생성자
    public ExerciseAdapter(ArrayList<ExerciseDTO> dtos, Context context, FragmentManager fragmentManager) {
        this.dtos = dtos;
        this.context = context;
        this.fragmentManager = fragmentManager;

        inflater = LayoutInflater.from(this.context);
    }

    // 1.화면을 인플레이트 시킨다. clickListener 를 달고 들어간다.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View itemView = inflater.inflate(R.layout.exercise_view,
                parent, false);

        return new ViewHolder(itemView, this);
    }

    // 인플레이트 시킨 화면에 데이터를 셋팅시킨다.
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        holder.tv_ex2.setVisibility(View.GONE);

        ExerciseDTO dto = dtos.get(position);
        // viewHolder에 만들어 놓은 setDto에 선택된 dto를 넘긴다.
        holder.setDto(dto);

        //항목를 클릭시 이미지뷰 펼치기.
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.tv_ex2.getVisibility() == View.VISIBLE){
                    holder.tv_ex2.setVisibility(View.GONE);
                }else {
                    holder.tv_ex2.setVisibility(View.VISIBLE);
                    holder.tv_ex2.setText(dtos.get(position).getE_content());
                }
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
    public void setOnItemClickListener
    (OnExerciseItemClickListener listener){
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
        TextView tv_name;
        TextView tv_content;
        TextView tv_ex2;
        ImageView img_ex;
        LinearLayout parentLayout;
        CheckBox chkEx;

        // exercise_view 에 정의한 아이디와 연결시킨다 (생성자)
        // 아래에 itemView 가 하나의 exercise view 라고 생각
        public ViewHolder(@NonNull View itemView, OnExerciseItemClickListener listener) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_content = itemView.findViewById(R.id.tv_content);
            img_ex = itemView.findViewById(R.id.img_ex);
            tv_ex2 = itemView.findViewById(R.id.tv_ex2);
            chkEx=itemView.findViewById(R.id.chkEx);

            explaylist = new ArrayList<>();
            if(loginDTO != null){
                chkEx.setVisibility(View.VISIBLE);
            }else {
                chkEx.setVisibility(View.GONE);
            }
            chkEx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try{
                    if (isChecked){
                        chkEx.setText("체크됨");
                        explaylist.add(new UserExerciseDTO(getAdapterPosition(), loginDTO.getId(), tv_name.getText().toString(), getTime().toString()
                        , 0, 0, 0, "N", "Y"));
                    }
                    }catch (IndexOutOfBoundsException e){
                        if(explaylist.size() > 0){
                            explaylist.remove(getAdapterPosition());
                            chkEx.setText("");
                        }else{
                            explaylist.clear();
                        }//if
                }//try&catch
            }//onCheckedChanged
     });//setOnCheckedChangeListener

            // 2.화면에 clickListener 를 달아준다.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this,
                                view, position);
                    }

                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;

                }//onclick
            });//setOnClickListener
        }//ViewHolder

            // exercise_view 에 데이터를 셋팅시키는 함수를 만든다
            public void setDto(ExerciseDTO dto){
                tv_name.setText(dto.getE_name());
                tv_content.setText(dto.getE_content());
                String filepath = ipConfig + "/resources/"  + dto.getE_filepath() + dto.getThumbnail();
                Log.d(TAG, "setDto: " + filepath);
                Glide.with(itemView).load(filepath).into(img_ex);
                tv_ex2.setText(dto.getE_content());
        }//setDto
    }//ViewHolder

    private Date getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mDate;
    }//getTime

    /**
     * 클릭된 Item의 상태 변경
     * @param isExpanded Item을 펼칠 것인지 여부
     */
    private void changeVisibility(final boolean isExpanded) {
        // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
        int dpValue = 150;
        float d = context.getResources().getDisplayMetrics().density;
        int height = (int) (dpValue * d);

        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(600);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // value는 height 값
                int value = (int) animation.getAnimatedValue();
                // tv_ex2 높이 변경
                tv_ex2.getLayoutParams().height = value;
                tv_ex2.requestLayout();
                // tv_ex2 가 실제로 사라지게하는 부분
                tv_ex2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }//onAnimationUpdate
        });
        // Animation start
        va.start();
    }//changeVisibility

}
