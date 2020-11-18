package com.example.delish;

import java.util.List;
import java.util.Map;

public class Food {
    private String name;
    private int calories;
    private List<String> ingredients;

    //create a new food object if the number of calories per serving is known
    public Food(String name, int numCalories) {
        this.name = name;
        this.calories = numCalories;
    }

    public void setName(String name) { this.name = name; }

    public void setCalories(int calories) { this.calories = calories; }

    public String getName() { return this.name; }

    public int getCalories() { return this.calories; }

    public String toString() {
        return this.name + ", " + this.calories;
    }

}
