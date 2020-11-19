package com.example.delish;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddFood extends AppCompatActivity {
    private RelativeLayout currLayout;
    private FoodDatabaseHelper foodDatabaseHelper;

    //input fields
    private EditText getFoodName;
    private EditText getNumServings;
    private EditText getNumIngredients;
    private Button submitButton;

    //UI info storage
    private int verticalPosition = 125;
    private List<Integer> ingredientIDs = new ArrayList<Integer>();

    //properties
    private String foodName;
    private int numServings;
    private int numIngredients;
    private int numCalories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_food);
        currLayout = (RelativeLayout)findViewById(R.id.layout_add_food);
        foodDatabaseHelper = FoodDatabaseHelper.getInstance(this);

        getFoodName = (EditText)findViewById((R.id.get_food_name));
        getNumServings = (EditText)findViewById((R.id.get_num_servings));
        getNumIngredients = (EditText)findViewById(R.id.get_num_ingredients);
        submitButton = (Button)findViewById(R.id.submit_food_button);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setIngredientFields(View view) {
        resetFields();
        numIngredients = Integer.parseInt(getNumIngredients.getText().toString());
        for(int i = 0; i < numIngredients; i++) {
            int id = View.generateViewId();
            ingredientIDs.add(id);
            EditText ingredientField = new EditText(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(convertToPx(400), convertToPx(50));
            layoutParams.setMargins(0, convertToPx(verticalPosition), 0, 0);
            ingredientField.setLayoutParams(layoutParams);
            ingredientField.setId(id);
            ingredientField.setHint("Enter an ingredient and amount (i.e: egg, 1)");
            currLayout.addView(ingredientField);
            verticalPosition += 50;
        }
        ((RelativeLayout.LayoutParams)submitButton.getLayoutParams()).setMargins(0, convertToPx(verticalPosition), 0, 0);
    }

    private int convertToPx(int dp) {
        return (int)(dp * this.getResources().getDisplayMetrics().density);
    }

    public void submitFood(View view) throws Exception {
        foodName = getFoodName.getText().toString().toLowerCase().trim();
        numServings = Integer.parseInt(getNumServings.getText().toString().trim());
        for(int id : ingredientIDs) {
            String[] inputs = ((EditText)findViewById(id)).getText().toString().toLowerCase().split(",");
            if(inputs.length != 2) {
                ((EditText)findViewById(id)).setError("Invalid input.");
                break;
            }
            String ingredientName = inputs[0].trim();
            int ingredientAmount = Integer.parseInt(inputs[1].trim());
            if(foodDatabaseHelper.contains(ingredientName)) {
                Food ingredient = foodDatabaseHelper.getFromName(ingredientName);
                numCalories += ingredient.getCaloriesPerServing() * ingredientAmount;
            } else {
                ((EditText)findViewById(id)).setError("Could not find " + ingredientName);
                break;
            }
        }
        foodDatabaseHelper.insert(foodName, numCalories / numServings);
        Toast.makeText(this, "Added " + foodName + ".", Toast.LENGTH_SHORT).show();

        getFoodName.getText().clear();
        getNumIngredients.getText().clear();
        getNumServings.getText().clear();
        resetFields();
    }

    private void resetFields() {
        for(int id : ingredientIDs) {
            if((EditText)findViewById(id) != null) {
                currLayout.removeView((EditText)findViewById(id));
            }
        }
        ingredientIDs.clear();
        ((RelativeLayout.LayoutParams)submitButton.getLayoutParams()).setMargins(0, convertToPx(125), 0, 0);
        verticalPosition = 125;
    }
}
