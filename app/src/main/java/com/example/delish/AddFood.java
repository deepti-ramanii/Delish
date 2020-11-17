package com.example.delish;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    Map<Food, Integer> ingredients = new HashMap<Food, Integer>();

    LinearLayout currLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_food);

        getFoodName = (EditText)findViewById((R.id.get_food_name));
        getNumServings = (EditText)findViewById((R.id.get_num_servings));
        getNumIngredients = (EditText)findViewById(R.id.get_num_ingredients);

        currLayout = (LinearLayout)findViewById(R.id.layout_add_food);
    }

    //creates a variable number of fields for the user to input ingredients of the food item
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setIngredientFields(View view) {
        //reset the previous inputs
        removeFields();
        ingredientIDs.clear();

        //create the necessary number of fields to store ingredient inputs
        numIngredients = Integer.parseInt(getNumIngredients.getText().toString());
        for(int i = 0; i < numIngredients; i++) {
            EditText temp = new EditText(this);
            int id = View.generateViewId();
            temp.setId(id);
            temp.setHint("Enter ingredient name and amount (i.e: egg, 2)");
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
            //format and separate the input for each ingredient into a name and an amount
            String input = ((EditText)findViewById(id)).getText().toString().toLowerCase();
            String[] inputs = input.split(",");
            inputs[0] = inputs[0].trim();
            inputs[1] = inputs[1].trim();

            //if the ingredient is valid, add it to the list of ingredients, otherwise prompt the user to re-input
            if(WriteToXML.nodeExists("//food[name='" + inputs[0] + "']")) {
                Food ingredient = WriteToXML.getFoodFromName(inputs[0]);
                ingredients.put(ingredient, Integer.parseInt(inputs[1]));
            } else {
                //TODO: prompt user to re-input the ingredient
                return;
            }
        }

        //use the newly inputted information to create a new food item and store it in the xml
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
