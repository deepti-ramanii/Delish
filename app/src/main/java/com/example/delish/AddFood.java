package com.example.delish;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class AddFood extends AppCompatActivity {
    private FoodDatabaseHelper foodDatabaseHelper;

    private View view;
    private LinearLayout currLayout;

    private EditText foodName;
    private EditText numServings;
    private EditText numIngredients;
    private List<Integer> ingredientInputIDs;
    private LinearLayout ingredientInputs;

    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_add_food, null);
        builder.setView(view)
                .setTitle("Add new food item:")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         cancelFoodSubmission();
                     }
                })
                .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         submitFood();
                     }
                });
        foodDatabaseHelper = FoodDatabaseHelper.getInstance(getActivity());

        currLayout = (LinearLayout)view.findViewById(R.id.layout_add_food);

        foodName = (EditText)view.findViewById((R.id.food_name));
        numServings = (EditText)view.findViewById((R.id.num_servings));
        numIngredients = (EditText)view.findViewById(R.id.num_ingredients);
        ingredientInputIDs = new ArrayList<Integer>();
        return builder.create();
    }
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_food);
        foodDatabaseHelper = FoodDatabaseHelper.getInstance(this);

        currLayout = (LinearLayout)findViewById(R.id.layout_add_food);

        foodName = (EditText)findViewById((R.id.food_name));
        numServings = (EditText)findViewById((R.id.num_servings));
        numIngredients = (EditText)findViewById(R.id.num_ingredients);
        ingredientInputIDs = new ArrayList<Integer>();
        ingredientInputs = (LinearLayout)currLayout.findViewById(R.id.layout_ingredient_inputs);
    }

    public void submitFood(View view) {
        boolean successful = true;
        String name = foodName.getText().toString().toLowerCase().trim();
        int servings = Integer.parseInt(numServings.getText().toString().toLowerCase().trim());
        int calories = 0;
        for(int id : ingredientInputIDs) {
            //TODO: two separate input fields, one for name and one for amount
            String[] inputs = ((EditText)findViewById(id)).getText().toString().toLowerCase().split(",");
            String ingredientName = inputs[0].trim();
            int ingredientAmount = Integer.parseInt(inputs[1].trim());
            if(foodDatabaseHelper.contains(ingredientName)) {
                Food ingredient = foodDatabaseHelper.getFromName(ingredientName);
                calories += ingredient.getCaloriesPerServing() * ingredientAmount;
            } else {
                successful = false;
                ((EditText)findViewById(id)).setError("Could not find " + ingredientName);
            }
        }
        if(successful) {
            foodDatabaseHelper.insert(name, calories / servings);
            removeAllFields();
            finish();
        }
    }

    public void cancelFoodSubmission(View view) {
        removeAllFields();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void displayIngredientInputFields(View view) {
        removeIngredientInputFields();
        int count = Integer.parseInt(numIngredients.getText().toString().toLowerCase().trim());
        for(int i = 0; i < count; i++) {
            int id = View.generateViewId();
            ingredientInputIDs.add(id);
            EditText ingredientInput = new EditText(this);
            ingredientInput.setId(id);
            ingredientInput.setHint("Enter an ingredient name and amount separated by a comma");
            ingredientInputs.addView(ingredientInput);
        }
    }

    private void removeIngredientInputFields() {
        for(int id : ingredientInputIDs) {
            ingredientInputs.removeView((EditText)findViewById(id));
        }
        ingredientInputIDs.clear();
    }

    private void removeAllFields() {
        removeIngredientInputFields();
        foodName.getText().clear();
        numServings.getText().clear();
        numIngredients.getText().clear();
    }
}
