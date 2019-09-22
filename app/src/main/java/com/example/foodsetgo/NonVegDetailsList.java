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

public class NonVegDetailsList extends ArrayAdapter <NonVegDetails>{

    private Activity context;
    private List<NonVegDetails> nonVegDetailsList;

    public NonVegDetailsList(Activity context, List<NonVegDetails> nonVegDetailsList){
        super(context, R.layout.layout_nonvegdetails_list,nonVegDetailsList);
        this.context = context;
        this.nonVegDetailsList = nonVegDetailsList;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItemNonVegDetails = inflater.inflate(R.layout.layout_nonvegdetails_list,null,true);

        TextView textViewNonVegPrice = (TextView) listViewItemNonVegDetails.findViewById(R.id.textViewNonVegPrice);
        TextView textViewNonVegQuantity = (TextView) listViewItemNonVegDetails.findViewById(R.id.textViewNonVegQuantity);
        TextView textViewNonVegRating = (TextView) listViewItemNonVegDetails.findViewById(R.id.textViewNonVegRating);



        NonVegDetails nonVegDetails = nonVegDetailsList.get(position);

        textViewNonVegPrice.setText(nonVegDetails.getNonvegdetailsPrice());
        textViewNonVegQuantity.setText(nonVegDetails.getNonvegdetailsQuantity());
        textViewNonVegRating.setText(String.valueOf(nonVegDetails.getNonvegdetailsrating()));

        return listViewItemNonVegDetails;

    }


}
