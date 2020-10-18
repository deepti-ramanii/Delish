package com.example.delish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    Button goToCreateNewFood;
    Button goToLogNewMeal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        goToCreateNewFood = (Button)findViewById(R.id.go_to_create_new_food);
        goToCreateNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(Home.this, CreateNewFood.class);
                startActivity(switchActivityIntent);
            }
        });

        goToLogNewMeal = (Button)findViewById(R.id.go_to_log_new_meal);
        goToLogNewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(Home.this, LogNewMeal.class);
                startActivity(switchActivityIntent);
            }
        });
    }
}