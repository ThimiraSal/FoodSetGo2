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

public class DessertActivity extends AppCompatActivity {

    public static final  String DES_NAME = "dessertname";
    public static final  String DES_ID = "dessertid";

    EditText des_name, des_description;
    Button add_dessert;
    DatabaseReference database_dessert;

    ListView listViewDes;

    List<Dessert> dessertList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert);

        des_name = (EditText) findViewById(R.id.des_name);
        des_description = (EditText) findViewById(R.id.des_description);
        add_dessert = (Button) findViewById(R.id.add_dessert);
        database_dessert = FirebaseDatabase.getInstance().getReference("Dessert");
        listViewDes = (ListView) findViewById(R.id.listView_des) ;
        dessertList = new ArrayList<>();



        add_dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDessert();
            }
        });

        listViewDes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dessert dessert = dessertList.get(i);

                Intent intent = new Intent(getApplicationContext(),AddDessertDetailsActivity.class);
                intent.putExtra(DES_ID, dessert.getDesid());
                intent.putExtra(DES_NAME, dessert.getDesname());

                startActivity(intent);
            }
        });

        listViewDes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Dessert dessert = dessertList.get(i);

                showDesUpdateDialog(dessert.getDesid(),dessert.getDesname(),dessert.getDesdiscription());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        database_dessert.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dessertList.clear();

                for(DataSnapshot dessertSnapshot: dataSnapshot.getChildren()){
                    Dessert dessert = dessertSnapshot.getValue(Dessert.class);

                    dessertList.add(dessert);
                }

                DessertList des_adapter = new DessertList(DessertActivity.this,dessertList);
                listViewDes.setAdapter(des_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showDesUpdateDialog(final String desid, String desname, final String desdiscription){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_des_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText des_update_name = (EditText) dialogView.findViewById(R.id.des_update_name);
        final EditText des_update_description = (EditText) dialogView.findViewById(R.id.des_update_description);
//        final TextView update_title = (TextView) dialogView.findViewById(R.id.update_title);
        final Button des_update = (Button) dialogView.findViewById(R.id.des_update);
        final Button des_delete = (Button) dialogView.findViewById(R.id.des_delete);


        dialogBuilder.setTitle("Making changes to "+desname);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        des_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String desname = des_update_name.getText().toString().trim();
                String desdesc = des_update_description.getText().toString().trim();

                if(TextUtils.isEmpty(desname)){
                    des_update_name.setError("Name Required");
                    return;
                }
                else if (TextUtils.isEmpty(desdesc)){
                    des_update_description.setError("Description Required");
                    return;
                }
                updateDes(desid, desname, desdesc);

                alertDialog.dismiss();

            }
        });

        des_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteDessert(desid);

            }
        });
    }

    private void deleteDessert(String desid) {


        DatabaseReference database_dessert = FirebaseDatabase.getInstance().getReference("Dessert").child(desid);
        DatabaseReference database_dessert_details = FirebaseDatabase.getInstance().getReference("DessertDetails").child(desid);

        database_dessert.removeValue();
        database_dessert_details.removeValue();

        Toast.makeText(this,"Dessert item is deleted",Toast.LENGTH_SHORT).show();


    }

    private boolean updateDes(String desid, String desname, String desdescription){
        DatabaseReference database_dessert = FirebaseDatabase.getInstance().getReference("Dessert").child(desid);

        Dessert dessert = new Dessert(desid,desname,desdescription);
        database_dessert.setValue(dessert);
        Toast.makeText(this,"Dessert Updated Successfully",Toast.LENGTH_SHORT).show();
        return true;
    }

    private void addDessert(){
        String name = des_name.getText().toString().trim();
        String desc = des_description.getText().toString().trim();


        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Enter a name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(desc)){
            Toast.makeText(this,"Enter a description",Toast.LENGTH_SHORT).show();
        }
        else{

            String id_des = database_dessert.push().getKey();
            Dessert dessert = new Dessert(id_des, name, desc);
            database_dessert.child(id_des).setValue(dessert);

            Toast.makeText(DessertActivity.this,"Dessert Added Successfully",Toast.LENGTH_SHORT).show();

        }
    }
}
