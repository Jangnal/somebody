package com.example.myteamcproject.Exercise;

import static com.example.myteamcproject.Common.CommonMethod.exlist;
import static com.example.myteamcproject.Common.CommonMethod.explaylist;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.tonext;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.R;

public class FragExStart extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragExStart";

    String e_type;
    int pos;
    FragmentManager fragmentManager;

    CountDownTimer countDownTimer;
    int timer;

    public FragExStart() {

    }

    // 생성자
    public FragExStart(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_exs, container, false);

        Bundle bundle = getArguments();
        ExerciseDTO dto = null;
            pos = bundle.getInt("pos");
            dto = (ExerciseDTO) bundle.getSerializable("dto");

        Log.d("TAG", "onCreateView: " + e_type);

        // Fragment에서는 onClick을 사용할 수 없기때문에, 별도로 리스너를 달아서 클릭이벤트를 지정한다.
        TextView tv_etitle = viewGroup.findViewById(R.id.tv_etitle);
        TextView tv_time = viewGroup.findViewById(R.id.tv_time);
        ImageView img_play = viewGroup.findViewById(R.id.img_play);
        ImageView img_pause = viewGroup.findViewById(R.id.img_pause);
        ImageView img_exs = viewGroup.findViewById(R.id.img_exs);
        img_play.setOnClickListener(this);
        img_pause.setOnClickListener(this);

        if(!explaylist.isEmpty()){
            tv_etitle.setText(explaylist.get(pos).getE_name());
        }else{
            tv_etitle.setText(explaylist.get(pos).getE_name());
        }

        String filepath = ipConfig + "/resources/"  + dto.getE_filepath() + dto.getE_filename();
        Log.d(TAG, "setDto: " + filepath);
        Glide.with(viewGroup).load(filepath).into(img_exs);

        countDownTimer = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished) {
                int num = (int) (millisUntilFinished / 1000);
                timer = num;
                tv_time.setText(Integer.toString(timer));

            }

            public void onFinish() {

                try{
                if(explaylist.get(tonext + 1) != null){
                    Bundle bundle = new Bundle(); //번들을 통해 값 전달

                    bundle.putInt("pos", pos + 1);
                    bundle.putSerializable("dto", exlist.get(pos + 1));
                    FragExStart fragExStart = new FragExStart();   //FragExStart 선언
                    fragExStart.setArguments(bundle);    //번들을 FragExStart 로 보낼 준비

                    fragmentManager = getParentFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    transaction.replace(R.id.main_frag, fragExStart).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);

                    transaction.commit();
                }
                } catch (IndexOutOfBoundsException e){
                    tv_time.setText("운동 끝");
                    FragHome fraghome = new FragHome();
                    getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fraghome).commit();
                }

            }//onFinish
        };

        return viewGroup;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_play:
                countDownTimer.start();
                break;

            case R.id.img_pause:
                countDownTimer.cancel();
                break;
        }
    }//onclick
}
