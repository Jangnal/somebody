package com.example.myteamcproject.Exercise;

import static com.example.myteamcproject.Common.CommonMethod.exlist;
import static com.example.myteamcproject.Common.CommonMethod.explaylist;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.ATask.ExInsertATask;
import com.example.myteamcproject.ATask.ExUpdateATask;
import com.example.myteamcproject.ATask.ExerciseATask;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragEx extends Fragment implements View.OnClickListener {
    private static final String TAG = "main:FragEx";

    RecyclerView recyclerView_ex;
    ExerciseAdapter adapter2;
    String e_type;
    int position;
    ArrayList<ExerciseDTO> dtos;
    FragmentManager fragmentManager;


    public FragEx(){

    }
    // 생성자
    public FragEx(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;

    }

    // 자신이 활동하는 Activity 찾기
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_ex, container, false);



        // Fragment에서는 onClick을 사용할 수 없기때문에, 별도로 리스너를 달아서 클릭이벤트를 지정한다.
        Button btnStart1 = viewGroup.findViewById(R.id.btnStart1);
        btnStart1.setOnClickListener(this);

        Bundle bundle = getArguments();
        if (bundle!=null){
            e_type = bundle.getString("e_type");

        }

        Log.d("TAG", "onCreateView: " + e_type);

        activity = (MainActivity) getActivity();

        ExerciseATask exerciseATask = new ExerciseATask("ex", e_type);

        try {
            exerciseATask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        // 반드시 생성해서 어댑터에 넘겨야 함
        dtos = new ArrayList<>();

        recyclerView_ex = viewGroup.findViewById(R.id.recyclerView_ex);

        // recyclerView 에서 반드시 초기화해야 하는 부분
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                activity, RecyclerView.VERTICAL, false);
        recyclerView_ex.setLayoutManager(layoutManager);


        FragmentManager fragmentManager = getParentFragmentManager();

        // 어댑터 객체를 생성한다
        adapter2 = new ExerciseAdapter(dtos, activity, fragmentManager);

        // 어댑터에 ArrayList에 dto를 추가한다
        for (int i = 0; i <= exlist.size() - 1; i++ ){
            adapter2.addDto(exlist.get(i));
        }//for

        // 만든 어댑터를 리사이클러 뷰에 붙인다
        recyclerView_ex.setAdapter(adapter2);

        // 6.어댑터에 있는 clickListener를 이용해 클릭한 위치에 dto를 가져온다.
        adapter2.setOnItemClickListener(new OnExerciseItemClickListener() {
            @Override
            public void onItemClick(ExerciseAdapter.ViewHolder holderm, View view, int position) {
                // 8.클릭하면 어댑터에게서 받은 클릭위치를 이용해 dto를 가져온다.

            }

        });

        return viewGroup;
    }

    // 운동시작버튼
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            //frag_ex 운동시작 버튼
            case R.id.btnStart1:
                try {
                    if (explaylist.size() != 0) {
                        
                        /*ExerciseDTO dto = adapter2.getItem(position);
                        Bundle bundle = new Bundle(); //번들을 통해 값 전달
                        String e_type = dto.getE_type();
                        bundle.putString("e_type", e_type);   //번들에 넘길 값 저장
                        bundle.putInt("pos", position);
                        bundle.putSerializable("dto", exlist.get(position));
                        FragExStart fragExStart = new FragExStart();   //FragExStart 선언
                        fragExStart.setArguments(bundle);    //번들을 FragExStart 로 보낼 준비

                        fragmentManager = getParentFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();

                        transaction.replace(R.id.main_frag, fragExStart).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);*/

                        for(int i=0; i<explaylist.size(); i++){
                            Log.d(TAG, "onClick: explaylist " + i + explaylist.get(i).getE_name());

                            ExInsertATask insertATask = new ExInsertATask("exi", loginDTO.getId(), explaylist.get(i).getE_name());//id, e_name값;
                            try{
                                insertATask.execute().get();
                            }catch (Exception e){

                            }

                        }

                        FragExStart fragExStart = new FragExStart(fragmentManager);
                        ExerciseDTO dto = adapter2.getItem(position);
                        Bundle bundle = new Bundle(); //번들을 통해 값 전달
                        String e_type = dto.getE_type();
                        bundle.putString("e_type", e_type);   //번들에 넘길 값 저장
                        bundle.putInt("pos", position);
                        bundle.putSerializable("dto", exlist.get(position));
                        activity.setFrag(fragExStart, bundle);



                       /* ExInsertATask insertATask = new ExInsertATask("exi", loginDTO.getId(), explaylist.get(position).getE_name());//id, e_name값;
                            String e_name = "윗몸일으키기" + "팔굽혀펴기";
                            explaylist.add(new UserExerciseDTO(loginDTO.getId(), explaylist.get(position).getE_name()));
                            explaylist.add(new UserExerciseDTO(loginDTO.getId(), explaylist.get(position).getE_name()));

                        ExUpdateATask updateATask = new ExUpdateATask("exu", u_numb);//u_numb값;

                        try{
                            insertATask.execute().get();
                            updateATask.execute().get();
                        }catch (Exception e){
                            e.getStackTrace();
                        }
                        transaction.commit();*/
                        break;

                    }else if(adapter2.getItem(position).getE_name().equals("걷기")){

                        ExerciseDTO dto = adapter2.getItem(position);

                        Bundle bundle = new Bundle(); //번들을 통해 값 전달
                        String e_type = dto.getE_type();
                        //String e_filepath = "";
                        bundle.putString("e_type", e_type);   //번들에 넘길 값 저장
                        //bundle.putString("운동사진", e_filepath);
                        bundle.putInt("pos", position);
                        bundle.putSerializable("dto", exlist.get(position));
                        FragWalk fragWalk = new FragWalk();
                        fragWalk.setArguments(bundle);

                        fragmentManager = getParentFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();

                        transaction.replace(R.id.main_frag, fragWalk).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);

                        transaction.commit();
                    }

                }catch (IndexOutOfBoundsException e){
                        Toast.makeText(getContext(), "운동 목록이 비어있습니다", Toast.LENGTH_SHORT).show();
                        FragWalk fragWalk = new FragWalk();
                        getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fragWalk).commit();
                    }//try & catch
        }//switch

    }//onclick

}
