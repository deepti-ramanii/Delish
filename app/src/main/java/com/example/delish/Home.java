package com.example.delish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    Button goToLogNewMeal;
    Button goToEditFood;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);



        goToLogNewMeal = (Button)findViewById(R.id.go_to_log_new_meal);
        goToLogNewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(Home.this, LogNewMeal.class);
                startActivity(switchActivityIntent);
            }
        });

        goToEditFood = (Button)findViewById(R.id.go_to_edit_food);
        goToEditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(Home.this, EditFoods.class);
                startActivity(switchActivityIntent);
            }
        });
    }
}