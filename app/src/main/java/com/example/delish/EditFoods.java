package com.example.delish;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class EditFoods extends AppCompatActivity {
    Button getFoodNameButton;
    EditText getFoodNameInput;

    Button addIngredientButton;
    EditText newIngredientInput;
    Food foodToEdit;

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

    public void checkIfFoodExists(View view) {
        String foodName = getFoodNameInput.getText().toString();
        //if the name exists in the database, print out properties that can be changed
            //set foodToChange to whatever it should be
            TextView tempText;
            Button tempButton;
            int i = 0;
            for(String ingredient : foodToEdit.getIngredients()) {
                tempText = new TextView(this);
                tempButton = new Button(this);
                tempText.setId(i);
                tempButton.setId(i + foodToEdit.getIngredients().size());
                tempText.setText(ingredient);
                tempButton.setText("Remove");
                tempButton.setOnClickListener(this::removeIngredient);
                currLayout.addView(tempText);
                currLayout.addView(tempButton);
                i++;
            }
            addIngredientButton.setVisibility(View.VISIBLE);
            newIngredientInput.setVisibility(View.VISIBLE);
        //otherwise
            Toast.makeText(this, foodName + " is not listed as a valid food item.", Toast.LENGTH_SHORT);
    }

    public void addIngredient(View view) {
        String ingredient = ((EditText)findViewById(R.id.new_ingredient)).getText().toString().toLowerCase();
        //if ingredient is valid
            foodToEdit.getIngredients().add(ingredient);
        //otherwise
            Toast.makeText(this, ingredient + " is not listed as a valid food item.", Toast.LENGTH_SHORT);
    }

    public void removeIngredient(View view) {
        int id = view.getId();
        String ingredient = ((EditText)findViewById(id)).getText().toString().toLowerCase();
        //if ingredient is valid
            foodToEdit.getIngredients().remove(ingredient);
            currLayout.removeView(findViewById(id));
            currLayout.removeView(findViewById(id + foodToEdit.getIngredients().size()));
        //otherwise
            Toast.makeText(this, ingredient + " is not listed as a valid food item.", Toast.LENGTH_SHORT);
    }

    public boolean containsIngredient(String ingredient) {
        //find the food corresponding to name & get the ingredients list
        //if the ingredient is in the list
            //return true
        //otherwise
            Toast.makeText(this, ingredient + " is not listed as a valid food item.", Toast.LENGTH_SHORT);
            return false;
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
