package ua.dashan.caloriescounter;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;

import ua.dashan.caloriescounter.Database.DatabaseHelpher;


public class CalendarFragment extends Fragment implements OnDateSelectedListener {
private MaterialCalendarView calendarView;
    private TextView textDate;
    DatabaseHelpher helpher;




    private static final java.text.DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView=(MaterialCalendarView)v.findViewById(R.id.calendarView);
        textDate=(TextView)v.findViewById(R.id.textDate);
        CalendarDay date = new CalendarDay();
        calendarView.setOnDateChangedListener(this);
        helpher=new DatabaseHelpher(getActivity());

        return v;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        textDate.setText(FORMATTER.format(date.getDate())+" "+helpher.getCaloriesCountForCalendar(FORMATTER.format(date.getDate()))+" каллорий съели" );

    }
}