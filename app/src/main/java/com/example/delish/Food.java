package com.example.delish;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

public class Food {
    private String name;
    private int numberOfServings;
    private List<String> ingredients;
    private int totalCalories;

    public Food(String name, int numberOfServings, List<String> ingredients) {
        this.name = name;
        this.numberOfServings = numberOfServings;
        this.ingredients = ingredients;
        calculateTotalCalories();
    }

    public Food(String name, int numberOfCalories, int numberOfServings, List<String> ingredients) {
        this.name = name;
        this.totalCalories = numberOfCalories;
        this.numberOfServings = numberOfServings;
        this.ingredients = ingredients;
    }

    private void calculateTotalCalories() {
        for(String ingredient : this.ingredients) {
            Food ing = WriteToXML.getFoodFromName(ingredient);
            this.totalCalories += ing.totalCalories;
        }
    }

    public String getName() {
        return this.name;
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public int getCaloriesPerServing() { return this.totalCalories / this.numberOfServings; }

    public int getNumberOfServings() { return this.numberOfServings; }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
        Food ing = WriteToXML.getFoodFromName(ingredient);
        this.totalCalories += ing.totalCalories;
    }

    public void removeIngredient(String ingredient) {
        this.ingredients.remove(ingredient);
        Food ing = WriteToXML.getFoodFromName(ingredient);
        this.totalCalories -= ing.totalCalories;
    }

}
