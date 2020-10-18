package com.example.delish;
import java.util.List;
import java.util.Set;

public class Food {
    private String name;
    private int numberOfServings;
    private List<String> ingredients;
    private int totalCalories;

    public Food(String name, int numberOfServings, List<String> ingredients) {
        this.name = name;
        this.numberOfServings = numberOfServings;
        this.ingredients = ingredients;

        //calculateTotalCalories();
    }

    /*
    private void calculateTotalCalories() {
        for(String ingredients : this.ingredients) {
            read from the list of ingredients
            int caloriesInIngredient = ____;
            this.totalCalories += caloriesInIngredient;
        }
    }

    public int getCaloriesPerServing() {
        return this.totalCalories / this.numberOfServings;
    }
    */

    public String getName() {
        return this.name;
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public int getCaloriesPerServing() { return this.totalCalories / this.numberOfServings; }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
        //update totalCalories
    }

    public void removeIngredient(String ingredient) {
        this.ingredients.remove(ingredient);
        //update totalCalories
    }

}
