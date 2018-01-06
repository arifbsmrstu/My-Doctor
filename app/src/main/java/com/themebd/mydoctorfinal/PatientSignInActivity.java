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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PatientSignInActivity extends AppCompatActivity {

    public static final String TAG="PatientSignInActivity";
    //EditText
    EditText mEmail,mPassword;
    //Button
    Button mSignIn,mSignUp;
    //Progress dialog
    ProgressDialog signInprogress;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_in);

        //Edittest init
        mEmail = (EditText) findViewById(R.id.patient_email_for_sign_in);
        mPassword = (EditText) findViewById(R.id.patient_pass_for_sign_in);
        //Button iinit
        mSignIn = (Button) findViewById(R.id.patient_sign_in_button);
        mSignUp = (Button) findViewById(R.id.patient_sign_up_button);
        signInprogress = new ProgressDialog(this);

        //Here we are checking log in sessio
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    startActivity(new Intent(PatientSignInActivity.this,PatientHome.class));
                    finish();
                }
            }
        };

        //Sign In Button onClick method
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill all requirements",Toast.LENGTH_SHORT).show();
                }
                else {
                    signInprogress.setTitle("Sign In process");
                    signInprogress.setMessage("Please wait for a while");
                    signInprogress.setCanceledOnTouchOutside(false);
                    signInprogress.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                signInprogress.dismiss();
                                startActivity(new Intent(PatientSignInActivity.this,PatientHome.class));
                                finish();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Please enter correct email and password",Toast.LENGTH_SHORT).show();
                                signInprogress.dismiss();
                            }
                        }
                    });
                }

            }
        });

        //Sign Up Button onClick method
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientSignInActivity.this,PatientSignUp.class));
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
