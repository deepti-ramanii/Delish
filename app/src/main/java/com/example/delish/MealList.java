package com.example.delish;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

    private int day;
    private int month;
    private int year;
    private List<Integer> mealIDs = new ArrayList<Integer>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_meal_list);

        currLayout = (TableLayout)findViewById(R.id.fragment_meal_list);
        mealDatabaseHelper = MealDatabaseHelper.getInstance(this);
        foodDatabaseHelper = FoodDatabaseHelper.getInstance(this);

        date = (TextView)findViewById(R.id.date);

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
        resetMeals();
        Map<List<Food>, Integer> getMeals = mealDatabaseHelper.getFromDate(day, month, year);
        List<Integer> mealCalories = new ArrayList<Integer>();
        List<Food> foods = new ArrayList<Food>();
        for(List<Food> meal : getMeals.keySet()) {
            foods = meal;
            mealCalories.add(getMeals.get(meal));
            FoodListAdapter foodListAdapter = new FoodListAdapter(this, R.layout.adapter_view_layout, meals.get(i));
        }
    }

    private void resetMeals() {
        for(int id : mealIDs) {
            if((EditText)findViewById(id) != null) {
                currLayout.removeView((ListView)findViewById(id));
            }
        }
        mealIDs.clear();
    }

    /*
    public void submitMeal(View view) {
        String meal = "";
        for () {
            String name = ""; //get the name
            int amount = 0; //get the amount
            if(foodDatabaseHelper.contains(name)) {
                meal += (name + "," + amount + ";");
            } else {
                set error message
            }
        }
        mealDatabaseHelper.insert(meal, day, month, year);
        getNumFoods.getText().clear();
    }
    */
}