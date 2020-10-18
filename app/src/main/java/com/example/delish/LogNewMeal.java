package com.example.delish;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LogNewMeal extends Fragment {
    Button getNumFoodsButton;
    EditText getNumFoodsInput;
    int numFoods = 0;

    Button submitMealButton;
    List<EditText> foodNameInputs = new ArrayList<EditText>();
    List<String> foodNames = new ArrayList<String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_new_meal, container, false);

        getNumFoodsButton = (Button)view.findViewById(R.id.get_num_foods_button);
        getNumFoodsInput = (EditText)view.findViewById(R.id.get_num_foods);

        getNumFoodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numFoods = Integer.parseInt(getNumFoodsInput.getText().toString());
                /*
                TODO: get variable number of ingredients working
                EditText temp;
                for(int i = 0; i < numFoods; i++) {
                    temp = view.findViewById(R.id.food_name_input_1);
                    temp.setId(i);
                    temp.setHint("Enter food item " + (i + 1));
                    layout = (LinearLayout)getActivity().findViewById(R.id.logNewMeal);
                    layout.addView(temp);
                }
                */
                foodNameInputs.add((EditText)view.findViewById(R.id.food_name_input_1));
                foodNameInputs.add((EditText)view.findViewById(R.id.food_name_input_2));
                foodNameInputs.add((EditText)view.findViewById(R.id.food_name_input_3));
                foodNameInputs.add((EditText)view.findViewById(R.id.food_name_input_4));
                foodNameInputs.add((EditText)view.findViewById(R.id.food_name_input_5));
            }
        });
        
        submitMealButton = (Button) view.findViewById(R.id.submit_meal_button);
        submitMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < numFoods; i++) {
                    foodNames.add(foodNameInputs.get(i).getText().toString().toLowerCase());
                }
            }
        });

        return view;
    }
}
