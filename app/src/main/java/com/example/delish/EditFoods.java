package com.example.delish;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditFoods extends AppCompatActivity {
    Button getFoodNameButton;
    EditText getFoodNameInput;

    Button addIngredientButton;
    Button removeIngredientButton;
    EditText getIngredientToChangeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_foods);

        getFoodNameButton = (Button)findViewById(R.id.get_food_name_button);
        getFoodNameInput = (EditText) findViewById(R.id.get_food_name);

        addIngredientButton = (Button)findViewById(R.id.add_ingredient_button);
        removeIngredientButton = (Button)findViewById(R.id.remove_ingredient_button);
        getIngredientToChangeInput = (EditText)findViewById(R.id.get_ingredient_to_change);
    }

    public void checkIfFoodExists(View view) {
        String foodName = getFoodNameInput.getText().toString();
        //TODO: check if the name exists in the xml inventory and set exists accordingly
            //set foodToChange to whatever it should be
            addIngredientButton.setVisibility(View.VISIBLE);
            removeIngredientButton.setVisibility(View.VISIBLE);
            getIngredientToChangeInput.setVisibility(View.VISIBLE);
        //otherwise
            Toast.makeText(this, foodName + " is not listed as a valid food item.", Toast.LENGTH_SHORT);
    }

    public void addIngredient(View view) {
        String ingredientToChange = getIngredientToChangeInput.getText().toString();
        //find the food corresponding to name & get the ingredients list
        if(containsIngredient(ingredientToChange)) {
            //add
        }
    }

    public void removeIngredient(View view) {
        String ingredientToChange = getIngredientToChangeInput.getText().toString();
        //find the food corresponding to name & get the ingredients list
        if(containsIngredient(ingredientToChange)) {
            //remove
        }
    }

    public boolean containsIngredient(String ingredient) {
        //find the food corresponding to name & get the ingredients list
        //if the ingredient is in the list
            //return true
        //otherwise
            Toast.makeText(this, ingredient + " is not a valid ingredient.", Toast.LENGTH_SHORT);
            return false;
    }
}
