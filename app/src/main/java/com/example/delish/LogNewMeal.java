package com.example.delish;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class LogNewMeal extends AppCompatActivity {
    EditText getNumFoods;
    int numFoods;
    List<Integer> foodNameIDs = new ArrayList<Integer>();
    List<String> foodNames = new ArrayList<String>();

    LinearLayout currLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_log_new_meal);

        getNumFoods = (EditText)findViewById(R.id.get_num_foods);
        currLayout = (LinearLayout)findViewById(R.id.fragment_log_new_meal);
    }

    //create a variable number of fields to store the user's food inputs for one meal
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setNumFoods(View view) {
        //reset the previous inputs
        removeFields();
        foodNameIDs.clear();

        //get the number of food inputs and create that many input fields
        numFoods = Integer.parseInt(getNumFoods.getText().toString());
        for(int i = 0; i < numFoods; i++) {
            EditText temp = new EditText(this);
            int id = View.generateViewId();
            temp.setId(id);
            temp.setHint("Enter food item");
            foodNameIDs.add(id);
            currLayout.addView(temp);
        }
    }

    //store the newly inputted meal
    public void submitMeal(View view) {
        foodNames.clear();
        for (int id : foodNameIDs) {
            String foodName = ((EditText)findViewById(id)).getText().toString().toLowerCase();
            if(true /* food exists */) {  //TODO: check if foodName is a valid food
                foodNames.add(foodName);
            } else {
                //TODO: open up a dialogue box that asks the user to go to AddFood();
                Toast.makeText(this, foodName + " is not listed as a valid food item.", Toast.LENGTH_SHORT);
                return;
            }
        }
        removeFields();
    }

    public void toHomePage(View view) {
        removeFields();
        finish();
    }

    public void toCreateNewFood(View view) {
        Intent switchActivityIntent = new Intent(LogNewMeal.this, AddFood.class);
        startActivity(switchActivityIntent);
    }

    //remove food name fields to prepare for new input
    private void removeFields() {
        for(int id : foodNameIDs) {
            if((EditText)findViewById(id) != null) {
                currLayout.removeView((EditText) findViewById(id));
            }
        }
        foodNameIDs.clear();
    }
}
