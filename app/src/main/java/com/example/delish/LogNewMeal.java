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
    RelativeLayout currLayout;
    FoodInventoryDatabaseHelper foodInventoryDatabaseHelper;

    //input fields
    EditText getNumFoods;
    Button submitButton;

    //UI input storage
    List<Integer> foodIDs = new ArrayList<Integer>();
    int verticalPosition = 75;

    //properties
    int numFoods;
    Map<Food, Integer> foods = new HashMap<Food, Integer>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_log_new_meal);

        currLayout = (RelativeLayout)findViewById(R.id.layout_log_new_meal);
        foodInventoryDatabaseHelper = FoodInventoryDatabaseHelper.getInstance(this);

        getNumFoods = (EditText)findViewById(R.id.get_num_foods);
        submitButton = (Button)findViewById(R.id.submit_meal_button);
    }

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
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(convertToPx(400), convertToPx(50));
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

    private int convertToPx(int dp) {
        return (int)(dp * this.getResources().getDisplayMetrics().density);
    }

    public void submitMeal(View view) throws Exception {
        foods.clear();
        for (int id : foodIDs) {
            String[] inputs = ((EditText)findViewById(id)).getText().toString().toLowerCase().split(",");
            if(inputs.length != 2) {
                ((EditText)findViewById(id)).setError("Invalid input.");
                return;
            }
            String name = inputs[0].trim();
            int amount = Integer.parseInt(inputs[1].trim());
            if(foodInventoryDatabaseHelper.contains(name)) {
                foods.put(foodInventoryDatabaseHelper.getFromName(name), amount);
            } else {
                ((EditText)findViewById(id)).setError("Could not find " + name);
                return;
            }
        }
        //TODO: maybe need to take date input?
        //TODO: how do we want to store meals? As a map? list?
        getNumFoods.getText().clear();
        resetFields();
    }

    public void toHomePage(View view) {
        resetFields();
        finish();
    }

    public void toAddFood(View view) {
        Intent switchActivityIntent = new Intent(LogNewMeal.this, AddFood.class);
        startActivity(switchActivityIntent);
    }

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
