package com.example.myteamcproject.Community;

import static com.example.myteamcproject.Common.CommonMethod.colist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.ATask.CommunityATask;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragChat extends Fragment {
    private static final String TAG = "FragChat: ";

    private Button write, co_searchbtn;
    private EditText co_search;
    private RecyclerView recyclerView_co;
    public CommunityAdapter adapter;
    ArrayList<CommunityDTO> dtos;
    MainActivity activity;
    FragmentManager fragmentManager;
    String search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_comm, container, false);
        activity = (MainActivity) getActivity();
        CommunityATask communityATask = new CommunityATask("list");

        try {
            communityATask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        dtos = new ArrayList<>();


        recyclerView_co = viewGroup.findViewById(R.id.recyclerView_co);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                activity, RecyclerView.VERTICAL, false);
        recyclerView_co.setLayoutManager(layoutManager);

        fragmentManager = getParentFragmentManager();

        adapter = new CommunityAdapter(dtos, activity, fragmentManager);


        if(colist != null){
        for (int i = 0; i <= colist.size() - 1; i++ ){
            adapter.addDto(colist.get(i));
            }
        }
        recyclerView_co.setAdapter(adapter);

        // 버튼클릭 메소드
        write = viewGroup.findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragWrite fragWrite = new FragWrite();

                transaction.replace(R.id.main_frag, fragWrite);
                transaction.commit();
            }
        });
        // 검색 메소드

        co_search = viewGroup.findViewById(R.id.co_search);


        co_searchbtn = viewGroup.findViewById(R.id.co_searchbtn);
        co_searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommunityATask communityATask_se = new CommunityATask("search", search);
                search = co_search.getText().toString();
                try {
                    communityATask_se.execute().get();
                    adapter = new CommunityAdapter(dtos, activity, fragmentManager);
                    recyclerView_co.setLayoutManager(layoutManager);
                    if(colist != null){
                        for (int i = 0; i <= colist.size() - 1; i++ ){
                            adapter.addDto(colist.get(i));
                        }
                    }
                    recyclerView_co.setAdapter(adapter);
                }catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            }
        });

        return viewGroup;
    }

}
