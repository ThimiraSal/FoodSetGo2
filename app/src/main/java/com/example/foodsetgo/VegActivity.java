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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VegActivity extends AppCompatActivity {

    public static final String VEG_ID = "vegid";
    public static final String VEG_NAME = "vegname";

    EditText veg_name;
    EditText veg_description;
    Button veg_add_button;

    DatabaseReference databaseVeg;
    ListView listViewVeg;

    List<Veg> vegList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veg);

        databaseVeg = FirebaseDatabase.getInstance().getReference("Veg");

        veg_name = (EditText)findViewById(R.id.veg_name);
        veg_description = (EditText)findViewById(R.id.veg_description);
        veg_add_button = (Button)findViewById(R.id.veg_add_button);

        listViewVeg = (ListView) findViewById(R.id.listViewVeg);

        vegList = new ArrayList<>();



        veg_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVeg();
            }
        });

        listViewVeg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Veg veg = vegList.get(i);

                Intent intent = new Intent(getApplicationContext(), AddVegDetailsActivity.class);

                intent.putExtra(VEG_ID, veg.getVegId());
                intent.putExtra(VEG_NAME, veg.getVegName());

                startActivity(intent);


            }
        });

       listViewVeg.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               Veg veg  = vegList.get(i);

               ShowUpdateDialog(veg.getVegId(), veg.getVegName(), veg.getVegDescription());


               return false;

           }
       });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseVeg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vegList.clear();

                for(DataSnapshot vegSnapshot : dataSnapshot.getChildren()){
                    Veg veg = vegSnapshot.getValue(Veg.class);

                    vegList.add(veg);


                }

                VegList adapter = new VegList(VegActivity.this, vegList);
                listViewVeg.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ShowUpdateDialog(final String vegId, String vegName, String vegDescriptio){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView  = inflater.inflate(R.layout.update_dialog_veg,null);

        dialogBuilder.setView(dialogView);

       //final TextView TextViewNameVeg = (TextView) dialogView.findViewById(R.id.TextViewNameVeg);
        final EditText update_veg_name = (EditText) dialogView.findViewById(R.id.update_veg_name);
        final EditText update_veg_description = (EditText) dialogView.findViewById(R.id.update_veg_description);
        final Button update_veg_button = (Button) dialogView.findViewById(R.id.update_veg_button);
        final Button delete_veg_button = (Button) dialogView.findViewById(R.id.delete_veg_button);

        dialogBuilder.setTitle("Updating Veg " + vegName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();


        update_veg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = update_veg_name.getText().toString().trim();
                String description = update_veg_description.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    update_veg_name.setError("Name Required");
                    return;
                }
                else if (TextUtils.isEmpty(description)){
                    update_veg_description.setError("Description Required");
                    return;
                }

                updateVeg(vegId, name, description);
                alertDialog.dismiss();

            }
        });

        delete_veg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteVeg(vegId);

            }
        });



    }

    private void deleteVeg(String vegId) {
        DatabaseReference databaseVeg = FirebaseDatabase.getInstance().getReference("Veg").child(vegId);
        DatabaseReference databaseVegDetails = FirebaseDatabase.getInstance().getReference("VegDetails").child(vegId);

        databaseVeg.removeValue();
        databaseVegDetails.removeValue();

        Toast.makeText(this, "Veg is deleted", Toast.LENGTH_SHORT).show();

    }

    private boolean updateVeg(String id, String vegname, String vegdescription){
        DatabaseReference databaseVeg = FirebaseDatabase.getInstance().getReference("Veg").child(id);

        Veg veg = new Veg(id, vegname, vegdescription);
        databaseVeg.setValue(veg);

        Toast.makeText(this, "Veg Updated Successfully", Toast.LENGTH_SHORT).show();

        return true;


    }

    private void addVeg(){
        String vegname = veg_name.getText().toString().trim();
        String vegdescription = veg_description.getText().toString().trim();

        if(TextUtils.isEmpty(vegname)){
            Toast.makeText(this,"Enter a name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vegdescription)){
            Toast.makeText(this,"Enter a description",Toast.LENGTH_SHORT).show();
        }
        else{

            String vegid = databaseVeg.push().getKey();

            Veg veg = new Veg(vegid, vegname, vegdescription);

            databaseVeg.child(vegid).setValue(veg);

            Toast.makeText(this, "Veg Added", Toast.LENGTH_SHORT).show();



        }
    }
}
