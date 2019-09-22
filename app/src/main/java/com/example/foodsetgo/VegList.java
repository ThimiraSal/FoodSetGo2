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

public class VegList extends ArrayAdapter<Veg> {

    private Activity context;
    private List<Veg> vegList;

    public VegList(Activity context, List<Veg> vegList){
        super(context, R.layout.list_layout_veg,vegList);
        this.context = context;
        this.vegList = vegList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItemVeg = inflater.inflate(R.layout.list_layout_veg, null, true);

        TextView textViewVegName = (TextView) listViewItemVeg.findViewById(R.id.textViewVegName);
        TextView textViewVegDescription = (TextView) listViewItemVeg.findViewById(R.id.textViewVegDescription);

        Veg veg = vegList.get(position);

        textViewVegName.setText(veg.getVegName());
        textViewVegDescription.setText(veg.getVegDescription());

        return listViewItemVeg;




    }
}
