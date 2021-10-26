package com.example.myteamcproject.Mypage;

import static com.example.myteamcproject.Common.CommonMethod.explaylist;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.ATask.ExManageATask;
import com.example.myteamcproject.Exercise.ExerciseManageAdapter;
import com.example.myteamcproject.Exercise.ExerciseManageAdapter2;
import com.example.myteamcproject.Exercise.OnExerciseManageItemClickListener;
import com.example.myteamcproject.Exercise.OnExerciseManageItemClickListener2;
import com.example.myteamcproject.Exercise.UserExerciseDTO;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragMyPage extends Fragment {
    private View view;

    TextView myid, myemail, myname, myphone, mygender, mybmi, myweight, myheight, mypoint;
    ImageView imgmypage;
    RecyclerView recyclerView_exm1;
    RecyclerView recyclerView_exm2;
    ExerciseManageAdapter adapter5;
    ExerciseManageAdapter2 adapter6;
    ArrayList<UserExerciseDTO> dtos, dtos2;
    MainActivity activity;

    private Button update_btn;

    private static final String TAG = "FragMyPage";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_mypage, container, false);
        Log.d("FragMyPage", "onCreateView: ");

        update_btn= view.findViewById(R.id.update_btn);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FrageMyPageUpdate frageMyPageUpdate = new FrageMyPageUpdate();
                transaction.replace(R.id.main_frag, frageMyPageUpdate);
                transaction.commit();
            }
        });

        myid = view.findViewById(R.id.myid);
        myemail = view.findViewById(R.id.myemail);
        myname = view.findViewById(R.id.myname);
        myphone = view.findViewById(R.id.myphone);
        mygender = view.findViewById(R.id.mygender);
        mybmi = view.findViewById(R.id.mybmi);
        myweight = view.findViewById(R.id.myweight);
        myheight = view.findViewById(R.id.myheight);
        mypoint = view.findViewById(R.id.mypoint);
        imgmypage = view.findViewById(R.id.imgmypage);

        myid.setText(loginDTO.getId());
        myemail.setText(loginDTO.getEmail());
        myname.setText(loginDTO.getName());
        myphone.setText(loginDTO.getPhone());
        mygender.setText(loginDTO.getGender());
        mybmi.setText(String.valueOf(loginDTO.getBmi()));
        myweight.setText(String.valueOf(loginDTO.getWeight()));
        myheight.setText(String.valueOf(loginDTO.getHeight()));
        mypoint.setText(String.valueOf(loginDTO.getPoint()));

        if(loginDTO.getMember_c_file_path() != null && loginDTO.getMember_c_file_name() != null) {
            String filepath = ipConfig + "/resources/" + loginDTO.getMember_c_file_path();
            Log.d(TAG, "setDto: " + filepath);
            Glide.with(this).load(filepath).circleCrop().into(imgmypage);
        }//if file null

        activity = (MainActivity) getActivity();

        String id = loginDTO.getId();

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

        recyclerView_exm1 = view.findViewById(R.id.recyclerView_exm1);
        recyclerView_exm2 = view.findViewById(R.id.recyclerView_exm2);
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

        return view;
    }
}
