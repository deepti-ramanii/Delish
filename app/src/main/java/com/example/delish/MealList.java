package com.example.delish;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MealList extends AppCompatActivity {
    private TableLayout currLayout;
    private FoodDatabaseHelper foodDatabaseHelper;
    private MealDatabaseHelper mealDatabaseHelper;

    private TextView date;
    private Button addMealButton;
    private ExpandableListView mealsList;

    private int day;
    private int month;
    private int year;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_meal_list);

        currLayout = (TableLayout)findViewById(R.id.fragment_meal_list);
        mealDatabaseHelper = MealDatabaseHelper.getInstance(this);
        foodDatabaseHelper = FoodDatabaseHelper.getInstance(this);

        date = (TextView)findViewById(R.id.date);
        mealsList = (ExpandableListView)findViewById(R.id.meals_list_view);
        addMealButton = (Button)findViewById(R.id.add_meal_button);
        addMealButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //TODO: open dialogue box to add a new meal
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onStart() {
        super.onStart();
        day = getIntent().getIntExtra("DAY", 0);
        month = getIntent().getIntExtra("MONTH", 0);
        year = getIntent().getIntExtra("YEAR", 0);
        date.setText(String.format("%02d/%02d/%02d", day, month, year));
        displayMeals();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void displayMeals() {
        Map<List<Food>, Integer> mealsFromDate = mealDatabaseHelper.getFromDate(day, month, year);
        List<Integer> mealCalories = new ArrayList<Integer>();
        List<List<Food>> meals = new ArrayList<List<Food>>();
        for(List<Food> meal : mealsFromDate.keySet()) {
            meals.add(meal);
            mealCalories.add(mealsFromDate.get(meal));
        }
        FoodListAdapter foodListAdapter = new FoodListAdapter(mealCalories, meals);
        foodListAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        mealsList.setAdapter(foodListAdapter);
    }
}