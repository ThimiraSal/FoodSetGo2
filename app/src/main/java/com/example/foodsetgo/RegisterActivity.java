package com.example.foodsetgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity{


    EditText txtPwd,txtPhone,txtUserName;
    //ProgressBar progressBar;
    Button submit,login;
    ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtUserName = (EditText) findViewById(R.id.editText3);
        txtPwd = (EditText) findViewById(R.id.editText5);
        txtPhone = (EditText) findViewById(R.id.editText4);
        loadingBar = new ProgressDialog(this);
        submit = (Button)findViewById(R.id.regSubmit) ;
        login = (Button)findViewById(R.id.login);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

        private void createAccount(){
            String username = txtUserName.getText().toString();
            String password = txtPwd.getText().toString();
            String phone = txtPhone.getText().toString();

            if(TextUtils.isEmpty(username)){
                Toast.makeText(this,"Please enter your name..",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(phone)){
                Toast.makeText(this,"Please enter your email..",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Please enter your password..",Toast.LENGTH_SHORT).show();
            }
            else{
                loadingBar.setTitle("Create Account");
                loadingBar.setMessage("Please wait,while we are checking the credentilas");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                ValidatePhone(username,phone,password);
            }
        }

    private void ValidatePhone(final String username, final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists())){

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("username",username);
                    userdataMap.put("password",password);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"Congratulations,Your account has been created!",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this,"Network Error: Please try again after sometime..",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }else{
                    Toast.makeText(RegisterActivity.this,"This " + phone + " already axists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this,"Please try again using another mobile number.",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//    private void registerUser(){
//
//        String username = txtUserName.getText().toString().trim();
//        String password = txtPwd.getText().toString().trim();
//        String email = txtEmail.getText().toString().trim();
//
//        if(username.isEmpty()){
//            txtUserName.setError("User name cannot be empty");
//            txtUserName.requestFocus();
//            return;
//        }
//
//        if(email.isEmpty()){
//            txtEmail.setError("Email cannot be empty");
//            txtEmail.requestFocus();
//            return;
//        }
//        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            txtEmail.setError("Please enter a valid email");
//            txtEmail.requestFocus();
//            return;
//        }
//        if(password.isEmpty()){
//            txtPwd.setError("User password cannot be empty");
//            txtPwd.requestFocus();
//            return;
//        }
//        if(password.length()<8){
//            txtPwd.setError("Minimum length of password is should be 8");
//            txtPwd.requestFocus();
//            return;
//        }
//
//        progressBar.setVisibility(View.VISIBLE);
//
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.regSubmit:
//                registerUser();
//                break;
//            case R.id.loginBtn:
//                startActivity(new Intent(this,LoginActivity.class));
//                break;
//
//        }
//    }
}
