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

public class VegDetailsList extends ArrayAdapter<VegDetails> {
    private Activity context;
    private List<VegDetails> vegdetailsList;

    public VegDetailsList(Activity context, List<VegDetails> vegdetailsList){
        super(context, R.layout.layout_vegdetails_list,vegdetailsList);
        this.context = context;
        this.vegdetailsList = vegdetailsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItemVeg = inflater.inflate(R.layout.layout_vegdetails_list, null, true);

        TextView textViewVegPrice = (TextView) listViewItemVeg.findViewById(R.id.textViewVegPrice);
        TextView textViewVegQuantity = (TextView) listViewItemVeg.findViewById(R.id.textViewVegQuantity);
        TextView textViewVegRating = (TextView) listViewItemVeg.findViewById(R.id.textViewVegRating);


        VegDetails veg_details = vegdetailsList.get(position);

        textViewVegPrice.setText(veg_details.getVegdetailsPrice());
        textViewVegQuantity.setText(veg_details.getVegdetailsQuantity());
        textViewVegRating.setText(String.valueOf(veg_details.getVegdetailsRating()));

        return listViewItemVeg;




    }


}
