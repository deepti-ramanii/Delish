package com.example.delish;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class LogNewMeal extends AppCompatActivity {
    Button getNumFoodsButton;
    EditText getNumFoodsInput;
    int numFoods;

    Button submitMealButton;
    List<EditText> foodNameInputs = new ArrayList<EditText>();
    List<String> foodNames = new ArrayList<String>();

    LinearLayout currLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_log_new_meal);

        getNumFoodsButton = (Button)findViewById(R.id.get_num_foods_button);
        getNumFoodsInput = (EditText)findViewById(R.id.get_num_foods);
        submitMealButton = (Button)findViewById(R.id.submit_meal_button);

        currLayout = (LinearLayout)findViewById(R.id.fragment_log_new_meal);
    }

    public void setNumFoods(View view) {
        numFoods = Integer.parseInt(getNumFoodsInput.getText().toString());
        foodNameInputs.clear();
        removeFields();

        EditText temp;
        for(int i = 0; i < numFoods; i++) {
            temp = new EditText(this);
            temp.setId(i);
            temp.setHint("Enter food item " + (i + 1));
            foodNameInputs.add(temp);
            currLayout.addView(temp);
        }
    }

    public void submitMeal(View view) {
        foodNames.clear();
        for (int i = 0; i < numFoods; i++) {
            String foodName = foodNameInputs.get(i).getText().toString().toLowerCase();
            if(true) {
                foodNames.add(foodName);
            } else {
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
        for(int i = 0; i < numFoods; i++) {
            if((EditText)findViewById(i) != null) {
                currLayout.removeView((EditText) findViewById(i));
            }
        }
    }
}
