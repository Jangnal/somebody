package com.example.myteamcproject.ServiceCenter;

import static com.example.myteamcproject.Common.CommonMethod.loginDTO;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.myteamcproject.R;

public class FragService extends Fragment {

    View view;
    Button btnQA, btnChat, btnadmin;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       view = inflater.inflate(R.layout.frag_service, container, false);

       btnQA = view.findViewById(R.id.btnQA);
       btnChat = view.findViewById(R.id.btnChat);
       btnadmin = view.findViewById(R.id.btnAdminChat);

       if(loginDTO.getAdmin().equals("Y")){
           btnChat.setVisibility(View.GONE);
           btnadmin.setVisibility(View.VISIBLE);
       }else if(loginDTO.getAdmin().equals("N")){
           btnChat.setVisibility(View.VISIBLE);
           btnadmin.setVisibility(View.GONE);
       }

       btnQA.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragQA fragQA = new FragQA();
               getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fragQA).commit();

           }//onClick
       });//view.findViewById(R.id.btnQA).setOnClickListener


       btnChat.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragMyChat fragMyChat = new FragMyChat();
               getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fragMyChat).commit();
           }//onClick
       });//view.findViewById(R.id.btnChat).setOnClickListener

       btnadmin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragChatroom fragChatroom = new FragChatroom();
               getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fragChatroom).commit();
           }//onClick
       });//view.findViewById(R.id.btnAdminChat).setOnClickListener

       return view;

    }
}