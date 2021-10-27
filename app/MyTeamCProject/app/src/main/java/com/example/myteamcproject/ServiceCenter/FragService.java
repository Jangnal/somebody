package com.example.myteamcproject.ServiceCenter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myteamcproject.R;

public class FragService extends Fragment {

    View view;
    Button btnQA, btnChat;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       view = inflater.inflate(R.layout.frag_service, container, false);

       view.findViewById(R.id.btnQA).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragQA fragQA = new FragQA();
               getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fragQA).commit();

           }//onClick
       });//view.findViewById(R.id.btnQA).setOnClickListener

       view.findViewById(R.id.btnChat).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragMyChat fragMyChat = new FragMyChat();
               getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fragMyChat).commit();
           }//onClick
       });//view.findViewById(R.id.btnChat).setOnClickListener



       return view;

    }
}