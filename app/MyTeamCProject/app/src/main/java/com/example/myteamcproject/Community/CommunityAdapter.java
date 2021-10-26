package com.example.myteamcproject.Community;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.R;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder>
                implements CommunityWriterClickListener {

    private static final String TAG = "CommunityAdapter : ";

    CommunityWriterClickListener listener;
    ArrayList<CommunityDTO> dtos;
    Context context;
    LayoutInflater inflater;
    FragmentManager fragmentManager;

    public CommunityAdapter(ArrayList<CommunityDTO> dtos, Context context, FragmentManager fragmentManager) {
        this.dtos = dtos;
        this.context = context;
        this.fragmentManager = fragmentManager;

        inflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.community_view,
                parent, false);

        return new ViewHolder(itemView, this);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder:" + position);

        CommunityDTO dto = dtos.get(position);
        // viewHolder에 만들어 놓은 setDto에 선택된 dto를 넘긴다.
        holder.setdto(dto);

        holder.colistlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                FragCoView fragCoView = new FragCoView();
                Bundle bundle = new Bundle();
                bundle.putInt("c_numb", dtos.get(position).getC_numb());
                bundle.putInt("c_readcount", dtos.get(position).getC_readcount());
                bundle.putInt("positon", position);
                fragCoView.setArguments(bundle);
                transaction.replace(R.id.main_frag, fragCoView);
                notifyDataSetChanged();
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() { return dtos.size(); }

    public void addDto(CommunityDTO dto){
        dtos.add(dto);
    }

    public void removeDto(int position) {
        dtos.remove(position);
    }

    public void removeAlldto(){ dtos.clear(); }

    public CommunityDTO getItem(int position){
        return dtos.get(position);
    }

    public void setOnItemClickListener(CommunityWriterClickListener listener){
        this.listener = listener;
    }

    public void Refresh(){
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(ViewHolder holderc, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holderc, view, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        Button co_delete, co_update, co_searchbtn;
        TextView co_num, co_title, co_writer, co_readcount;
        LinearLayout colistlayout;

        public ViewHolder(@NonNull View itemView, CommunityWriterClickListener listener) {
            super(itemView);

            colistlayout = itemView.findViewById(R.id.colistlayout);
            co_num = itemView.findViewById(R.id.co_num);
            co_title = itemView.findViewById(R.id.co_title);
            co_writer = itemView.findViewById(R.id.co_writer);
            co_delete = itemView.findViewById(R.id.co_delete);
            co_update = itemView.findViewById(R.id.co_update);
            co_readcount = itemView.findViewById(R.id.co_readcount);
            co_searchbtn = itemView.findViewById(R.id.co_searchbtn);

            // 2.화면에 clickListener 를 달아준다.

        }
        // community_view 에 데이터를 셋팅시키는 함수를 만든다
        public void setdto(CommunityDTO dto){
            co_num.setText(String.valueOf(dto.getC_numb()));
            co_title.setText(dto.getC_title());
            co_writer.setText(dto.getC_writer());
            co_readcount.setText(String.valueOf(dto.getC_readcount()));
        }
    }
}
