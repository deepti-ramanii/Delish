package com.example.delish;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogNewMeal extends AppCompatActivity {
    EditText getNumFoods;
    int numFoods;
    List<Integer> foodIDs = new ArrayList<Integer>();
    Map<Food, Integer> foods = new HashMap<Food, Integer>();

    LinearLayout currLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_log_new_meal);

        getNumFoods = (EditText)findViewById(R.id.get_num_foods);
        currLayout = (LinearLayout)findViewById(R.id.layout_log_new_meal);
    }

    //create a variable number of fields to store the user's food inputs for one meal
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setFoodItemFields(View view) {
        //reset the previous inputs
        removeFields();
        foodIDs.clear();

        //get the number of food inputs and create that many input fields
        numFoods = Integer.parseInt(getNumFoods.getText().toString());
        for(int i = 0; i < numFoods; i++) {
            EditText temp = new EditText(this);
            int id = View.generateViewId();
            temp.setId(id);
            temp.setHint("Enter food name and amount (i.e: rice, 1)");
            foodIDs.add(id);
            currLayout.addView(temp);
        }
    }

    //store the newly inputted meal in a map
    public void submitMeal(View view) throws Exception {
        //reset any previously inputted foods
        foods.clear();

        //add the newly inputted foods to the list of foods in the meal
        for (int id : foodIDs) {
            //format and separate the input for each food item into a name and an amount
            String input = ((EditText)findViewById(id)).getText().toString().toLowerCase();
            String[] inputs = input.split(",");
            inputs[0] = inputs[0].trim();
            inputs[1] = inputs[1].trim();

            //if the food item is valid, add it to the list, otherwise prompt the user to add the food item
            if(WriteToXML.nodeExists("//food[name='" + inputs[0] + "']")) {
                Food food = WriteToXML.getFoodFromName(inputs[0]);
                foods.put(food, Integer.parseInt(inputs[1]));
            } else {
                return;
            }
        }

        //when we finish submitting a meal, remove the fields to prepare for new input
        removeFields();
    }

    public void toHomePage(View view) {
        removeFields();
        finish();
    }

    public void toAddFood(View view) {
        Intent switchActivityIntent = new Intent(LogNewMeal.this, AddFood.class);
        startActivity(switchActivityIntent);
    }

    //remove food name fields to prepare for new input
    private void removeFields() {
        for(int id : foodIDs) {
            if((EditText)findViewById(id) != null) {
                currLayout.removeView((EditText) findViewById(id));
            }
        }
        foodIDs.clear();
    }
}
