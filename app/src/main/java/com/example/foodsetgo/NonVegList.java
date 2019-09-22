package com.example.foodsetgo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NonVegList extends ArrayAdapter<NonVeg> {
    private Activity context;
    private List<NonVeg> nonVegList;

    public NonVegList(Activity context, List<NonVeg> nonVegList){
        super(context, R.layout.list_layout_nonveg,nonVegList);
        this.context = context;
        this.nonVegList = nonVegList;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItemNonVeg = inflater.inflate(R.layout.list_layout_nonveg,null,true);

        TextView textViewNonVegName = (TextView) listViewItemNonVeg.findViewById(R.id.textViewNonVegName);
        TextView textViewNonVegDescription = (TextView) listViewItemNonVeg.findViewById(R.id.textViewNonVegDescription);

        NonVeg nonVeg = nonVegList.get(position);

        textViewNonVegName.setText(nonVeg.getNonvegName());
        textViewNonVegDescription.setText(nonVeg.getNonvegDescription());

        return listViewItemNonVeg;

    }
}
