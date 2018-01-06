package com.themebd.mydoctorfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PrimaryLogin extends AppCompatActivity {
    public static final String TAG="PatientLogin";
    private Switch loginOptionSelect;
    private Button getStarted;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_login);

        // For switch button
        loginOptionSelect = (Switch) findViewById(R.id.login_option_selection);
        //For button
        getStarted = (Button) findViewById(R.id.get_started_button);

        //getStartedButton onClick method
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(loginOptionSelect.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), DoctorSignInActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Intent intent = new Intent(getApplicationContext(), PatientSignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


}
