package com.themebd.mydoctorfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorDetails extends AppCompatActivity {

    public static final String TAG="DoctorDeatails";
    private Toolbar mToolbar;
    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    // database
    private DatabaseReference mDatabase;
    private Query query;

    public String ctg;
    public String name;
    public String imageUrl;
    public String doctorId;

    //Widget
    CircleImageView profileImage;
    TextView doctorName,doctorCategory;
    ImageView chatSymbol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        Intent i = getIntent();
        name = i.getStringExtra("doctor_name");
        ctg = i.getStringExtra("doctor_category");
        imageUrl = i.getStringExtra("doctor_image");
        doctorId = i.getStringExtra("doctor_id");

        //Widget init
        profileImage = (CircleImageView) findViewById(R.id.doctor_details_profile_image);
        doctorName = (TextView) findViewById(R.id.doctor_details_name);
        doctorCategory = (TextView) findViewById(R.id.doctor_details_category);
        chatSymbol = (ImageView) findViewById(R.id.doctor_details_message);

        Picasso.with(getApplicationContext()).load(imageUrl).placeholder(R.drawable.ic_person_black_24dp).into(profileImage);
        doctorName.setText(name);
        doctorCategory.setText(ctg);

        //mProfilePicture imageview init
        final FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

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
        getSupportActionBar().setTitle("Doctor info");

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        chatSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
                String uid = currentUser.getUid();

                Intent msgActivity = new Intent(getApplicationContext(),ChatActivity.class);
                msgActivity.putExtra("doctor_name",name);
                msgActivity.putExtra("doctor_id",doctorId);
                startActivity(msgActivity);
                finish();
            }
        });
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
            startActivity(new Intent(DoctorDetails.this, PatientHome.class));
            finish(); // close this activity and return to preview activity (if there is any)
            return true;
        }
        else if(item.getItemId()==R.id.main_menu_profile){
            startActivity(new Intent(DoctorDetails.this, PatientProfile.class));
            finish();
            return true;
        }
        else if(item.getItemId()==R.id.main_menu_setting){
            startActivity(new Intent(DoctorDetails.this, PatientProfileSetting.class));
            finish();
            return true;
        }
        else if(item.getItemId()==R.id.main_menu_logout){
            mAuth.signOut();
            startActivity(new Intent(DoctorDetails.this, PrimaryLogin.class));
            finish();
            return true;
        }

        return true;
    }
}
