package com.themebd.mydoctorfinal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorList extends AppCompatActivity {

    public static final String TAG="DoctorList";
    private Toolbar mToolbar;
    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    // database
    private DatabaseReference mDatabase;
    private Query query;

    public String ctg;

    //RecyclerView
    private RecyclerView mDoctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        Intent i = getIntent();
        ctg = i.getStringExtra("doctor_category");

        //mProfilePicture imageview init
        final FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

        //Retrive from database
        mDatabase =  FirebaseDatabase.getInstance().getReference().child("Doctors");
        query = mDatabase.orderByChild("category").equalTo(ctg);

        //Firebase init
        //Firebase Auth init
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
            }
        };

        //Toolbar initialization
        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Recycler view set up for doctor list
        mDoctorList = (RecyclerView) findViewById(R.id.doctor_list_recycler);
        mDoctorList.setHasFixedSize(true);
        mDoctorList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Doctors,DoctorsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Doctors, DoctorsViewHolder>(
                Doctors.class,
                R.layout.sample_doctor_online_list,
                DoctorsViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(DoctorsViewHolder viewHolder, final Doctors doctor, int position) {


                    viewHolder.setName(doctor.getName());
                    viewHolder.setCategory(doctor.category);
                    viewHolder.setImage(doctor.getImageUrl(), getApplicationContext());
                    viewHolder.showOnline(doctor.getOnline());

                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(DoctorList.this,DoctorDetails.class);
                            i.putExtra("doctor_name",doctor.getName());
                            i.putExtra("doctor_category",doctor.getCategory());
                            i.putExtra("doctor_image",doctor.getImageUrl());
                            i.putExtra("doctor_id",doctor.getId());

                            startActivity(i);
                        }
                    });

            }
        };
        mDoctorList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class DoctorsViewHolder extends RecyclerView.ViewHolder{

        View mItem;
        public DoctorsViewHolder(View itemView) {
            super(itemView);
            mItem = itemView;
        }

        public void setName(String name){
            TextView mDoctorName = (TextView) mItem.findViewById(R.id.doctor_name);
            mDoctorName.setText(name);
        }
        public void setCategory(String ctg){
            TextView mDoctorName = (TextView) mItem.findViewById(R.id.doctor_category);
            mDoctorName.setText(ctg);
        }
        public void setImage(String url, Context con){
            CircleImageView mDoctorImage = (CircleImageView) mItem.findViewById(R.id.doctor_image);
            Picasso.with(con).load(url).placeholder(R.drawable.ic_person_black_24dp).into(mDoctorImage);
        }

        public void showOnline(Boolean b){
            ImageView onlineImage = (ImageView) mItem.findViewById(R.id.online_image);
            if(b){

                onlineImage.setImageResource(R.drawable.online);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_bar_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(DoctorList.this, PatientHome.class));
            finish(); // close this activity and return to preview activity (if there is any)
            return true;
        }
        else if(item.getItemId()==R.id.main_menu_profile){
            startActivity(new Intent(DoctorList.this, PatientProfile.class));
            finish();
            return true;
        }
        else if(item.getItemId()==R.id.main_menu_setting){
            startActivity(new Intent(DoctorList.this, PatientProfileSetting.class));
            finish();
            return true;
        }
        else if(item.getItemId()==R.id.main_menu_logout){
            mAuth.signOut();
            startActivity(new Intent(DoctorList.this, PrimaryLogin.class));
            finish();
            return true;
        }

        return true;
    }
}
