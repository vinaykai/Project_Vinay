package com.vkai.project;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Created by ganesh4517 on 08-04-2016.
 */
public class GetNameInForeground extends GetUsernameTask {
    GetNameInForeground(MainLoginActivity activity, String name, String scope) {
        super(activity, name, scope);
    }
    @Override
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

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }
}


