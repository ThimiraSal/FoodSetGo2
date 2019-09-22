package com.example.foodsetgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddNonVegDetailsActivity extends AppCompatActivity {

    TextView textViewnonveg;
    EditText nonveg_price,nonveg_quantity;
    SeekBar seekbarnonveg;
    Button nonveg_adddetails_button;

    ListView listViewNonVegdetails;

    DatabaseReference databaseNonVegDetails;

    List<NonVegDetails> nonvegs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_non_veg_details);

        textViewnonveg = (TextView) findViewById(R.id.textViewnonveg);
        nonveg_price = (EditText) findViewById(R.id.nonveg_price);
        nonveg_quantity = (EditText) findViewById(R.id.nonveg_quantity);
        seekbarnonveg = (SeekBar) findViewById(R.id.seekbarnonveg);
        nonveg_adddetails_button = (Button) findViewById(R.id.nonveg_adddetails_button);

        listViewNonVegdetails = (ListView) findViewById(R.id.listViewNonVegdetails);

        Intent intent = getIntent();

        nonvegs = new ArrayList<>();


        String id = intent.getStringExtra(NonVegActivity.NONVEG_ID);
        String name = intent.getStringExtra(NonVegActivity.NONVEG_NAME);

        textViewnonveg.setText(name);

        databaseNonVegDetails = FirebaseDatabase.getInstance().getReference("NonVegDetails").child(id);

        nonveg_adddetails_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNonVeg();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseNonVegDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nonvegs.clear();

                for(DataSnapshot nonvegdetailsSnapshot : dataSnapshot.getChildren()){
                    NonVegDetails nonvegdetails = nonvegdetailsSnapshot.getValue(NonVegDetails.class);
                    nonvegs.add(nonvegdetails);

                }

                NonVegDetailsList nonVegDetailsListAdapter = new NonVegDetailsList(AddNonVegDetailsActivity.this, nonvegs);
                listViewNonVegdetails.setAdapter(nonVegDetailsListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveNonVeg(){
        String price = nonveg_price.getText().toString().trim();
        String quantity = nonveg_quantity.getText().toString().trim();
        int rating = seekbarnonveg.getProgress();

        if(TextUtils.isEmpty(price)){
            Toast.makeText(this,"Enter the price....",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(quantity)){
            Toast.makeText(this,"Enter the Quantity",Toast.LENGTH_SHORT).show();
        }
        else{
            String id = databaseNonVegDetails.push().getKey();

            NonVegDetails nonvegDetails = new NonVegDetails(id, price,quantity, rating);
            databaseNonVegDetails.child(id).setValue(nonvegDetails);

            Toast.makeText(this,"Details saved Successfully",Toast.LENGTH_SHORT).show();
        }

    }
}
