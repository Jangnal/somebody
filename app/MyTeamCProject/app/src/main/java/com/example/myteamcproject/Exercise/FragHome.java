package com.example.myteamcproject.Exercise;

import static com.example.myteamcproject.Common.CommonMethod.exlist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.ATask.ExerciseATask;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragHome extends Fragment {

    private static final String TAG = "FragHome";

    RecyclerView recyclerView_ext;
    ExerciseTypeAdapter adapter1;
    ArrayList<ExerciseDTO> dtos;

    // 자신이 활동하는 Activity 찾기
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_home, container, false);
        Log.d("FragHome", "onCreateView: ");

        activity = (MainActivity) getActivity();

        ExerciseATask exerciseATask = new ExerciseATask("type");

        try {
            exerciseATask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        // 반드시 생성해서 어댑터에 넘겨야 함
        dtos = new ArrayList<>();

        recyclerView_ext = viewGroup.findViewById(R.id.recyclerView_ext);
        // recyclerView 에서 반드시 초기화해야 하는 부분
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                activity, RecyclerView.VERTICAL, false);
        recyclerView_ext.setLayoutManager(layoutManager);

        FragmentManager fragmentManager = getParentFragmentManager();

        // 어댑터 객체를 생성한다
        adapter1 = new ExerciseTypeAdapter(dtos, activity, fragmentManager);

        // 어댑터에 ArrayList에 dto를 추가한다
        for (int i = 0; i <= exlist.size() - 1; i++ ){
            adapter1.addDto(exlist.get(i));
        }//for

        // 만든 어댑터를 리사이클러 뷰에 붙인다
        recyclerView_ext.setAdapter(adapter1);

        // 6.어댑터에 있는 clickListener를 이용해 클릭한 위치에 dto를 가져온다.
        adapter1.setOnItemClickListener(new OnExerciseTypeItemClickListener() {
            @Override
            public void onItemClick(ExerciseTypeAdapter.ViewHolder holder, View view, int position) {

            }
        });

        return viewGroup;
    }

}
