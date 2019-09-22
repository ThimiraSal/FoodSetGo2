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

public class AddDessertDetailsActivity extends AppCompatActivity {

    EditText des_price, des_qty;
    TextView selected_des_name;
    SeekBar des_seekbar;
    Button add_dessert_price;

    ListView select_listView_des;

    DatabaseReference databaseDesDetails;

    List<DessertDetails> dessertDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dessert_details);

        des_price = (EditText) findViewById(R.id.des_price);
        des_qty = (EditText) findViewById(R.id.des_qty);
        selected_des_name = (TextView) findViewById(R.id.selected_des_name);
        des_seekbar = (SeekBar) findViewById(R.id.des_seekbar);

        add_dessert_price = (Button) findViewById(R.id.add_dessert_price);

        select_listView_des = (ListView) findViewById(R.id.select_listView_des);


        Intent intent = getIntent();
        dessertDetails = new ArrayList<>();

        String id_des = intent.getStringExtra(DessertActivity.DES_ID);
        String name_des = intent.getStringExtra(DessertActivity.DES_NAME);

        selected_des_name.setText(name_des);

        databaseDesDetails = FirebaseDatabase.getInstance().getReference("DessertDetails").child(id_des);

        add_dessert_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDessertDetails();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseDesDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dessertDetails.clear();

                for(DataSnapshot desdetailSnapshot : dataSnapshot.getChildren()){
                    DessertDetails dessertDetail = desdetailSnapshot.getValue(DessertDetails.class);
                    dessertDetails.add(dessertDetail);

                }
                DessertDetailsList dessertDetailsListAdapter = new DessertDetailsList(AddDessertDetailsActivity.this,dessertDetails);
                select_listView_des.setAdapter(dessertDetailsListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void saveDessertDetails() {

        String price = des_price.getText().toString().trim();
        String qty = des_qty.getText().toString().trim();
        int rating = des_seekbar.getProgress();


        if(TextUtils.isEmpty(price)){
            Toast.makeText(this,"Enter the price....",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(qty)){
            Toast.makeText(this,"Enter the Quantity",Toast.LENGTH_SHORT).show();
        }
        else{
           String id = databaseDesDetails.push().getKey();

           DessertDetails dessertDetails = new DessertDetails(id, price,qty, rating);
           databaseDesDetails.child(id).setValue(dessertDetails);

           Toast.makeText(AddDessertDetailsActivity.this,"Details saved Successfully",Toast.LENGTH_SHORT).show();
        }

    }
}
