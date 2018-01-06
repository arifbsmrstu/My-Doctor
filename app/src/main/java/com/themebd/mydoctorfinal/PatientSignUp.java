package com.themebd.mydoctorfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PatientSignUp extends AppCompatActivity {

    public static final String TAG = "PatientSignUp";
    //Button
    Button signUp;
    //EditText
    EditText mEmail,mPassword,mConfirmPasswor,mUserName,mMobile;
    //ProgressBar
    private ProgressBar mProgressBar;
    //Progress dialog
    ProgressDialog signUpProgress;
    //Firebase Auth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //Firebase Database
    // Write a message to the database
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);

        //Firebase Auth init
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        //Button initialization
        signUp = (Button)findViewById(R.id.sign_up_button_reg_page);
        //EditText initialization
        mEmail = (EditText)findViewById(R.id.email_for_sign_up);
        mPassword = (EditText)findViewById(R.id.pass_for_sign_up);
        mConfirmPasswor = (EditText)findViewById(R.id.confirm_pass_for_sign_up);
        mUserName = (EditText)findViewById(R.id.user_name_for__sign_up);
        mMobile = (EditText)findViewById(R.id.phone_for__sign_up);
        
        signUpProgress = new ProgressDialog(this);

        //Sign Up Button onClick method
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                String conPass = mConfirmPasswor.getText().toString();
                final String name = mUserName.getText().toString();
                final String mobile = mMobile.getText().toString();

                if(!pass.equals(conPass)){
                    Toast.makeText(getApplicationContext(),"Passwords are not same",Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty() || pass.isEmpty() || conPass.isEmpty() || name.isEmpty() || mobile.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill all requirements.",Toast.LENGTH_SHORT).show();
                }
                else{
                    signUpProgress.setTitle("Sign Up process");
                    signUpProgress.setMessage("Please wait for a while");
                    signUpProgress.setCanceledOnTouchOutside(false);
                    signUpProgress.show();
                    mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
                                String uid = currentUser.getUid();

                                //Insert into Database
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                                HashMap<String, String> userMap = new HashMap<String, String>();
                                userMap.put("name",name);
                                userMap.put("mobile",mobile);
                                userMap.put("image","Default");
                                userMap.put("status","Patient");
                                mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            signUpProgress.dismiss();
                                            startActivity(new Intent(PatientSignUp.this, PatientHome.class));
                                            finish();
                                        }
                                        else{
                                            signUpProgress.dismiss();
                                            Toast.makeText(getApplicationContext(),"Registration failed.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Registration failed.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
