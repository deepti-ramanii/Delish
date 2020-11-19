package com.example.delish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.List;

public class FoodListAdapter extends BaseExpandableListAdapter {
    private List<Integer> calories;
    private List<List<Food>> meals;
    private LayoutInflater inflater;
    private Context context;

    public FoodListAdapter(List<Integer> calories, List<List<Food>> meals) {
        this.calories = calories;
        this.meals = meals;
    }

    public void setInflater(LayoutInflater inflater, Context context) {
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Food food = meals.get(groupPosition).get(childPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.food_list_layout, null);
        }
        TextView foodView = (TextView) convertView.findViewById(R.id.food_view);
        TextView amountView = (TextView) convertView.findViewById(R.id.amount_view);
        TextView caloriesView = (TextView) convertView.findViewById(R.id.calories_view);

        if(foodView != null) {
            foodView.setText(food.getName());
        }
        if(caloriesView != null) {
            caloriesView.setText(Integer.toString(food.getCaloriesPerServing()));
        }
        if(amountView != null) {
            amountView.setText(Integer.toString(food.getAmount()));
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return meals.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return calories.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.calories_list_layout, null);
        }
        ((CheckedTextView)convertView).setText("Calories in meal: " + calories.get(groupPosition));
        ((CheckedTextView)convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}