package com.example.myteamcproject.Mypage;

import static com.example.myteamcproject.Common.CommonMethod.MyattList;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myteamcproject.ATask.MemberATask;
import com.example.myteamcproject.Mypage.deco.AttrDecorator;
import com.example.myteamcproject.Mypage.deco.EventDecorator;
import com.example.myteamcproject.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarUtils;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class FrageMyAttr extends Fragment {

    private static final String TAG = "FrageMyAttr";

    MaterialCalendarView calendarView;
    Button btn_Myattr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_attrcalander, container, false);

        loadAttList();

        calendarView = view.findViewById(R.id.attr_calendarView);
        calendarView.setSelectedDate(CalendarDay.today());
        Log.d(TAG, "CalendarDay: " + CalendarDay.today().toString());

        Log.d(TAG, "onCreateView: " + MyattList.size());

        for(int i = 0; i < MyattList.size(); i++){
                String[] date = MyattList.get(i).getAtt_Date().toString().split("-");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]) - 1;
                int day = Integer.parseInt(date[2]);

                calendarView.addDecorators(
                        new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(year, month, day)))
                );
        }//for

        calendarView.addDecorators(
                new WeekDecorator(),
                new SaturDecorator()
        );

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        btn_Myattr = view.findViewById(R.id.btn_Myattr);
        btn_Myattr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to_day = CalendarDay.today().getYear() + "-" + (CalendarDay.today().getMonth() + 1) + "-" + CalendarDay.today().getDay();
                //Log.d(TAG, "onClick: " + MyattList.get(MyattList.size() - 1).getAtt_Date());
                //Log.d(TAG, "onClick: " + CalendarDay.today().getYear() + "-" + (CalendarDay.today().getMonth() + 1) + "-" + CalendarDay.today().getDay());
                //Log.d(TAG, "onClick: " + MyattList.get(MyattList.size() - 1).getAtt_Date().equals(to_day));
                if(!MyattList.get(MyattList.size() - 1).getAtt_Date().equals(to_day)){
                    MemberATask aTask = new MemberATask("AttrInsert", loginDTO.getId());

                    String result = null;
                    try {
                        result = aTask.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }//try & catch

                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                    calendarView.addDecorators(
                            new EventDecorator(Color.RED, Collections.singleton(CalendarDay.today()))
                    );
                    loadAttList();
                }else{
                    Toast.makeText(getActivity(), "이미 출석체크를 완료하셨습니다", Toast.LENGTH_SHORT).show();
                }//if
            }//onClick
        });//btn_Myattr.setOnClickListener

        return view;
    }

    public void loadAttList(){
        MemberATask aTask = new MemberATask("AttrSelect", loginDTO.getId());

        try {
            aTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }//loadAttList()
}
