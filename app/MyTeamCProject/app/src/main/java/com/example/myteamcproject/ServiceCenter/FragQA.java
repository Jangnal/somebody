package com.example.myteamcproject.ServiceCenter;
import static com.example.myteamcproject.Common.CommonMethod.qalist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myteamcproject.ATask.QaATask;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.concurrent.ExecutionException;

public class FragQA extends Fragment {

    private static final String TAG = "FragQA";

    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_qa, container, false);

        //프래그먼트에서 액티비티에 있는 객체를 사용하기 위한 액티비티 변수 선언 후 액티비티를 저장
        activity = (MainActivity) getActivity();

        //DB와 연동하기 위한 Atask 객체 선언
        QaATask qaATask = new QaATask();

        //Atask 실행 후 값을 가져온다
        try {
           qaATask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        Log.d(TAG, "onCreateView: " + qalist.size());

        return viewGroup;
    }
}
