package com.example.myteamcproject.Gift;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.Common.FragHome;
import com.example.myteamcproject.R;

public class FragGift_buy extends Fragment {

    private static final String TAG = "FragGift_buy";

    ImageView giftImg;
    TextView giftName;
    Button btnHome, btnShop;

    GiftDTO dto;

    FragmentManager fragmentManager;



    public FragGift_buy(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.frag_gift_buy, container, false);
        Bundle bundle = getArguments();

        if(bundle != null) {
            dto = (GiftDTO) bundle.getSerializable("dto");
            Log.d(TAG, "onCreateView: " + dto.getGs_name());

            giftImg = viewGroup.findViewById(R.id.giftImg);
            giftName = viewGroup.findViewById(R.id.giftName);
            btnHome = viewGroup.findViewById(R.id.btnHome);
            btnShop = viewGroup.findViewById(R.id.btnShop);

            giftName.setText(dto.getGs_name());

            String filepath = ipConfig + "/resources/"  + dto.getGs_filepath() + dto.getGs_filename();
            Log.d(TAG, "setDto: " + filepath);
            Glide.with(this).load(filepath).into(giftImg);

            btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: 클릭함");
                FragGift fragGift = new FragGift(fragmentManager);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dto", dto);
                fragmentManager.beginTransaction().replace(R.id.main_frag, fragGift).commit();

            }
            });

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: 클릭함");
                    FragHome fragHome = new FragHome();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dto", dto);
                    fragmentManager.beginTransaction().replace(R.id.main_frag, fragHome).commit();


                }
            });
        }
        return viewGroup;
    }
}



