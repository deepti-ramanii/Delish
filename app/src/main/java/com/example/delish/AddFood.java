package com.example.delish;

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

public class AddFood extends AppCompatActivity {
    EditText getFoodName;
    String foodName;

    EditText getNumServings;
    int numServings;

    EditText getNumIngredients;
    int numIngredients;

    List<Integer> ingredientIDs = new ArrayList<Integer>();
    Map<Food, Integer> ingredients = new HashMap<Food, Integer>();

    Button submitButton;
    int verticalPosition = 125;

    RelativeLayout currLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_food);

        getFoodName = (EditText)findViewById((R.id.get_food_name));
        getNumServings = (EditText)findViewById((R.id.get_num_servings));
        getNumIngredients = (EditText)findViewById(R.id.get_num_ingredients);

        submitButton = (Button)findViewById(R.id.submit_food_button);

        currLayout = (RelativeLayout)findViewById(R.id.layout_add_food);
    }

    //creates a variable number of fields for the user to input ingredients of the food item
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setIngredientFields(View view) {
        //reset the previous inputs
        resetFields();

        //create the necessary number of fields to store ingredient inputs
        numIngredients = Integer.parseInt(getNumIngredients.getText().toString());
        for(int i = 0; i < numIngredients; i++) {
            //get a new id for the field
            int id = View.generateViewId();
            ingredientIDs.add(id);

            //set the field's properties
            EditText ingredientField = new EditText(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(convertToPx(300), convertToPx(50));
            layoutParams.setMargins(0, convertToPx(verticalPosition), 0, 0);
            ingredientField.setLayoutParams(layoutParams);
            ingredientField.setId(id);
            ingredientField.setHint("Enter an ingredient and amount (i.e: egg, 1)");

            //add the field to the layout
            currLayout.addView(ingredientField);
            verticalPosition += 50;
        }

        //move the submit button if necessary
        ((RelativeLayout.LayoutParams)submitButton.getLayoutParams()).setMargins(0, convertToPx(verticalPosition), 0, 0);
    }

    //converts a dp value to a px value
    private int convertToPx(int dp) {
        return (int)(dp * this.getResources().getDisplayMetrics().density);
    }

    //stores the user's input information in the xml file
    public void submitFood(View view) throws Exception {
        //reset the previous ingredients list
        ingredients.clear();

        //get the user's input for the name of the food item and the number of servings given by the listed ingredients
        foodName = getFoodName.getText().toString().toLowerCase().trim();
        numServings = Integer.parseInt(getNumServings.getText().toString().trim());

        //get the user's input for the ingredients that make up the food item
        for(int id : ingredientIDs) {
            //format and separate the input for each ingredient into a name and an amount
            String[] inputs = ((EditText)findViewById(id)).getText().toString().toLowerCase().split(",");
            if(inputs.length != 2) {
                ((EditText)findViewById(id)).setError("Please enter an ingredient and amount separated by a comma (i.e:cereal, 1)");
                return;
            }
            String name = inputs[0].trim();
            int amount = Integer.parseInt(inputs[1].trim());

            //if the ingredient is valid, add it to the list of ingredients, otherwise prompt the user to re-input
            if(WriteToXML.nodeExists("/food_inventory/food[@name='" + name + "']")) {
                ((EditText)findViewById(id)).setError(null);
                Food ingredient = WriteToXML.getFoodFromName(name);
                ingredients.put(ingredient, amount);
            } else {
                ((EditText)findViewById(id)).setError("Did not recognize " + name + ". Would you like to add it?");
                return;
            }
        }

        //use the newly inputted information to create a new food item and store it in the xml
        Food newFood = new Food(foodName, numServings, ingredients);
        WriteToXML.writeNewFoodToXml(newFood);

        //reset fields and user inputs to prepare for a new food item
        getFoodName.getText().clear();
        getNumIngredients.getText().clear();
        getNumServings.getText().clear();
        resetFields();
        //TODO: do we stay here or go back to LogNewMeal?
    }

    //resets the ingredient fields to prepare for new input
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
