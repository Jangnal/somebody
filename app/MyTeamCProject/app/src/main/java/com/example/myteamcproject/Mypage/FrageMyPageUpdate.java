package com.example.myteamcproject.Mypage;

import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myteamcproject.ATask.MemberATask;
import com.example.myteamcproject.R;

import java.util.concurrent.ExecutionException;

public class FrageMyPageUpdate extends Fragment {

    private View view;
    private Button finish_btn, cancel_btn ;
    private FragMyPage frag_myPage;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    TextView upmyid, upmypoint;
    EditText upmyemail, upmyname, upmyphone, upmygender, upmybmi, upmyweight, upmyheight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_mypage_update, container, false);

        //기본값 세팅
        upmyid = view.findViewById(R.id.upmyid);
        upmyemail = view.findViewById(R.id.upmyemail);
        upmyname = view.findViewById(R.id.upmyname);
        upmyphone = view.findViewById(R.id.upmyphone);
        upmygender = view.findViewById(R.id.upmygender);
        upmybmi = view.findViewById(R.id.upmybmi);
        upmyweight = view.findViewById(R.id.upmyweight);
        upmyheight = view.findViewById(R.id.upmyheight);
        upmypoint = view.findViewById(R.id.upmypoint);

        upmyid.setText(loginDTO.getId());
        upmyemail.setText(loginDTO.getEmail());
        upmyname.setText(loginDTO.getName());
        upmyphone.setText(loginDTO.getPhone());
        upmygender.setText(loginDTO.getGender());
        upmybmi.setText(String.valueOf(loginDTO.getBmi()));
        upmyweight.setText(String.valueOf(loginDTO.getWeight()));
        upmyheight.setText(String.valueOf(loginDTO.getHeight()));
        upmypoint.setText(String.valueOf(loginDTO.getPoint()));

        //취소 버튼
        cancel_btn=view.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragMyPage fragMyPage = new FragMyPage();
                transaction.replace(R.id.main_frag, fragMyPage);
                transaction.commit();
            }
        });


        //완료 버튼
        finish_btn=view.findViewById(R.id.finish_btn);
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = upmyid.getText().toString();
                String email = upmyemail.getText().toString();
                String name = upmyname.getText().toString();
                String phone = upmyphone.getText().toString();
                String gender = upmygender.getText().toString();
                int bmi = Integer.parseInt(upmybmi.getText().toString());
                int weight = Integer.parseInt(upmyweight.getText().toString());
                int height = Integer.parseInt(upmyheight.getText().toString());
                int point = Integer.parseInt(upmypoint.getText().toString());

                //ATask 연결
                MemberATask memberATask =
                        new MemberATask("myupdate", id, email, name, gender, phone, bmi
                                , weight, height, point);
                try {
                    memberATask.execute().get();
                } catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }

                Toast.makeText(getContext(), "작성이 완료 되었습니다.", Toast.LENGTH_SHORT).show();

                memberATask = new MemberATask("login", loginDTO.getId(), loginDTO.getPassword());
                try {
                    memberATask.execute().get();
                } catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }

                frag_myPage = new FragMyPage();
                fragmentManager = getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.main_frag,frag_myPage);
                fragmentTransaction.commit();

            }
        });


        return view;
    }
}
