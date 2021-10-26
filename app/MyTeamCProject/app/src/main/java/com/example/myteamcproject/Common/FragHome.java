package com.example.myteamcproject.Common;

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
import com.example.myteamcproject.Exercise.ExerciseDTO;
import com.example.myteamcproject.Exercise.ExerciseTypeAdapter;
import com.example.myteamcproject.Exercise.OnExerciseTypeItemClickListener;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragHome extends Fragment {

    //log용 TAG
    private static final String TAG = "FragHome";

    //리사이클러뷰와 어뎁터 그리고 담을 arrayList 변수 선언
    RecyclerView recyclerView_ext;
    ExerciseTypeAdapter adapter;
    ArrayList<ExerciseDTO> dtos;

    // 자신이 활동하는 Activity 찾기
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //프래그먼트를 인플레이트로 연결
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_home, container, false);
        Log.d("FragHome", "onCreateView: ");

        //프래그먼트에서 액티비티에 있는 객체를 사용하기 위한 액티비티 변수 선언 후 액티비티를 저장
        activity = (MainActivity) getActivity();

        //DB와 연동하기 위한 Atask 객체 선언
        ExerciseATask exerciseATask = new ExerciseATask("type");

        //Atask 실행 후 값을 가져온다
        try {
            exerciseATask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        // 어뎁터에 보내기 위해 위에서 선언한 arraylist 객체 선언
        dtos = new ArrayList<>();

        //fraghome에 있는 recyclerView_ext 리사이클러뷰를 담는 다
        recyclerView_ext = viewGroup.findViewById(R.id.recyclerView_ext);

        // 레이아웃 매니저 생성
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                activity, RecyclerView.VERTICAL, false);
        recyclerView_ext.setLayoutManager(layoutManager);

        FragmentManager fragmentManager = getParentFragmentManager();
        // 어댑터 객체를 생성하고 arraylist와 액티비티를 넘겨준다
        adapter = new ExerciseTypeAdapter(dtos, activity, fragmentManager);

        // 어댑터에 ArrayList에 dto를 추가한다
        for (int i = 0; i <= exlist.size() - 1; i++ ){
            adapter.addDto(exlist.get(i));
        }//for

        // 만든 어댑터를 리사이클러 뷰에 붙인다
        recyclerView_ext.setAdapter(adapter);

        // 어댑터에 있는 clickListener를 이용해 클릭한 위치에 dto를 가져온다.
        adapter.setOnItemClickListener(new OnExerciseTypeItemClickListener() {
            @Override
            public void onItemClick(ExerciseTypeAdapter.ViewHolder holder, View view, int position) {
                // 클릭하면 어댑터에게서 받은 클릭위치를 이용해 dto를 가져온다.
                ExerciseDTO dto = adapter.getItem(position);
            }
        });


        return viewGroup;
    }

}
