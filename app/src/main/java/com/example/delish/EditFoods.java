package com.example.delish;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class EditFoods extends AppCompatActivity {
    Button getFoodNameButton;
    EditText getFoodNameInput;

    Button addIngredientButton;
    EditText newIngredientInput;
    Food foodToEdit;

    int numIngredients = 0;
    List<TextView> ingredientsList = new ArrayList<TextView>();
    List<Button> removeIngredientButtons = new ArrayList<Button>();
    LinearLayout currLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_foods);

        getFoodNameButton = (Button)findViewById(R.id.get_food_name_button);
        getFoodNameInput = (EditText) findViewById(R.id.get_food_name);

        addIngredientButton = (Button)findViewById(R.id.add_ingredient_button);
        newIngredientInput = (EditText)findViewById(R.id.new_ingredient);
        currLayout = (LinearLayout)findViewById(R.id.fragment_create_new_food);
    }

    public void checkIfFoodExists(View view) throws ParserConfigurationException, SAXException, IOException {
        String foodName = getFoodNameInput.getText().toString();

        Food newFood = WriteToXML.getFoodFromName(foodName);
        if(newFood == null) {
            Toast.makeText(this, foodName + " is not listed as a valid food item.", Toast.LENGTH_SHORT).show();
            return;
        }
        TextView tempText;
        Button tempButton;
        numIngredients = foodToEdit.getIngredients().size();

        for(int i = 0; i < numIngredients; i++) {
            tempText = new TextView(this);
            tempText.setId(Integer.parseInt("ingredient_at_" + i));
            tempText.setText(foodToEdit.getIngredients().get(i));

            tempButton = new Button(this);
            tempButton.setId(i);
            tempButton.setText("Remove");
            //tempButton.setOnClickListener(this::removeIngredient);

            currLayout.addView(tempText);
            currLayout.addView(tempButton);
            i++;
        }
        addIngredientButton.setVisibility(View.VISIBLE);
        newIngredientInput.setVisibility(View.VISIBLE);
    }

    public void addIngredient(View view) throws Exception {
        String ingredient = ((EditText)findViewById(R.id.new_ingredient)).getText().toString().toLowerCase();

        if(WriteToXML.nodeExists("/food_list/food[ingredient = " + ingredient + " ]")) {
            //add to xml
            foodToEdit.getIngredients().add(ingredient);

            TextView tempText = new TextView(this);
            tempText.setId(Integer.parseInt("ingredient_at_" + numIngredients));
            tempText.setText(ingredient);

            Button tempButton = new Button(this);
            tempButton.setId(numIngredients);
            tempButton.setText("Remove");
            //tempButton.setOnClickListener(this::removeIngredient);
            currLayout.addView(tempText);

            numIngredients++;
        } else {
            Toast.makeText(this, ingredient + " is not a valid ingredient.", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeIngredient(View view) throws Exception {
        int id = view.getId();
        String ingredient = ((EditText)findViewById(id)).getText().toString().toLowerCase();

        if(WriteToXML.nodeExists("/food_list/food[ingredient = " + ingredient + " ]")) {
            //remove from xml
            foodToEdit.getIngredients().remove(ingredient);

            currLayout.removeView(findViewById(Integer.parseInt("ingredient_at_" + id)));
            currLayout.removeView(findViewById(id));

            numIngredients--;
        } else {
            Toast.makeText(this, ingredient + " is not an ingredient in this food.", Toast.LENGTH_SHORT).show();
        }
    }

    public void returnToHome() {
        removeFields();
        finish();
    }

    public void removeFields() {
        addIngredientButton.setVisibility(View.INVISIBLE);
        newIngredientInput.setVisibility(View.INVISIBLE);
    }
}
