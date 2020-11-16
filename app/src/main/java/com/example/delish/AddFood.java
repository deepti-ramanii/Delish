package com.example.delish;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFood extends AppCompatActivity {
    EditText getFoodName;
    String foodName;

    EditText getNumServings;
    int numServings;

    EditText getNumIngredients;
    int numIngredients;

    List<Integer> ingredientIDs = new ArrayList<Integer>();
    Map<String, Integer> ingredients = new HashMap<String, Integer>();

    LinearLayout currLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_new_food);

        getFoodName = (EditText)findViewById((R.id.get_food_name));
        getNumServings = (EditText)findViewById((R.id.get_num_servings));
        getNumIngredients = (EditText)findViewById(R.id.get_num_ingredients);

        currLayout = (LinearLayout)findViewById(R.id.fragment_create_new_food);
    }

    //creates a variable number of fields for the user to input ingredients of the food item
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setNumIngredients(View view) {
        //reset the previous inputs
        removeFields();
        ingredientIDs.clear();

        //create the necessary number of fields to store ingredient inputs
        numIngredients = Integer.parseInt(getNumIngredients.getText().toString());
        for(int i = 0; i < numIngredients; i++) {
            EditText temp = new EditText(this);
            int id = View.generateViewId();
            temp.setId(id);
            temp.setHint("Enter ingredient name and amount (i.e: apple, 2)");
            ingredientIDs.add(id);
            currLayout.addView(temp);
        }
    }

    //stores the user's input information in the xml file
    public void submitFood(View view) throws Exception {
        //reset the previous ingredients list
        ingredients.clear();

        //get the user's input for the name of the food item and the number of servings given by the listed ingredients
        foodName = getFoodName.getText().toString().toLowerCase();
        numServings = Integer.parseInt(getNumServings.getText().toString());

        //get the user's input for the ingredients that make up the food item
        for(int id : ingredientIDs) {
            String input = ((EditText)findViewById(id)).getText().toString().toLowerCase();
            String[] ingredient = input.split(",");
            if(WriteToXML.nodeExists("//food[name='" + ingredient[0].trim() + "']")) {
                ingredients.put(ingredient[0].trim(), Integer.parseInt(ingredient[1].trim()));
            } else {
                //TODO: prompt the user to re-input using a simpler visual cue
                Toast.makeText(this, ingredient + " is not listed as a valid food item.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //use the given information to create a new food item and store it in the xml
        Food newFood = new Food(foodName, numServings, ingredients);
        WriteToXML.writeNewFoodToXml(newFood);
        removeFields();
        //TODO: return to LogNewMeal() when done adding the new food
    }

    //remove the ingredient fields to prepare for new input
    private void removeFields() {
        for(int id : ingredientIDs) {
            if((EditText)findViewById(id) != null) {
                currLayout.removeView((EditText)findViewById(id));
            }
        }
        ingredientIDs.clear();
    }
}
