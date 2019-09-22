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

public class AddVegDetailsActivity extends AppCompatActivity {
    EditText veg_price, veg_quantity;
    SeekBar seekbarveg;
    Button veg_adddetails_button;
    TextView textView1;
    ListView listViewVegdetails;

    DatabaseReference databaseVegDetails;

    List<VegDetails> veg_details;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_veg_details);

        veg_price = (EditText)findViewById(R.id.veg_price);
        veg_quantity = (EditText)findViewById(R.id.veg_quantity);
        seekbarveg = (SeekBar)findViewById(R.id.seekbarveg);
        veg_adddetails_button = (Button)findViewById(R.id.veg_adddetails_button);
        textView1 = (TextView)findViewById(R.id.textView1);
        listViewVegdetails = (ListView)findViewById(R.id.listViewVegdetails);

        Intent intent = getIntent();

        veg_details = new ArrayList<>();

        String id = intent.getStringExtra(VegActivity.VEG_ID);
        String name = intent.getStringExtra(VegActivity.VEG_NAME);

        textView1.setText(name);

        databaseVegDetails = FirebaseDatabase.getInstance().getReference("VegDetails").child(id);


        veg_adddetails_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveVegDetails();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseVegDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                veg_details.clear();

                for (DataSnapshot vegdetailsSnapshot : dataSnapshot.getChildren()){
                    VegDetails vegDetails = vegdetailsSnapshot.getValue(VegDetails.class);
                    veg_details.add(vegDetails);

                }

                VegDetailsList vegdetailslistAdapter = new VegDetailsList(AddVegDetailsActivity.this, veg_details);
                listViewVegdetails.setAdapter(vegdetailslistAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveVegDetails(){
        String vegdetailsPrice = veg_price.getText().toString().trim();
        String vegdetailsQuantity = veg_quantity.getText().toString().trim();
        int ratingveg = seekbarveg.getProgress();

        if(TextUtils.isEmpty(vegdetailsPrice)){
            Toast.makeText(this,"Enter the price....",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vegdetailsQuantity)){
            Toast.makeText(this,"Enter the Quantity",Toast.LENGTH_SHORT).show();
        }
        else{
            String id = databaseVegDetails.push().getKey();

            VegDetails vegDetails = new VegDetails(id, vegdetailsPrice,vegdetailsQuantity, ratingveg);
            databaseVegDetails.child(id).setValue(vegDetails);

            Toast.makeText(AddVegDetailsActivity.this,"Details saved Successfully",Toast.LENGTH_SHORT).show();
        }

    }
}
