package com.vkai.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;

public class MainLoginActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText eText = (EditText)findViewById(R.id.emailText);
        final EditText pText = (EditText)findViewById(R.id.passwordText);
        Button SignInButton = (Button)findViewById(R.id.button);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If sign in button is clicked
                if(eText.getText().toString().trim().equals("") || pText.getText().toString().trim().equals("")) {
                    //if email address or password is empty
                    Toast.makeText(getApplicationContext(),"Please enter all the details in order to log in",Toast.LENGTH_LONG).show();
                }
                else {
                    String a = eText.getText().toString(); //Taking the string value of the email address
                    String b = pText.getText().toString(); //Taking the string value of the password
                    String password = helper.searchPass(a);
                    if(password.equals(b)) {
                        Intent intent = new Intent(MainLoginActivity.this, navigation.class);
                        intent.putExtra("email",a);
                        startActivity(intent);
                        MainLoginActivity.this.finish(); //used so that user can't go back to login screen once back key is pressed
                    }
                    else
                    {
                        Toast.makeText(MainLoginActivity.this,"Email address and password not correct",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Button RegisterButton = (Button)findViewById(R.id.buttonRegister);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
