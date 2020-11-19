package com.example.delish;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageMeals extends AppCompatActivity {
    private MealDatabaseHelper mealDatabaseHelper;
    private FoodDatabaseHelper foodDatabaseHelper;

    private List<List<Food>> meals;
    private List<Integer> mealCalories;

    private TextView date;
    private int day, month, year;

    private LinearLayout manageFoodInputs;

    private EditText numFoodInputs;
    private List<Integer> foodInputIDs;
    private LinearLayout getNumFoods;
    private LinearLayout foodInputs;
    private LinearLayout mealSubmission;

    private ExpandableListView mealsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_manage_meals);
        mealDatabaseHelper = MealDatabaseHelper.getInstance(this);
        foodDatabaseHelper = FoodDatabaseHelper.getInstance(this);

        date = (TextView)findViewById(R.id.date);

        manageFoodInputs = (LinearLayout)findViewById(R.id.layout_manage_food_inputs);

        getNumFoods = (LinearLayout) manageFoodInputs.findViewById(R.id.layout_get_num_foods);
        numFoodInputs = (EditText)findViewById(R.id.num_foods);
        foodInputIDs = new ArrayList<Integer>();
        foodInputs = (LinearLayout) manageFoodInputs.findViewById(R.id.layout_food_inputs);
        mealSubmission = (LinearLayout) manageFoodInputs.findViewById(R.id.layout_meal_submission);

        mealsList = (ExpandableListView)findViewById(R.id.meals_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        day = getIntent().getIntExtra("DAY", 0);
        month = getIntent().getIntExtra("MONTH", 0);
        year = getIntent().getIntExtra("YEAR", 0);
        date.setText(String.format("%02d/%02d/%02d", day, month, year));

        Map<List<Food>, Integer> mealsFromDate = mealDatabaseHelper.getFromDate(day, month, year);
        mealCalories = new ArrayList<Integer>();
        meals = new ArrayList<List<Food>>();
        for(List<Food> meal : mealsFromDate.keySet()) {
            meals.add(meal);
            mealCalories.add(mealsFromDate.get(meal));
        }
        displayMeals();
    }

    private void displayMeals() {
        FoodListAdapter foodListAdapter = new FoodListAdapter(mealCalories, meals);
        foodListAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        mealsList.setAdapter(foodListAdapter);
    }

    public void submitMeal(View view) {
        String meal = "";
        List<Food> tempMeal = new ArrayList<Food>();
        int tempMealCalories = 0;
        for(int id : foodInputIDs) {
            String[] input = ((EditText)findViewById(id)).getText().toString().toLowerCase().split(",");
            String name = input[0].trim();
            int amount = Integer.parseInt(input[1].trim());
            if(foodDatabaseHelper.contains(name)) {
                meal += name + "," + amount + ";";
                Food food = foodDatabaseHelper.getFromName(name);
                food.setAmount(amount);
                tempMeal.add(food);
                tempMealCalories += food.getTotalCalories();
            } else {
                ((EditText)findViewById(id)).setError("Could not find " + name + ". Would you like to add it?");
            }
        }
        meals.add(tempMeal);
        mealCalories.add(tempMealCalories);
        mealDatabaseHelper.insert(meal, day, month, year);
        displayMeals();
    }

    public void cancelMealSubmission(View view) {
        removeFoodInputFields();
        getNumFoods.setVisibility(View.INVISIBLE);
        mealSubmission.setVisibility(View.INVISIBLE);
    }

    public void displayMealInputFields(View view) {
        getNumFoods.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void displayFoodInputFields(View view) {
        removeFoodInputFields();
        int numFoods = Integer.parseInt(numFoodInputs.getText().toString().toLowerCase().trim());
        for(int i = 0; i < numFoods; i++) {
            int id = View.generateViewId();
            foodInputIDs.add(id);
            EditText foodInput = new EditText(this);
            foodInput.setId(id);
            foodInput.setHint("Enter a food name and amount separated by a comma");
            foodInputs.addView(foodInput);
        }
        mealSubmission.setVisibility(View.VISIBLE);
    }

    private void removeFoodInputFields() {
        for(int id : foodInputIDs) {
            foodInputs.removeView((EditText)findViewById(id));
        }
        foodInputIDs.clear();
    }
}