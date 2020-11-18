package com.example.delish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;

public class Home extends AppCompatActivity {
    FoodInventoryDatabaseHelper foodInventoryDatabaseHelper;
    Button goToLogNewMeal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        foodInventoryDatabaseHelper = FoodInventoryDatabaseHelper.getInstance(this);
        fillDatabaseFromXML();

        goToLogNewMeal = (Button)findViewById(R.id.go_to_log_new_meal);
        goToLogNewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(Home.this, LogNewMeal.class);
                startActivity(switchActivityIntent);
            }
        });
    }

    private void fillDatabaseFromXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document document = factory.newDocumentBuilder().parse(this.getApplicationContext().getResources().openRawResource(R.raw.food_inventory));
            Element rootElement = document.getDocumentElement();
            NodeList nodeList = rootElement.getElementsByTagName("food");
            for(int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element)nodeList.item(i);
                String name = element.getAttribute("name");
                int calories = Integer.parseInt(element.getElementsByTagName("calories").item(0).getTextContent());
                foodInventoryDatabaseHelper.insert(name, calories);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}