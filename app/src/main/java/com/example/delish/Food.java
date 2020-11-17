package com.example.delish;

import java.util.List;
import java.util.Map;

public class Food {
    private String name;
    private int calories;

    //create a new food object if the number of calories per serving is unknown
    public Food(String name, int numServings, Map<Food, Integer> ingredients) {
        this.name = name;
        this.calories = calculateTotalCalories(ingredients) / numServings;
    }

    //create a new food object if the number of calories per serving is known
    public Food(String name, int numCalories) {
        this.name = name;
        this.calories = numCalories;
    }

    //add calories from each ingredient to get total calories from the food item
    private int calculateTotalCalories(Map<Food, Integer> ingredients) {
        int numCalories = 0;
        for(Food ingredient : ingredients.keySet()) {
            numCalories += ingredient.calories * ingredients.get(ingredient);
        }
        return numCalories;
    }

    public String getName() { return this.name; }

    public int getCalories() { return this.calories; }

    public String toString() {
        String str = "";
        str += "<food>\n";
        str += "\t<name>" + this.name + "</name>\n";
        str += "\t<calories>" + this.calories + "</calories>\n";
        str += "</food>";
        return str;
    }

}
