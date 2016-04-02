package com.vkai.project;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    //The signup activity

    DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button signUp = (Button)findViewById(R.id.ButtonSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.nameText);
                EditText email = (EditText) findViewById(R.id.emailText);
                EditText pass1 = (EditText) findViewById(R.id.passwordText);
                EditText pass2 = (EditText) findViewById(R.id.confirmPassword);

                String namestr = name.getText().toString();
                String emailstr = email.getText().toString();
                String pass1str = pass1.getText().toString();
                String pass2str = pass2.getText().toString();

                if (!pass1str.equals(pass2str)) {
                    //if passwords don't match
                    Toast.makeText(SignUpActivity.this, "Passwords don't match!", Toast.LENGTH_LONG).show();
                } else {
                    //Insert the details into the database
                    Contact c = new Contact();
                    c.setName(namestr);
                    c.setEmail(emailstr);
                    c.setPassword(pass1str);

                    helper.addContact(c);
                    Toast.makeText(SignUpActivity.this,"The contact has been added",Toast.LENGTH_LONG).show();
                    //User must press back key in order to go back to sign in page
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
