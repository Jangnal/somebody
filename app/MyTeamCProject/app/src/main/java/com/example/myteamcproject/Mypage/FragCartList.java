package com.example.myteamcproject.Mypage;

import static com.example.myteamcproject.Common.CommonMethod.cartlist;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.ATask.CartAtask;
import com.example.myteamcproject.Gift.CartAdapter;
import com.example.myteamcproject.Gift.CartDTO;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class FragCartList extends Fragment {

    RecyclerView recyclerView_ca;
    CartAdapter adapter;
    ArrayList<CartDTO> dtos;
    MainActivity activity;
    FragmentManager fragmentManager;

    Button btnAllBuy;

    public FragCartList() {

    }

    public FragCartList(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_cart, container, false);
        activity = (MainActivity) getActivity();


        CartAtask cartAtask = new CartAtask("cartlist", loginDTO.getId());

        try {
            cartAtask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        dtos = new ArrayList<>();


        recyclerView_ca = viewGroup.findViewById(R.id.recyclerView_ca);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                activity, RecyclerView.VERTICAL, false);
        recyclerView_ca.setLayoutManager(layoutManager);

        fragmentManager = getParentFragmentManager();

        // 어댑터 객체를 생성한다
        adapter = new CartAdapter(dtos, activity, fragmentManager);


        if(cartlist != null){
            for (int i = 0; i <= cartlist.size() - 1; i++ ){
                adapter.addDto(cartlist.get(i));
            }
        }

        // 만든 어댑터를 리사이클러 뷰에 붙인다
        recyclerView_ca.setAdapter(adapter);

        btnAllBuy = viewGroup.findViewById(R.id.btnAllBuy);
        btnAllBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }//onClick
        });//btnAllBuy.setOnClickListener

        return viewGroup;
    }


}
