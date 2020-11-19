package com.example.delish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Food> {
    private Context context;
    private int resource;

    public FoodListAdapter(Context context, int resource, List<Food> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Food food = getItem(position);
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, null);
        }
        TextView nameView = (TextView) convertView.findViewById(R.id.food_view);
        TextView amountView = (TextView) convertView.findViewById(R.id.amount_view);
        TextView caloriesView = (TextView) convertView.findViewById(R.id.calories_view);
        if(nameView != null) {
            nameView.setText(food.getName());
        }
        if(amountView != null) {
            amountView.setText(Integer.toString(food.getAmount()));
        }
        if(caloriesView != null) {
            caloriesView.setText(Integer.toString(food.getTotalCalories()));
        }
        return convertView;
    }
}