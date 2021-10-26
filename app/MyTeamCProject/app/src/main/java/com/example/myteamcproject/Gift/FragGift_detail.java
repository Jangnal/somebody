package com.example.myteamcproject.Gift;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.ATask.CartAtask;
import com.example.myteamcproject.ATask.GiftATask;
import com.example.myteamcproject.ATask.Gift_userATask;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.Mypage.FragCartList;
import com.example.myteamcproject.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragGift_detail extends Fragment {

    private static final String TAG = "FragGift_detail";

    GiftDTO dto;
    TextView giftName, giftPoint;
    ImageView giftImg;
    Button btnBuy, btnBack, btnCart;

    //그리드뷰와 어뎁터 그리고 담을 arrayList 변수 선언
    GridView gridView_gift;
    GiftAdapter giftAdapter;
    ArrayList<GiftDTO> dtos;

    //자신이 활동하는 Activity 찾기
    MainActivity activity;
    FragmentManager fragmentManager;

    //이걸 통해 위 fragmentManager로 보낼수 있다.
    public FragGift_detail(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //프래그먼트를 인플레이트로 연결
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.frag_gift_detail, container, false);
        Log.d("FragGift_detail", "onCreateView: ");

        //프래그먼트에서 액티비티에 있는 객체를 사용하기 위한 액티비티 변수 선언 후 액티비티를 저장
        activity = (MainActivity) getActivity();

        //DB와 연동하기 위한 Atask객체 선언
        GiftATask giftATask = new GiftATask();

        //Atask 실행 후 값을 가져온다.

        try {
            giftATask.execute().get();
        }catch (ExecutionException e){
            e.getMessage();
        }catch (InterruptedException e){
            e.getMessage();
        }

        //어댑터에 보내기 위해 위에서 선언한 arraylist 객체 선언
        dtos = new ArrayList<>();

        //fraghome에 있는 그리드뷰를 담는다
        gridView_gift = viewGroup.findViewById(R.id.gridgift);
        btnCart = viewGroup.findViewById(R.id.btnCart);

        Bundle bundle = getArguments();

        if(bundle != null) {
            dto = (GiftDTO) bundle.getSerializable("dto");
            Log.d(TAG, "onCreateView: " + dto.getGs_name());

            giftImg = viewGroup.findViewById(R.id.giftImg);
            giftName = viewGroup.findViewById(R.id.giftName);
            giftPoint = viewGroup.findViewById(R.id.giftPoint);
            btnBuy = viewGroup.findViewById(R.id.btnBuy);
            btnBack = viewGroup.findViewById(R.id.btnBack);

            giftName.setText(dto.getGs_name());
            String filepath = ipConfig + "/resources/" + dto.getGs_filepath() + dto.getGs_filename();
            Log.d(TAG, "setDto: " + filepath);
            Glide.with(this).load(filepath).into(giftImg);
            giftPoint.setText(String.valueOf(dto.getPonint()));


            //구매 버튼
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: 클릭됨");


                    Gift_userATask gift_userATask = new Gift_userATask("buy", dto.getGs_name());

                    try {
                        gift_userATask.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                    FragGift_buy fragGift_buy = new FragGift_buy(fragmentManager);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dto", dto);
                    fragGift_buy.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.main_frag, fragGift_buy).commit();


                }

            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: 클릭함");
                    FragGift fragGift = new FragGift(fragmentManager);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dto", dto);
                    fragmentManager.beginTransaction().replace(R.id.main_frag, fragGift).commit();


                }
            });

            btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //DB와 연동하기 위한 Atask 객체 선언
                    CartAtask cartAtask = new CartAtask("insert", loginDTO.getId(), dto.getGs_name(), dto.getPonint());

                    //Atask 실행 후 값을 가져온다.

                    try {
                        cartAtask.execute().get();
                    }catch (ExecutionException e) {
                        e.getMessage();
                    }catch (InterruptedException e) {
                        e.getMessage();
                    }

                    Log.d(TAG, "onClick: 클릭함");
                    FragCartList fragCart = new FragCartList(fragmentManager);
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("dto", dto);
                    Toast.makeText(activity, "찜되었습니다", Toast.LENGTH_SHORT).show();

                    //fragmentManager.beginTransaction().replace(R.id.main_frag, fragCart).commit();

                }
            });



        }

        return viewGroup;
    }
}
