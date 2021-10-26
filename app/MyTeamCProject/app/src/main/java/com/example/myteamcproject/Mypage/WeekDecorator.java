package com.example.myteamcproject.Mypage;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

public class WeekDecorator implements DayViewDecorator {

    Calendar calander = Calendar.getInstance();
    int weekDay;

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calander);
        weekDay = calander.get(Calendar.DAY_OF_WEEK);
        return weekDay == Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}

