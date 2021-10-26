package com.example.myteamcproject.Exercise;

import static com.example.myteamcproject.Common.CommonMethod.exlist;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.example.myteamcproject.R;

public class FragWalk extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragWalk";
    
    String e_type;
    int pos;
    FragmentManager fragmentManager;

    public FragWalk() {

    }
    // 생성자
    public FragWalk(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;

    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_walk, container, false);

        Bundle bundle = getArguments();
        ExerciseDTO dto = null;
        pos = bundle.getInt("pos");
        dto = (ExerciseDTO) bundle.getSerializable("dto");

        TextView tv_walk = viewGroup.findViewById(R.id.tv_walk);
        tv_walk.setText(exlist.get(pos).getE_content());

        String filepath = ipConfig + "/resources/"  + dto.getE_filepath() + dto.getThumbnail();
        Log.d(TAG, "setDto: " + filepath);

        ImageView img_walk = viewGroup.findViewById(R.id.img_walk);
        Glide.with(viewGroup).load(R.raw.walk).into(img_walk);

        Log.d("TAG", "onCreateView: " + e_type);


        // Fragment에서는 onClick을 사용할 수 없기때문에, 별도로 리스너를 달아서 클릭이벤트를 지정한다.
        ImageView img_reset = viewGroup.findViewById(R.id.img_reset);
        img_reset.setOnClickListener(this);

        return viewGroup;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_reset:

                break;
        }
    }
}
