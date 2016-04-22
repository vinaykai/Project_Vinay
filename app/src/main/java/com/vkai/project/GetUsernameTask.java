package com.vkai.project;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ganesh4517 on 08-04-2016.
 */
public abstract class GetUsernameTask extends AsyncTask {
    MainLoginActivity mActivity;
    String mScope;
    String mEmail;
    protected int mRequest;
    String GOOGLE_USER_DATA = "data not found";

    GetUsernameTask(MainLoginActivity activity, String name, String scope) {
        this.mActivity = activity;
        this.mScope = scope;
        this.mEmail = name;
    }

    /**
     * Executes the asynchronous job. This runs when you call execute()
     * on the AsyncTask instance.
     */
    //@Override
    protected Void doInBackground(Void... params) {
        try {
            fetchProfileNamefromServer();
        } catch (IOException ex) {
        onError("Following error, please try again."+ex.getMessage(),ex);
        }
        catch (JSONException e) {
            onError("Following error, please try again."+e.getMessage(),e);

        }
        return null;
    }
    protected void onError(String msg,Exception e) {
        if(e!=null)
            Log.d("","Exception",e);
    }
    //protected abstract String fetchToken() throws IOException;
    private void fetchProfileNamefromServer() throws IOException,JSONException{
        String token = fetchToken();
        URL url = new URL("https://www.googleapis.com/oauth2/vl/userinfo?access_token="+token);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        int sc = con.getResponseCode();
        if(sc==200) {
            InputStream is = con.getInputStream();
            GOOGLE_USER_DATA = readResponse(is);
            is.close();
            Intent intent = new Intent(mActivity,navigation.class);
            intent.putExtra("email_id", mEmail);
            mActivity.startActivity(intent);
            mActivity.finish();
            return;
        }
        else if(sc==401) {
            GoogleAuthUtil.invalidateToken(mActivity,token);
            onError("Server authentication error",null);
            return;
        }
        else {
            onError("Returned by server"+sc,null);
            return;
        }

    }
    private static String readResponse(InputStream is)throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] data = new byte[2048];
        int len=0;
        while((len=is.read(data,0,data.length))>=0) {
        bos.write(data,0,len);
        }
        return new String(bos.toByteArray(),"UTF-8");
    }

    /**
     * Gets an authentication token from Google and handles any
     * GoogleAuthException that may occur.
     */
   // @Override
    protected String fetchToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
        } catch (GooglePlayServicesAvailabilityException playEx) {

        } catch (UserRecoverableAuthException urae) {
            mActivity.startActivityForResult(urae.getIntent(), mRequest);
        } catch (GoogleAuthException fatalException) {
            fatalException.printStackTrace();
        }

        return null;
    }
}





