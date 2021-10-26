package com.example.myteamcproject.Mypage.deco;

import android.graphics.Color;
import android.util.Log;

import com.example.myteamcproject.Mypage.AttendanceDTO;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AttrDecorator implements DayViewDecorator {

    private static final String TAG = "AttrDecorator";

    Date parse;

    public AttrDecorator(Date parse) {
        this.parse = parse;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(parse);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, Color.RED));
    }
}
