package com.example.delish;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    private CalendarView calendar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        calendar = (CalendarView)findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Intent switchActivityIntent = new Intent(Home.this, ManageMeals.class);
                switchActivityIntent.putExtra("DAY", dayOfMonth);
                switchActivityIntent.putExtra("MONTH", month);
                switchActivityIntent.putExtra("YEAR", year);
                startActivity(switchActivityIntent);
            }
        });
    }
}