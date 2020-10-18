package com.example.delish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateNewFood extends Fragment {
    EditText getFoodNameInput;
    String foodName;

    EditText getNumServingsInput;
    int numServings;

    Button getNumIngredientsButton;
    EditText getNumIngredientsInput;
    int numIngredients;

    Button submitFoodButton;
    List<EditText> ingredientNameInputs = new ArrayList<EditText>();
    Set<String> ingredients = new HashSet<String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_food, container, false);

        //get name of new food
        getFoodNameInput = (EditText)view.findViewById((R.id.get_food_name));

        //get number of servings in new food
        getNumServingsInput = (EditText)view.findViewById((R.id.get_num_servings));

        //get ingredients of new food
        getNumIngredientsInput = (EditText)view.findViewById(R.id.get_num_ingredients);
        getNumIngredientsButton = (Button)view.findViewById(R.id.get_num_ingredients_button);
        getNumIngredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numIngredients = Integer.parseInt(getNumIngredientsInput.getText().toString());
                /*
                TODO: get changing number of ingredients working
                for(int i = 0; i < numIngredients; i++) {
                    //add corresponding number of input fields
                }
                */
                //add the ingredients to a set
                ingredientNameInputs.add((EditText)view.findViewById(R.id.ingredient_input_1));
                ingredientNameInputs.add((EditText)view.findViewById(R.id.ingredient_input_2));
                ingredientNameInputs.add((EditText)view.findViewById(R.id.ingredient_input_3));
                ingredientNameInputs.add((EditText)view.findViewById(R.id.ingredient_input_4));
                ingredientNameInputs.add((EditText)view.findViewById(R.id.ingredient_input_5));
            }
        });

        submitFoodButton = (Button)view.findViewById(R.id.submit_food_button);
        submitFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodName = getFoodNameInput.getText().toString().toLowerCase();
                numServings = Integer.parseInt(getNumServingsInput.getText().toString());
                for(int i = 0; i < numIngredients; i++) {
                    ingredients.add(ingredientNameInputs.get(i).getText().toString().toLowerCase());
                }
            }
        });

        Food newFood = new Food(foodName, numServings, ingredients);
        //store the new food in the directory somewhere

        return view;
    }
}
