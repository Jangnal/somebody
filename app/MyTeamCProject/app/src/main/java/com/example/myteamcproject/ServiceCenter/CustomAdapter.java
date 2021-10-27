package com.example.myteamcproject.ServiceCenter;

import static com.example.myteamcproject.Common.CommonMethod.exlist;

import android.content.Context;
import android.os.Bundle;
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
import com.example.myteamcproject.Exercise.FragExStart;
import com.example.myteamcproject.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<UserDTO> arrayList;
    private Context context;
    private FragmentManager fragmentManager = null;

    public void addList(UserDTO dto){
        arrayList.add(dto);
    }

    public CustomAdapter(ArrayList<UserDTO> arrayList, Context context, FragmentManager fragmentManager) {
        this.arrayList = arrayList;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }//CustomAdapter

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatroom_view, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }//CustomViewHolder

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView).load(arrayList.get(position).getProfileImgUrl()).circleCrop()
        .into(holder.iv_profile);

        String id = arrayList.get(position).getName();
        holder.tv_id.setText(id);
        holder.tv_comment.setText(String.valueOf(arrayList.get(position).getMsg()));
        holder.tv_date.setText(arrayList.get(position).getDate());

        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(); //번들을 통해 값 전달

                bundle.putString("userid", id);
                FragMyChat FragMyChat = new FragMyChat();   //FragExStart 선언
                FragMyChat.setArguments(bundle);    //번들을 FragExStart 로 보낼 준비

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.main_frag, FragMyChat).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);

                transaction.commit();
            }
        });

    }//onBindViewHolder

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }//getItemCount

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_id, tv_comment, tv_date;
        LinearLayout line;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_id = itemView.findViewById(R.id.tv_id);
            this.tv_comment = itemView.findViewById(R.id.tv_comment);
            this.tv_date = itemView.findViewById(R.id.tv_date);
            this.line = itemView.findViewById(R.id.line);
        }//CustomViewHolder

    }//CustomViewHolder

}//CustomAdapter
