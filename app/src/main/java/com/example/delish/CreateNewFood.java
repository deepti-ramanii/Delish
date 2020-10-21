package com.example.delish;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

public class CreateNewFood extends AppCompatActivity {
    EditText getFoodNameInput;
    String foodName;

    EditText getNumServingsInput;
    int numServings;

    EditText getNumIngredientsInput;
    int numIngredients;

    List<Integer> ingredientNameInputs = new ArrayList<Integer>();
    List<String> ingredients = new ArrayList<String>();

    LinearLayout currLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_new_food);

        //get name of new food
        getFoodNameInput = (EditText)findViewById((R.id.get_food_name));

        //get number of servings in new food
        getNumServingsInput = (EditText)findViewById((R.id.get_num_servings));

        //get ingredients of new food
        getNumIngredientsInput = (EditText)findViewById(R.id.get_num_ingredients);

        currLayout = (LinearLayout)findViewById(R.id.fragment_create_new_food);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setNumIngredients(View view) {
        removeFields();
        ingredientNameInputs.clear();

        numIngredients = Integer.parseInt(getNumIngredientsInput.getText().toString());

        EditText temp;
        for(int i = 0; i < numIngredients; i++) {
            temp = new EditText(this);
            int id = View.generateViewId();
            temp.setId(id);
            temp.setHint("Enter ingredient");
            ingredientNameInputs.add(id);
            currLayout.addView(temp);
        }
    }

    public void submitFood(View view) throws Exception {
        foodName = getFoodNameInput.getText().toString().toLowerCase();
        numServings = Integer.parseInt(getNumServingsInput.getText().toString());

        ingredients.clear();
        for(int id : ingredientNameInputs) {
            String ingredient = ((EditText)findViewById(id)).getText().toString().toLowerCase();
            if(WriteToXML.nodeExists("//food[name='" + ingredient + "']")) {
                ingredients.add(ingredient);
            } else {
                //TODO: prompt the user to re-input using a simpler visual cue
                Toast.makeText(this, ingredient + " is not listed as a valid food item.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Food newFood = new Food(foodName, numServings, ingredients);
        Log.d("DEBUG", "food: " + foodName + ", calories: " + newFood.getCaloriesPerServing());
        WriteToXML.writeNewFoodToXml(newFood);
        removeFields();
        finish();   //return to LogNewMeal()
    }

    private void removeFields() {
        for(int id : ingredientNameInputs) {
            if((EditText)findViewById(id) != null) {
                currLayout.removeView((EditText)findViewById(id));
            }
        }
    }
}
