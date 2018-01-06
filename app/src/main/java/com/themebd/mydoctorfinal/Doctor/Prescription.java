package com.themebd.mydoctorfinal.Doctor;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.StorageReference;
import com.themebd.mydoctorfinal.DoctorHome;
import com.themebd.mydoctorfinal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prescription extends AppCompatActivity {

    RadioButton a,b,c;
    EditText mSubject;

    private ArrayList<MyObject> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private MedicineListAdapter mAdapter;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //Firbase storage
    private StorageReference mImageStorage;
    // database
    private DatabaseReference mUserRef,mDatabase;
    private Query query;

    private String doctorId,senderId;

    /*
     * Change to type CustomAutoCompleteView instead of AutoCompleteTextView
     * since we are extending to customize the view and disable filter
     * The same with the XML view, type will be CustomAutoCompleteView
     */
    CustomAutoCompleteView myAutoComplete;

    // adapter for auto-complete
    ArrayAdapter<String> myAdapter;

    // for database operations
    DatabaseHandler databaseH;

    // just to add some initial value
    String[] item = new String[] {"Please search..."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        a = (RadioButton) findViewById(R.id.a);
        b= (RadioButton) findViewById(R.id.b);
        c = (RadioButton) findViewById(R.id.c);

        mSubject = (EditText) findViewById(R.id.sub);

        Intent i = getIntent();
        senderId = i.getStringExtra("sender_id");

        //Firebase init
        //Firebase Auth init
        mAuth = FirebaseAuth.getInstance();

        //Firebase init
        //Firebase Auth init
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        doctorId = mAuth.getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Doctors").child(doctorId);

        try{

            // instantiate database handler
            databaseH = new DatabaseHandler(Prescription.this);

            // put sample data to database
            insertSampleData();

            // autocompletetextview is in activity_main.xml
            myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);

            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));

            // set our adapter
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            myAutoComplete.setAdapter(myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.medicine_list);

        mAdapter = new MedicineListAdapter(this,list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    public void insertSampleData(){

        // CREATE
        databaseH.create( new MyObject("Napa") );
        databaseH.create( new MyObject("Bronkolax") );
        databaseH.create( new MyObject("Provair") );
        databaseH.create( new MyObject("Antacid") );
        databaseH.create( new MyObject("Renidin") );
        databaseH.create( new MyObject("Deltason") );
        databaseH.create( new MyObject("July") );
        databaseH.create( new MyObject("August") );
        databaseH.create( new MyObject("September") );
        databaseH.create( new MyObject("October") );
        databaseH.create( new MyObject("November") );
        databaseH.create( new MyObject("December") );
        databaseH.create( new MyObject("New Caledonia") );
        databaseH.create( new MyObject("New Zealand") );
        databaseH.create( new MyObject("Papua New Guinea") );
        databaseH.create( new MyObject("COFFEE-1K") );
        databaseH.create( new MyObject("coffee raw") );
        databaseH.create( new MyObject("authentic COFFEE") );
        databaseH.create( new MyObject("k12-coffee") );
        databaseH.create( new MyObject("view coffee") );
        databaseH.create( new MyObject("Indian-coffee-two") );

    }

    // this function is used in CustomAutoCompleteTextChangedListener.java
    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        List<MyObject> products = databaseH.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (MyObject record : products) {

            item[x] = record.objectName;
            x++;
        }

        return item;
    }

    public void addMedicine(View view){
        String name = myAutoComplete.getText().toString();
        String cm=null;

        if(!name.isEmpty()) {

            if(a.isChecked()) {
                cm = "1 + 1 + 1";
            }
            else if(b.isChecked()) {
                cm = "1 + 0 + 1";
            }
            else if(c.isChecked()) {
                cm = "0 + 0 + 1";
            }

            name = name + " \n     "+cm;

            list.add(new MyObject(name));
            mAdapter.notifyDataSetChanged();
            myAutoComplete.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(),"Please provide correct input",Toast.LENGTH_SHORT).show();
        }
    }

    public void sendPrescribe(View view){
        String medicineName = "";
        String subject = mSubject.getText().toString();

        for(int i=0;i<list.size();i++){
            medicineName = medicineName + list.get(i).objectName + " \n ";
        }

       // String message = messageTextBox.getText().toString();

        if(!medicineName.isEmpty()){

            //Insert into Database
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Prescription").child(senderId).push();

            HashMap<String, String> userMap = new HashMap<String, String>();
            userMap.put("subject",subject);
            userMap.put("medicine",medicineName);
            userMap.put("from",doctorId);
            userMap.put("to",senderId);

            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(Prescription.this,DoctorHome.class));
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if(!medicineName.isEmpty()){

            //Insert into Database
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Prescription").child(doctorId).push();

            HashMap<String, String> userMap = new HashMap<String, String>();
            userMap.put("subject",subject);
            userMap.put("medicine",medicineName);
            userMap.put("from",doctorId);
            userMap.put("to",senderId);

            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(Prescription.this,DoctorHome.class));
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

      //  Toast.makeText(getApplicationContext(),medicineName,Toast.LENGTH_SHORT).show();
    }
}
