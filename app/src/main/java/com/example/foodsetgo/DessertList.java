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

public class DessertList extends ArrayAdapter<Dessert> {

    private Activity context;
    private List<Dessert> dessertList;

    public DessertList(Activity context,List<Dessert> dessertList){
        super(context, R.layout.list_layout, dessertList);
        this.context = context;
        this.dessertList = dessertList;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View desList = inflater.inflate(R.layout.list_layout, null, true);

        TextView des_text1 = (TextView) desList.findViewById(R.id.des_text1);
        TextView des_text2 = (TextView) desList.findViewById(R.id.des_text2);

        Dessert dessert = dessertList.get(position);

        des_text1.setText(dessert.getDesname());
        des_text2.setText(dessert.getDesdiscription());

        return desList;
    }
}
