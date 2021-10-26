package com.example.myteamcproject.Community;

import static com.example.myteamcproject.Common.CommonMethod.colist;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

public class FragComm extends Fragment {

    private Button write, co_searchbtn;
    private CheckBox mywrite;
    private EditText co_search;
    private RecyclerView recyclerView_co;
    public CommunityAdapter adapter;
    ArrayList<CommunityDTO> dtos;
    MainActivity activity;
    FragmentManager fragmentManager;
    String search, mysearch;

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
                if(loginDTO != null){
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    FragWrite fragWrite = new FragWrite();
                    transaction.replace(R.id.main_frag, fragWrite);
                    transaction.commit();
                }else{
                    Toast.makeText(getActivity(), "로그인 후 이용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
                }//if
            }
        });

        //검색 메소드
        co_search = viewGroup.findViewById(R.id.co_search);
        co_searchbtn = viewGroup.findViewById(R.id.co_searchbtn);
        co_searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = co_search.getText().toString();
                CommunityATask communityATask_se = new CommunityATask("search", search);
                try {
                    communityATask_se.execute().get();
                    if(colist != null){
                            adapter.removeAlldto();
                        for (int i = 0; i <= colist.size() - 1; i++ ){
                            adapter.addDto(colist.get(i));
                        }
                        recyclerView_co.setAdapter(adapter);
                        adapter.Refresh();
                    }
                }catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            }
        });

        //내글보기 체크박스
        mywrite = viewGroup.findViewById(R.id.mywrite);
        if(loginDTO != null){
            mywrite.setVisibility(View.VISIBLE);
        }else{
            mywrite.setVisibility(View.GONE);
        }//if
        mywrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mywrite.isChecked()){
                    CommunityATask communityATask_se = new CommunityATask("mysearch", loginDTO.getId());
                    try {
                        communityATask_se.execute().get();
                        if(colist != null){
                            adapter.removeAlldto();
                            for (int i = 0; i <= colist.size() - 1; i++ ){
                                adapter.addDto(colist.get(i));
                            }
                            recyclerView_co.setAdapter(adapter);
                            adapter.Refresh();
                        }
                    }catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }
                }else{
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
                }
            }
        });

        return viewGroup;
    }

}
