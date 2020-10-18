package com.example.delish;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

public class CreateNewFood extends AppCompatActivity {
    EditText getFoodNameInput;
    String foodName;

    EditText getNumServingsInput;
    int numServings;

    Button getNumIngredientsButton;
    EditText getNumIngredientsInput;
    int numIngredients;

    Button submitFoodButton;
    List<EditText> ingredientNameInputs = new ArrayList<EditText>();
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
        getNumIngredientsButton = (Button)findViewById(R.id.get_num_ingredients_button);

        //submit all inputs
        submitFoodButton = (Button)findViewById(R.id.submit_food_button);

        currLayout = (LinearLayout)findViewById(R.id.fragment_create_new_food);
    }

    public void setNumIngredients(View view) {
        numIngredients = Integer.parseInt(getNumIngredientsInput.getText().toString());
        ingredientNameInputs.clear();
        removeFields();

        EditText temp;
        for(int i = 0; i < numIngredients; i++) {
            temp = new EditText(this);
            temp.setId(i);
            temp.setHint("Enter ingredient " + (i + 1));
            ingredientNameInputs.add(temp);
            currLayout.addView(temp);
        }
    }

    public void submitFood(View view) throws ParserConfigurationException, SAXException, IOException {
        foodName = getFoodNameInput.getText().toString().toLowerCase();
        numServings = Integer.parseInt(getNumServingsInput.getText().toString());

        ingredients.clear();
        for(int i = 0; i < numIngredients; i++) {
            String ingredient = ingredientNameInputs.get(i).getText().toString().toLowerCase();
            if(true) {
                ingredients.add(ingredient);
            } else {
                Toast.makeText(this, ingredient + " is not listed as a valid food item.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Food newFood = new Food(foodName, numServings, ingredients);

        //try adding the new food to the xml file
        boolean successful = WriteToXML.writeNewFoodToXml(newFood);
        //if the food already exists, exit
        if(!successful) {
            Toast.makeText(this, newFood.getName() + " already exists.", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Successfully added " + newFood.getName(), Toast.LENGTH_SHORT).show();
        removeFields();
    }

    public void toHomePage(View view) {
        removeFields();
        finish();
    }

    private void removeFields() {
        for(int i = 0; i < numIngredients; i++) {
            if((EditText)findViewById(i) != null) {
                currLayout.removeView((EditText) findViewById(i));
            }
        }
    }
}
