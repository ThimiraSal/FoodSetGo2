package com.example.foodsetgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NonVegActivity extends AppCompatActivity {

    public static final String NONVEG_ID = "nonvegid";
    public static final String NONVEG_NAME = "nonvegname";


    EditText nonvegname,nonvegdescription;
    Button nonvegaddbutton;

    DatabaseReference databaseNonVeg;

    ListView listViewNonVeg;

    List<NonVeg> nonVegs;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_veg);

        databaseNonVeg = FirebaseDatabase.getInstance().getReference("NonVeg");

        nonvegname = (EditText) findViewById(R.id.nonvegname);
        nonvegdescription = (EditText) findViewById(R.id.nonvegdescription);
        nonvegaddbutton = (Button) findViewById(R.id.nonvegaddbutton);

        listViewNonVeg = (ListView) findViewById(R.id.listViewNonVeg);

        nonVegs = new ArrayList<>();



        nonvegaddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNonVeg();
            }
        });

        listViewNonVeg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NonVeg nonVeg = nonVegs.get(i);

                Intent intent = new Intent(getApplicationContext(), AddNonVegDetailsActivity.class);

                intent.putExtra(NONVEG_ID, nonVeg.getNonvegId());
                intent.putExtra(NONVEG_NAME, nonVeg.getNonvegName());

                startActivity(intent);

            }
        });

        listViewNonVeg.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                NonVeg nonVeg  = nonVegs.get(i);

                showUpdateDialog(nonVeg.getNonvegId(), nonVeg.getNonvegName(), nonVeg.getNonvegDescription());


                return false;

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseNonVeg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nonVegs.clear();
                for(DataSnapshot nonvegSnapshot : dataSnapshot.getChildren()){

                    NonVeg nonVeg = nonvegSnapshot.getValue(NonVeg.class);

                    nonVegs.add(nonVeg);

                }

                NonVegList adapter = new NonVegList(NonVegActivity.this, nonVegs);
                listViewNonVeg.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(final String nonvegId, String nonvegName, String nonvegDescription){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog_nonveg, null);

        dialogBuilder.setView(dialogView);

        //final TextView textViewNonVegName = (TextView) dialogView.findViewById(R.id.textViewNonVegName);
        final EditText updatenonvegname = (EditText) dialogView.findViewById(R.id.updatenonvegname);
        final EditText updatenonvegdescription = (EditText) dialogView.findViewById(R.id.updatenonvegdescription);
        final Button updatenonveg = (Button) dialogView.findViewById(R.id.updatenonveg);
        final Button deletenonveg = (Button) dialogView.findViewById(R.id.deletenonveg);

        dialogBuilder.setTitle("Updating Non Veg " + nonvegName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();


        updatenonveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = updatenonvegname.getText().toString().trim();
                String description = updatenonvegdescription.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    updatenonvegname.setError("Name Required");
                    return;
                }
                else if (TextUtils.isEmpty(description)){
                    updatenonvegdescription.setError("Description Required");
                    return;
                }

                updateNonVeg(nonvegId, name, description);

                alertDialog.dismiss();




            }
        });

        deletenonveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNonVeg(nonvegId);
            }
        });

    }

    private void deleteNonVeg(String nonvegId) {
        DatabaseReference databaseNonVeg = FirebaseDatabase.getInstance().getReference("NonVeg").child(nonvegId);
        DatabaseReference databaseNonVegDetails = FirebaseDatabase.getInstance().getReference("NonVegDetails").child(nonvegId);

        databaseNonVeg.removeValue();
        databaseNonVegDetails.removeValue();

        Toast.makeText(this, "Non Veg deleted", Toast.LENGTH_SHORT).show();
        
    }

    private boolean updateNonVeg(String id,String name, String description){
        DatabaseReference databaseNonVeg = FirebaseDatabase.getInstance().getReference("NonVeg").child(id);

        NonVeg nonVeg = new NonVeg(id, name, description);

        databaseNonVeg.setValue(nonVeg);

        Toast.makeText(this, "Non Veg Updated", Toast.LENGTH_SHORT).show();

        return true;


    }

    private void addNonVeg(){
        String name = nonvegname.getText().toString().trim();
        String description = nonvegdescription.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Enter a name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(description)){
            Toast.makeText(this,"Enter a description",Toast.LENGTH_SHORT).show();
        }
        else{

            String id = databaseNonVeg.push().getKey();
            NonVeg nonVeg = new NonVeg(id,name,description);

            databaseNonVeg.child(id).setValue(nonVeg);

            Toast.makeText(this, "NonVeg added", Toast.LENGTH_SHORT).show();


        }
    }
}
