package com.example.myteamcproject.Mypage;

import static com.example.myteamcproject.Common.CommonMethod.foodlist;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myteamcproject.ATask.FoodATask;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class FragMfood extends Fragment {
    private static final String TAG = "main:FragMfood";

    private View view;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragMfoodwrite frag_mfoodwrite;
    String m_writedate;
    String foodschdul = "false";
    Button food_write_btn;
    CalendarView my_calendar;
    //https://gameprograming.tistory.com/152

    long mNow;
    Date mDate;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy"+ "-" +"MM" + "-" + "dd");

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_mfood, container, false);
        Log.d("FragMfood", "onCreateView: ");

        FoodATask foodATask = new FoodATask("mAlllist", loginDTO.getId());

        try {
            foodATask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        view.findViewById(R.id.food_write_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                m_writedate = getTime();

                FoodATask foodATask = new FoodATask("mAlllist", loginDTO.getId());

                try {
                    foodATask.execute().get();
                } catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }

                Log.d(TAG, "onClick: " +m_writedate + ", " + foodlist.get(0).getWritedate().split(" ")[0] );
                
                for(int i = 0; i < foodlist.size(); i++){

                    Log.d(TAG, "onClick: " +m_writedate + ", " + foodlist.get(i).getWritedate().split(" ")[0] );

                    if(foodlist.get(i).getWritedate().split(" ")[0].equals(m_writedate)) {
                        foodschdul = "true";
                        break;
                    }//if
                }//for
                if(foodschdul.equals("false")){
                    frag_mfoodwrite = new FragMfoodwrite();
                    fragmentManager = getParentFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();

                    MainActivity activity = (MainActivity) getActivity();

                    fragmentTransaction.replace(R.id.main_frag,frag_mfoodwrite);
                    fragmentTransaction.commit();

                }else {
                    Toast.makeText(getActivity(), "이미 작성되있습니다.", Toast.LENGTH_SHORT).show();
                }//if
            }
        });



        my_calendar = view.findViewById(R.id.my_calendar);

        my_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

                m_writedate = year + "/" + month + "/" + day;

               // Toast.makeText(getActivity(), year + "," + month + "," + day, Toast.LENGTH_SHORT).show();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragMfooddate fragMfooddate = new FragMfooddate(year, month, day);
                transaction.replace(R.id.main_frag, fragMfooddate);
                transaction.commit();


            }
        });



        return view;
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return sdf.format(mDate);
    }

}
