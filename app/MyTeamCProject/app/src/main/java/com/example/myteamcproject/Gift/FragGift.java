package com.example.myteamcproject.Gift;

import static com.example.myteamcproject.Common.CommonMethod.gflist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myteamcproject.ATask.GiftATask;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragGift extends Fragment {

    //log용 TAG
    private View view;
    private static final String TAG = "FragGift";

    //그리드뷰와 어뎁터 그리고 담을 arrayList 변수 선언
    GridView gridView_gift;
    GiftAdapter giftAdapter;
    ArrayList<GiftDTO> dtos;

    //자신이 활동하는 Activity 찾기
    MainActivity activity;
    FragmentManager fragmentManager;

    public FragGift(){

    }

    public FragGift(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    //프래그먼트를 인플레이트로 연결
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.frag_gift, container, false);
        Log.d("FragGift", "onCreateView:");

    //프래그먼트에서 액티비티에 있는 객체를 사용하기 위한 액티비티 변수 선언 후 액티비티를 저장
    activity = (MainActivity) getActivity();

    //DB와 연동하기 위한 Atask 객체 선언
      GiftATask giftATask = new GiftATask();

      //Atask 실행 후 값을 가져온다.

        try {
            giftATask.execute().get();
        }catch (ExecutionException e) {
            e.getMessage();
        }catch (InterruptedException e) {
            e.getMessage();
        }

    //어댑터에 보내기 위해 위에서 선언한 arraylist 객체 선언
        dtos = new ArrayList<>();

        //fraghome에 있는 recyclerView_ext 리사이클러뷰를 담는 다
        gridView_gift = viewGroup.findViewById(R.id.gridgift);
        
        // 레이아웃 매니저 생성

        // 어댑터 객체를 생성하고 arraylist와 액티비티를 넘겨준다
        giftAdapter = new GiftAdapter(activity, dtos);

        // 어댑터에 ArrayList에 dto를 추가한다.
        for(int i = 0; i <= gflist.size() -1; i++){
           giftAdapter.addDto(gflist.get(i));
        }//for

        // 만든 어댑터를 리사이클러 뷰에 붙인다
        gridView_gift.setAdapter(giftAdapter);

        gridView_gift.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                GiftDTO dto = giftAdapter.getItem(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dto", dto);

                fragmentManager = getParentFragmentManager();
                FragGift_detail fragGift_detail = new FragGift_detail(fragmentManager);
                fragGift_detail.setArguments(bundle);


            fragmentManager.beginTransaction().replace(R.id.main_frag, fragGift_detail).commit();

            }
        });

      /*  //화면 제목 설정
        TextView view = getActivity().findViewById(R.id.textView);
        view.setText("GIFT SHOP");*/


        return viewGroup;

    }

}
