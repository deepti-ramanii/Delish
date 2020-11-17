package com.example.delish;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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

    Button submitButton;
    int verticalPosition = 75;

    RelativeLayout currLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_log_new_meal);

        getNumFoods = (EditText)findViewById(R.id.get_num_foods);

        submitButton = (Button)findViewById(R.id.submit_meal_button);

        currLayout = (RelativeLayout)findViewById(R.id.layout_log_new_meal);
    }

    //create a variable number of fields to store the user's food inputs for one meal
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setFoodItemFields(View view) {
        //reset the previous inputs
        resetFields();

        //get the number of food inputs and create that many input fields
        numFoods = Integer.parseInt(getNumFoods.getText().toString().trim());
        for(int i = 0; i < numFoods; i++) {
            //get and store an id for the field
            int id = View.generateViewId();
            foodIDs.add(id);

            //set the field's properties
            EditText foodField = new EditText(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(convertToPx(300), convertToPx(50));
            layoutParams.setMargins(0, convertToPx(verticalPosition), 0, 0);
            foodField.setLayoutParams(layoutParams);
            foodField.setId(id);
            foodField.setHint("Enter a food item and amount (i.e: cereal, 1)");

            //add the field to the layout
            currLayout.addView(foodField);
            verticalPosition += 50;
        }

        //move submit button down as necessary
        ((RelativeLayout.LayoutParams)submitButton.getLayoutParams()).setMargins(0, convertToPx(verticalPosition), 0, 0);
    }

    //convert a dp value to a px value
    private int convertToPx(int dp) {
        return (int)(dp * this.getResources().getDisplayMetrics().density);
    }

    //store the newly inputted meal in a map
    public void submitMeal(View view) throws Exception {
        //reset any previously inputted foods
        foods.clear();

        //add the inputted foods to the list of foods in the meal
        for (int id : foodIDs) {
            //format and separate the input for each food item into a name and an amount
            String[] inputs = ((EditText)findViewById(id)).getText().toString().toLowerCase().split(",");
            if(inputs.length != 2) {
                ((EditText)findViewById(id)).setError("Please enter a food item and amount separated by a comma (i.e:cereal, 1)");
                return;
            }
            String name = inputs[0].trim();
            int amount = Integer.parseInt(inputs[1].trim());

            //if the food item is valid, add it to the list, otherwise prompt the user to add the food item
            if(WriteToXML.nodeExists("/food_inventory/food[@name='" + name + "']")) {
                ((EditText)findViewById(id)).setError(null);
                Food food = WriteToXML.getFoodFromName(name);
                foods.put(food, amount);
            } else {
                ((EditText)findViewById(id)).setError("Did not recognize '" + name + "'. Would you like to add it?");
                return;
            }
        }

        //when we finish submitting a meal, remove the fields and reset user input to prepare for the next submission
        getNumFoods.getText().clear();
        resetFields();
        //TODO: somehow store the new meal in a calendar-like format
    }

    public void toHomePage(View view) {
        resetFields();
        finish();
    }

    public void toAddFood(View view) {
        Intent switchActivityIntent = new Intent(LogNewMeal.this, AddFood.class);
        startActivity(switchActivityIntent);
    }

    //resets the food name fields to prepare for new input
    private void resetFields() {
        for(int id : foodIDs) {
            if((EditText)findViewById(id) != null) {
                currLayout.removeView((EditText) findViewById(id));
            }
        }
        foodIDs.clear();
        ((RelativeLayout.LayoutParams)submitButton.getLayoutParams()).setMargins(0, convertToPx(75), 0, 0);
        verticalPosition = 75;
    }
}
