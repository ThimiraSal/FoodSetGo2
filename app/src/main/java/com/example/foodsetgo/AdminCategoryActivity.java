package com.example.foodsetgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {


    private ImageView dessert;
    private ImageView nonVeg;
    private ImageView veg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        dessert = (ImageView) findViewById(R.id.dessert1);
        veg = (ImageView) findViewById(R.id.veg1);
        nonVeg = (ImageView) findViewById(R.id.nonVeg1);

        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,DessertActivity.class);
                intent.putExtra("category","dessert");
                startActivity(intent);
            }
        });

        nonVeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,NonVegActivity.class);
                intent.putExtra("category","nonveg");
                startActivity(intent);
            }
        });

        veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,VegActivity.class);
                intent.putExtra("category","veg");
                startActivity(intent);
            }
        });


    }
}
