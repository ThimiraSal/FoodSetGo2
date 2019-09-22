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

public class DessertDetailsList extends ArrayAdapter<DessertDetails> {

    private Activity context;
    private List<DessertDetails> dessertDetails;

    public DessertDetailsList(Activity context,List<DessertDetails> dessertDetails){
        super(context, R.layout.dessert_details_list, dessertDetails);
        this.context = context;
        this.dessertDetails = dessertDetails;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View desList = inflater.inflate(R.layout.dessert_details_list, null, true);

        TextView getprice = (TextView) desList.findViewById(R.id.getprice);
        TextView getquantity = (TextView) desList.findViewById(R.id.getquantity);
        TextView getrating = (TextView) desList.findViewById(R.id.getrating);


        DessertDetails dessertaddDetails = dessertDetails.get(position);

        getprice.setText(dessertaddDetails.getDesPrice());
        getquantity.setText(dessertaddDetails.getDesQuantity());
        getrating.setText(String.valueOf(dessertaddDetails.getDesDetailRating()));


        return desList;
    }


}
