package com.example.delish;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class LogNewMeal extends AppCompatActivity {
    EditText getNumFoodsInput;
    int numFoods;

    List<Integer> foodNameInputs = new ArrayList<Integer>();
    List<String> foodNames = new ArrayList<String>();

    LinearLayout currLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_log_new_meal);

        getNumFoodsInput = (EditText)findViewById(R.id.get_num_foods);

        currLayout = (LinearLayout)findViewById(R.id.fragment_log_new_meal);
    }

    public void toCreateNewFood(View view) {
        Intent switchActivityIntent = new Intent(LogNewMeal.this, CreateNewFood.class);
        startActivity(switchActivityIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setNumFoods(View view) {
        removeFields();
        numFoods = Integer.parseInt(getNumFoodsInput.getText().toString());
        foodNameInputs.clear();

        EditText temp;
        for(int i = 0; i < numFoods; i++) {
            temp = new EditText(this);
            int id = View.generateViewId();
            temp.setId(id);
            temp.setHint("Enter food item");
            foodNameInputs.add(id);
            currLayout.addView(temp);
        }
    }

    public void submitMeal(View view) {
        foodNames.clear();
        for (int id : foodNameInputs) {
            String foodName = ((EditText)findViewById(id)).getText().toString().toLowerCase();
            if(true) {  //TODO: check if foodName is a valid food
                foodNames.add(foodName);
            } else {
                //TODO: open up a dialogue box that asks the user to go to CreateNewFood();
                Toast.makeText(this, foodName + " is not listed as a valid food item.", Toast.LENGTH_SHORT);
                return;
            }
        }
        removeFields();
    }

    public void toHomePage(View view) {
        finish();
        removeFields();
    }

    private void removeFields() {
        for(int id : foodNameInputs) {
            if((EditText)findViewById(id) != null) {
                currLayout.removeView((EditText) findViewById(id));
            }
        }
    }
}
