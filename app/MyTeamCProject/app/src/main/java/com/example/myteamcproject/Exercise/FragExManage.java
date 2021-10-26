package com.example.myteamcproject.Exercise;

import static com.example.myteamcproject.Common.CommonMethod.explaylist;

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

import com.example.myteamcproject.ATask.ExManageATask;
import com.example.myteamcproject.Common.CommonMethod;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragExManage extends Fragment {
    private static final String TAG = "FragExManage";

    RecyclerView recyclerView_exm1;
    RecyclerView recyclerView_exm2;
    ExerciseManageAdapter adapter5;
    ExerciseManageAdapter2 adapter6;
    String id;
    ArrayList<UserExerciseDTO> dtos, dtos2;
    FragmentManager fragmentManager;

    public FragExManage(){

    }

    // 생성자
    public FragExManage(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;

    }

    // 자신이 활동하는 Activity 찾기
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_exm, container, false);

        activity = (MainActivity) getActivity();

        String id = CommonMethod.loginDTO.getId();

        ExManageATask exManageATask = new ExManageATask("exp", id);

        //Atask 실행 후 값을 가져온다
        try {
            exManageATask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        // 반드시 생성해서 어댑터에 넘겨야 함
        dtos = new ArrayList<>();
        dtos2 = new ArrayList<>();

        recyclerView_exm1 = viewGroup.findViewById(R.id.recyclerView_exm1);
        recyclerView_exm2 = viewGroup.findViewById(R.id.recyclerView_exm2);
        // recyclerView 에서 반드시 초기화해야 하는 부분
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                activity, RecyclerView.VERTICAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(
                activity, RecyclerView.VERTICAL, false);
        recyclerView_exm1.setLayoutManager(layoutManager);
        recyclerView_exm2.setLayoutManager(layoutManager2);

        FragmentManager fragmentManager = getParentFragmentManager();

        // 어댑터 객체를 생성한다
        adapter5 = new ExerciseManageAdapter(dtos, activity, fragmentManager);
        adapter6 = new ExerciseManageAdapter2(dtos2, activity, fragmentManager);

        // 어댑터에 ArrayList에 dto를 추가한다
        try{

        for (int i = 0; i <= explaylist.size() - 1; i++ ){
               if (explaylist.get(i).getU_complete().equals("N")){
                    adapter5.addDto(explaylist.get(i));
                }//if
                  if(explaylist.get(i).getU_complete().equals("Y")){
                    adapter6.addDto(explaylist.get(i));
                  }
            }//for

        }catch (Exception e){
            Log.d(TAG, "onCreateView: " + "Null error");
            e.getMessage();
            e.getStackTrace();
        }

        // 만든 어댑터를 리사이클러 뷰에 붙인다
        recyclerView_exm1.setAdapter(adapter5);
        recyclerView_exm2.setAdapter(adapter6);

        // 6.어댑터에 있는 clickListener를 이용해 클릭한 위치에 dto를 가져온다.
        adapter5.setOnItemClickListener(new OnExerciseManageItemClickListener() {
            @Override
            public void onItemClick(ExerciseManageAdapter.ViewHolder holderm, View view, int position) {

            }
        });
        adapter6.setOnItemClickListener(new OnExerciseManageItemClickListener2() {
            @Override
            public void onItemClick(ExerciseManageAdapter2.ViewHolder holderm, View view, int position) {

            }
        });

        return viewGroup;
    }
}
