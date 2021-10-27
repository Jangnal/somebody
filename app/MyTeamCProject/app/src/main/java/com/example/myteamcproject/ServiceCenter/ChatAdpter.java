package com.example.myteamcproject.ServiceCenter;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.R;

import java.util.List;

public class ChatAdpter extends RecyclerView.Adapter<ChatAdpter.MyViewHolder> {
    private List<UserDTO> mDataset;
    private String myNickName;
    private Context context;


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_nickName , txt_msg , txt_date;
        public TextView  txt_msg2 , txt_date2;
        public LinearLayout ln1 ;
        public ImageView down_arrow , down_arrow2, destprofile, myprofile;
        public View rootView;

        public MyViewHolder(View v){
            super(v);

            txt_msg = v.findViewById(R.id.txt_msg);
            txt_nickName = v.findViewById(R.id.txt_nickname);
            txt_date = v.findViewById(R.id.txt_date);
            ln1 = v.findViewById(R.id.ln1);
            txt_msg2 = v.findViewById(R.id.txt_msg2);
            txt_date2 = v.findViewById(R.id.txt_date2);
            down_arrow = v.findViewById(R.id.down_arrow);
            down_arrow2 = v.findViewById(R.id.down_arrow2);
            destprofile= v.findViewById(R.id.destProfile);
            myprofile = v.findViewById(R.id.myProfile);
            v.setClickable(true);
            v.setEnabled(true);
        }
    }
    public ChatAdpter(List<UserDTO> myDataset , Context context , String myNickName){
        mDataset = myDataset;
        this.myNickName = myNickName;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat,parent , false);

        MyViewHolder vh = new MyViewHolder(v);
        return  vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserDTO dto = mDataset.get(position);

        holder.txt_nickName.setText(dto.getName());
        holder.txt_msg.setText(dto.getMsg());
        holder.txt_date.setText(dto.getDate());
        holder.txt_msg2.setText(dto.getMsg());
        holder.txt_date2.setText(dto.getDate());
        if(dto.getProfileImgUrl() != null) {
            Glide.with(context).load( dto.getProfileImgUrl()).circleCrop()
                    .into(holder.myprofile);
            Glide.with(context).load( dto.getProfileImgUrl() ).circleCrop()
                    .into(holder.destprofile);
        }

        if(dto.getName().equals(this.myNickName)){
            holder.txt_msg.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            holder.txt_nickName.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            holder.txt_date.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            LinearLayout ln11 =  (LinearLayout) holder.ln1.getParent();
            LinearLayout ln12 =  (LinearLayout) holder.ln1;
            ln11.setGravity(Gravity.RIGHT);
            holder.txt_msg.setBackgroundColor(Color.WHITE);

        }else{
            holder.txt_msg.setVisibility(View.GONE);
            holder.txt_date.setVisibility(View.GONE);
            holder.down_arrow.setVisibility(View.GONE);
            holder.destprofile.setVisibility(View.GONE);
            holder.txt_msg2.setVisibility(View.VISIBLE);
            holder.txt_date2.setVisibility(View.VISIBLE);
            holder.down_arrow2.setVisibility(View.VISIBLE);
            holder.myprofile.setVisibility(View.VISIBLE);
            holder.txt_nickName.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            holder.txt_msg2.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            holder.txt_date2.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            LinearLayout ln11 =  (LinearLayout) holder.ln1.getParent();
            LinearLayout ln12 =  (LinearLayout) holder.ln1;
            ln11. setGravity(Gravity.LEFT);
            holder.txt_msg2.setBackgroundColor(Color.YELLOW);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset==null ? 0: mDataset.size();
    }
    public UserDTO getChat(int position){
        return mDataset != null ? mDataset.get(position) : null;
    }
    public void addChat(UserDTO dto){
        mDataset.add(dto);
        notifyItemInserted(mDataset.size()-1);
    }

}
