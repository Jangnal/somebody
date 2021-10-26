package com.example.myteamcproject.Gift;

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
import com.example.myteamcproject.Mypage.FragCartList;
import com.example.myteamcproject.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>
        implements CartWriterClickListener {//alt + ent 로 임프리트
    private static final String TAG = "main: CartAdapter";

    //메인에서 보내주는거
    CartWriterClickListener listener;
    Context context;
    ArrayList<CartDTO> dtos;
    FragmentManager fragmentManager;

    //화면 붙이기용 객체 생성
    LayoutInflater inflater;

    View view;

    public CartAdapter(ArrayList<CartDTO> dtos, Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.dtos = dtos;
        this.fragmentManager = fragmentManager;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //캐스트

    }
    //하나의 dto 추가하기



    //Arraylist에 저장된 dto 갯수
    public int getCount() {return dtos.size();}


    //ArrayList 특정위치에 있는 dto 가져오기
    public CartDTO getItem(int position) { return dtos.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.cartview,
                parent, false);

        return new ViewHolder(itemView, this);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: adapter" + position);

        CartDTO dto = dtos.get(position);
        // viewHolder에 만들어 놓은 setDto에 선택된 dto를 넘긴다.
        holder.setDto(dto);

        holder.cartlist_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                FragCartList fragCartList = new FragCartList();
                Bundle bundle = new Bundle();
                bundle.putString("id", dtos.get(position).getId());
                bundle.putInt("point", dtos.get(position).getPoint());
                bundle.putString("cart_title", dtos.get(position).getCart_title());
                fragCartList.setArguments(bundle);
                transaction.replace(R.id.main_frag, fragCartList);
                notifyDataSetChanged();
                transaction.commit();
            }
        });

    }


    @Override
    public int getItemCount() { return dtos.size(); }

    public void addDto(CartDTO dto) {dtos.add(dto);}

    public void removeDto(int position) {
        dtos.remove(position);
    }

    @Override
    public long getItemId(int postion) {return postion;}


    public void setOnItemClickListener(CartWriterClickListener listener){
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        // singerview에 있는 모든 위젯을 정의한다.
        ImageView giftImg;
        TextView giftName, giftContent, giftPoint;
        String filepath;   //cartdto에서도 filepath를 추가해야한다
        LinearLayout cartlist_view;
        // singerview에 정의한 아이디와 연결시킨다 (생성자)
        // 아래에 itemView가 하나의 singerview라고 생각
        public ViewHolder(@NonNull View itemView, CartWriterClickListener listener) {
            super(itemView);

            giftImg = itemView.findViewById(R.id.giftImg);
            giftName = itemView.findViewById(R.id.giftName);
            giftContent = itemView.findViewById(R.id.giftContent);
            giftPoint = itemView.findViewById(R.id.giftPoint);
            cartlist_view = itemView.findViewById(R.id.cartlist_view);

        }

        // singerview에 데이터를 셋팅시키는 함수를 만든다
        public void setDto(CartDTO dto) {
            giftName.setText(dto.getCart_title());
            giftContent.setText(dto.getCart_title());
            giftPoint.setText(String.valueOf(dto.getPoint()));
            filepath = ipConfig + "/resources/"  + dto.getFilepath() + dto.getFilename();
            Log.d(TAG, "setDto: " + filepath);
            Glide.with(itemView).load(filepath).into(giftImg);
        }


    }
}
