package com.example.myteamcproject.Community;

import static com.example.myteamcproject.Common.CommonMethod.coDto;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myteamcproject.ATask.CommunityATask;
import com.example.myteamcproject.Exercise.FragChat;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.concurrent.ExecutionException;

public class FragCoUpdate extends Fragment {

    private static final String TAG = "FragCoUpdate";

    private final int GET_GALLERY_IMAGE = 200;
    public int reqPicCode = 1004;

    private View view;
    int c_numb;
    String cou_imgpath;
    private Button cou_submit, cou_reset;
    private EditText cou_title, cou_content;
    private ImageView cou_imgfile;
    MainActivity activity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_write, container, false);
        cou_submit = view.findViewById(R.id.submit);
        cou_reset = view.findViewById(R.id.reset);
        cou_title = view.findViewById(R.id.cod_title);
        cou_content = view.findViewById(R.id.cod_content);
        cou_imgfile = view.findViewById(R.id.imgfile);

        activity = new MainActivity();

        CommunityATask communityATask = new CommunityATask("coupdate", c_numb);

        try {
            communityATask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        if(coDto != null){
            cou_title.setText(coDto.getC_title());
            cou_content.setText(coDto.getC_content());
        }

        cou_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragComm fragChat = new FragComm();
                int co_update_c = coDto.c_numb;
                Log.d(TAG, "onClick_update: " + co_update_c);

                String cou_title_c = cou_title.getText().toString();
                String cou_content_c = cou_content.getText().toString();

                CommunityATask communityATask = new CommunityATask(co_update_c, "update", cou_title_c, cou_content_c, cou_imgpath, loginDTO.getId() );
                Log.d(TAG, "onClick: cnumb값" + co_update_c);

                try {
                    communityATask.execute().get();
                } catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }
                transaction.replace(R.id.main_frag, fragChat);
                transaction.commit();
            }
        });

        cou_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragChat fragChat = new FragChat();
                transaction.replace(R.id.main_frag, fragChat);
                transaction.commit();
            }
        });

        return view;
    }
}