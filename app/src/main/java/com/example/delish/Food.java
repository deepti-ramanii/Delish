package com.example.delish;

public class Food {
    private String name;
    private int caloriesPerServing;
    private int amount;

    //create a new food object if the number of calories per serving is known
    public Food(String name, int caloriesPerServing) {
        this.name = name;
        this.caloriesPerServing = caloriesPerServing;
        this.amount = 1;
    }

    public Food(String name, int caloriesPerServing, int amount) {
        this.name = name;
        this.caloriesPerServing = caloriesPerServing;
        this.amount = amount;
    }

    public void setName(String name) { this.name = name; }

    public void setCaloriesPerServing(int caloriesPerServing) { this.caloriesPerServing = caloriesPerServing; }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() { return this.name; }

    public int getCaloriesPerServing() { return this.caloriesPerServing; }

    public int getTotalCalories() { return this.caloriesPerServing * this.amount; }

    public int getAmount() { return this.amount; }

    public String toString() {
        return this.name + " (" + this.caloriesPerServing + " cal), " + this.amount;
    }

}
