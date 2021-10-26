package com.example.myteamcproject.Mypage;

import static com.example.myteamcproject.Common.CommonMethod.foodDTO;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myteamcproject.ATask.FoodATask;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.concurrent.ExecutionException;

public class FrageMyfoodupdate extends Fragment {
    private ImageView m_food_pic, l_food_pic, d_food_pic;
    private View view;
    private Button cancelbtn, update_btn ;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragMfood frag_mfood;

    String writedate;
    EditText m_food_menu, l_food_menu, d_food_menu, food_content;
    TextView date_text;

    String imgFilePath1 = "";
    String imgFilePath2 = "";
    String imgFilePath3 = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_mfood_update, container, false);
        Bundle bundle = getArguments();
        writedate = bundle.getString("writedate");
        cancelbtn= view.findViewById(R.id.cancelbtn);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragMfood fragMfood = new FragMfood();
                transaction.replace(R.id.main_frag, fragMfood);
                transaction.commit();
            }
        });

        update_btn = view.findViewById(R.id.update_btn);
        date_text = view.findViewById(R.id.date_text);
        m_food_menu = view.findViewById(R.id.m_food_menu);
        l_food_menu = view.findViewById(R.id.l_food_menu);
        d_food_menu = view.findViewById(R.id.d_food_menu);
        food_content = view.findViewById(R.id.food_content);

        date_text.setText(writedate);
        m_food_menu.setText(foodDTO.getMorning());
        l_food_menu.setText(foodDTO.getLunch());
        d_food_menu.setText(foodDTO.getDinner());
        food_content.setText(foodDTO.getContent());

        m_food_pic = (ImageView)view.findViewById(R.id.m_food_pic);
        m_food_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentm = new Intent(Intent.ACTION_PICK);
                intentm.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intentm, 200);
            }
        });

        l_food_pic = (ImageView)view.findViewById(R.id.l_food_pic);
        l_food_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentl = new Intent(Intent.ACTION_PICK);
                intentl.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intentl, 300);
            }
        });

        d_food_pic = (ImageView)view.findViewById(R.id.d_food_pic);
        d_food_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentd = new Intent(Intent.ACTION_PICK);
                intentd.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intentd, 400);
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String writedate = date_text.getText().toString();
                String mornig = m_food_menu.getText().toString();
                String lunch = l_food_menu.getText().toString();
                String dinner = d_food_menu.getText().toString();
                String content = food_content.getText().toString();
                String imgpath1 = imgFilePath1;
                String imgpath2 = imgFilePath2;
                String imgpath3 = imgFilePath3;

                // 이 정보를 비동기 Task 로 넘겨 서버에게 전달한다.
                FoodATask FoodATask = new FoodATask("foodMupdate", writedate ,loginDTO.getId(), mornig, lunch, dinner, content, imgpath1, imgpath2, imgpath3);
                try {
                    FoodATask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "작성이 완료 되었습니다.", Toast.LENGTH_SHORT).show();

                frag_mfood = new FragMfood();
                fragmentManager = getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                MainActivity activity = (MainActivity) getActivity();

                fragmentTransaction.replace(R.id.main_frag,frag_mfood);
                fragmentTransaction.commit();
            }
        });

            return view;
        };


}
