package com.example.myteamcproject.ServiceCenter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.usermgmt.response.model.User;

import java.util.ArrayList;

public class FragChatroom extends Fragment {

    private static final String TAG = "FragChatroom";

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<UserDTO> arrayList;
    private ArrayList<String> strings;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference childReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_chatroom, container, false);

        recyclerView = view.findViewById(R.id.recyl);    //아이디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        strings= new ArrayList<>();

        database = FirebaseDatabase.getInstance();  //파이어베이스 데이터베이스 연동
        adapter = new CustomAdapter(arrayList, getContext(), getParentFragmentManager());

        databaseReference = database.getReference("chatroom");
        Log.d(TAG, "databaseReference: " + databaseReference.get());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id = snapshot.getKey().trim();
                    strings.add(id);
                }//for
                String laststring = strings.get(strings.size()-1);
                for(int i = 0; i < strings.size(); i++){

                    childReference = databaseReference.child(strings.get(i));
                    if(strings.get(i).indexOf("admin") == -1){
                       childReference.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshoti) {
                               UserDTO user = null;

                               for (DataSnapshot snapshoti : dataSnapshoti.getChildren()){
                                   if(!snapshoti.getValue(UserDTO.class).getName().equals("admin")){
                                       user = snapshoti.getValue(UserDTO.class);
                                   }//if

                               }//for
                               if(user != null){
                                   adapter.addList(user);
                                   Log.d(TAG, "onDataChange: 일 " + arrayList.size());
                                   adapter.notifyDataSetChanged();
                               }//if

                           }//onDataChange

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });
                    }//if
                    if(strings.get(i).equals(laststring)){
                        recyclerView.setAdapter(adapter);   //리사이클러뷰에 어뎁터 연결
                    }
                }//for
                Log.d(TAG, "onDataChange: 이 " + arrayList.size());
                adapter.notifyDataSetChanged();
            }//onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //DB를 가져오던 중 에러 발생 시
                Log.d(TAG, "onCancelled: " + error.toException());      //에러문 출력

            }
        });
        Log.d(TAG, "onDataChange: 삼 " + arrayList.size());

        return view;
    }
}
