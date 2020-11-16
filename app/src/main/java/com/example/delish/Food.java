package com.example.delish;

import java.util.List;
import java.util.Map;

public class Food {
    private String name;
    private int totalCalories;

    //create a new food object if the number of calories per serving is unknown
    public Food(String name, int numServings, Map<String, Integer> ingredients) {
        this.name = name;
        this.totalCalories = calculateTotalCalories(ingredients) / numServings;
    }

    //create a new food object if the number of calories per serving is known
    public Food(String name, int numCalories) {
        this.name = name;
        this.totalCalories = numCalories;
    }

    //add calories from each ingredient to get total calories from the food item
    private int calculateTotalCalories(Map<String, Integer> ingredients) {
        int numCalories = 0;
        for(String name : ingredients.keySet()) {
            Food ingredient = WriteToXML.getFoodFromName(name);
            numCalories += ingredient.totalCalories * ingredients.get(name);
        }
        return numCalories;
    }

    public String getName() {
        return this.name;
    }

    public int getCalories() { return this.totalCalories; }

    public String toString() {
        String temp = "";
        temp += "<food>\n";
        temp += "\t<name>" + this.name + "</name>\n";
        temp += "\t<calories>" + this.totalCalories + "</calories>\n";
        temp += "</food>";
        return temp;
    }

}
