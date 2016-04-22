package com.vkai.project;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.FacebookSdk;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

public class MainLoginActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);
    private GoogleApiClient mGoogleApiClient;
    Context mContext = MainLoginActivity.this;
    static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    AccountManager mAccountManager;
    String token;
    int serverCode;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";


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
        syncGoogleAccount();


       /* Button googleButton = (Button)findViewById(R.id.googlebtn);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUserAccount();//new code
            }
        });*/



        Button RegisterButton = (Button)findViewById(R.id.buttonRegister);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

   /* @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
*/







    private String[] getAccountNames() {
        mAccountManager = AccountManager.get(this);
        Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for(int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }
    public void syncGoogleAccount() {
        if(isNetworkAvailable() == true) {
            String[] accountarrs = getAccountNames();
            if(accountarrs.length > 0) {
                getTask(MainLoginActivity.this, accountarrs[0], SCOPE).execute();
            } else {
                Toast.makeText(MainLoginActivity.this, "No Google Account Sync!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainLoginActivity.this, "No Network Service!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "Available");
            return true;
        }

        Log.e("Network Testing", "Not Available");
        return false;
    }
    private GetUsernameTask getTask(MainLoginActivity activity, String email, String scope) {
        return new GetNameInForeground(activity, email, scope);
    }
    private void pickUserAccount() {//new account
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }

    String mEmail; // Received from newChooseAccountIntent(); passed to getToken()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            // Receiving a result from the AccountPicker
            if (resultCode == RESULT_OK) {
                mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                // With the account name acquired, go get the auth token
                //getUsername();
            } else if (resultCode == RESULT_CANCELED) {
                // The account picker dialog closed without selecting an account.
                // Notify users that they must pick an account to proceed.
                Toast.makeText(this, "Please pick an account to proceed", Toast.LENGTH_SHORT).show();
            }
        }
        // Handle the result from exceptions

    }
    /**
     * Attempts to retrieve the username.
     * If the account is not yet known, invoke the picker. Once the account is known,
     * start an instance of the AsyncTask to get the auth token and do work with it.
     */
   /* private void getUsername() {
        if (mEmail == null) {
            pickUserAccount();
        } else {
            if (isDeviceOnline()) {
                new GetUsernameTask(MainLoginActivity.this, mEmail, SCOPE).execute();
            } else {
                Toast.makeText(this, "The device is not online", Toast.LENGTH_LONG).show();
            }
        }
    }*/
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
