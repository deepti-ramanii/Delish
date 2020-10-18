package com.example.delish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

        EditText temp;
        for(int i = 0; i < numFoods; i++) {
            temp = new EditText(this);
            temp.setId(i);
            temp.setHint("Enter food item " + (i + 1));
            foodNameInputs.add(temp);
        }
    }

    public void submitMeal(View view) {
        foodNames.clear();
        for (int i = 0; i < numFoods; i++) {
            foodNames.add(foodNameInputs.get(i).getText().toString().toLowerCase());
        }
    }
}
