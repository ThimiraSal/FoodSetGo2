package com.example.foodsetgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecipePageActivity extends AppCompatActivity {

    private Button logout_r_button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        logout_r_button = (Button)findViewById(R.id.logout_r_button);

        logout_r_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipePageActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
