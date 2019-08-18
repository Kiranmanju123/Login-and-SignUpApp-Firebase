package com.example.hp.dailyshoppinglist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button btnlogin;
    private TextView signup;

    private FirebaseAuth mauth;








    private ProgressDialog mDailog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mauth=FirebaseAuth.getInstance();

        if(mauth.getCurrentUser()!=null) {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

        mDailog = new ProgressDialog(this);

        email=(EditText) findViewById(R.id.email_login);
        pass=(EditText) findViewById(R.id.password_login);
        btnlogin=(Button) findViewById(R.id.btnlogin);
        signup=(TextView) findViewById(R.id.signup_txt);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail=email.getText().toString().trim();
                String mPass=pass.getText().toString().trim();
                if(TextUtils.isEmpty(mEmail)){
                    email.setError("Required fields");
                    return;
                }

                if(TextUtils.isEmpty(mPass)){
                    pass.setError("Required fields");
                    return;
                }

                mDailog.setMessage("Processing");
                mDailog.show();

                mauth.signInWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            mDailog.dismiss();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                            mDailog.dismiss();
                        }

                    }
                });



            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });


    }
}
