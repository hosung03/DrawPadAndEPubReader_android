package com.hosung.drawpadandepubreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hosung.drawpadandepubreader.models.UserProfile;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

/**
 * Created by Hosung, Lee on 2017. 5. 23..
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private ProgressDialog progressDialog = null;

    EditText nameText = null;
    EditText emailText = null;
    EditText passwordText = null;
    EditText reEnterPasswordText = null;
    Button signupButton = null;
    TextView loginLink = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameText = (EditText) findViewById(R.id.input_name);
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        reEnterPasswordText = (EditText) findViewById(R.id.input_reEnterPassword);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        if (!validate()) {
            return;
        }

        if(MainActivity.isSynced) {
            onSignupProcess();
        } else {
            signupButton.setEnabled(false);

            progressDialog = new ProgressDialog(SignupActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            final SyncCredentials syncCredentials = SyncCredentials.usernamePassword(MainActivity.realmID, MainActivity.realmPasswd);
            SyncUser.loginAsync(syncCredentials, MainActivity.syncAuthURL, new SyncUser.Callback() {
                @Override
                public void onSuccess(SyncUser user) {
                    Log.d(TAG, "Realm Server Connection Success!");
                    final SyncConfiguration syncConfiguration
                            = new SyncConfiguration.Builder(user, MainActivity.syncServerURL).build();
                    Realm.setDefaultConfiguration(syncConfiguration);
                    MainActivity.isSynced = true;
                    //MainActivity.createInitialDataIfNeeded();

                    progressDialog.dismiss();
                    signupButton.setEnabled(true);
                    onSignupProcess();
                }

                @Override
                public void onError(ObjectServerError error) {
                    MainActivity.isSynced = false;
                    progressDialog.dismiss();
                    String errorMsg;
                    switch (error.getErrorCode()) {
                        case UNKNOWN_ACCOUNT:
                            errorMsg = "Account does not exists.";
                            Toast.makeText(getBaseContext(), errorMsg, Toast.LENGTH_LONG).show();
                            break;
                        case INVALID_CREDENTIALS:
                            errorMsg = "User name and password does not match";
                            Toast.makeText(getBaseContext(), errorMsg, Toast.LENGTH_LONG).show();
                            break;
                        default:
                            errorMsg = error.toString();
                            Toast.makeText(getBaseContext(), "Authenticating Error. Please try later", Toast.LENGTH_LONG).show();
                    }
                    Log.d(TAG, "Realm Server Connection Error: " + errorMsg);
                }
            });
        }
    }

    public void onSignupProcess() {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<UserProfile> users
                = realm.where(UserProfile.class).equalTo("email", emailText.getText().toString()).findAll();
        if(users.size() == 0){
            realm.beginTransaction();
            int nextID = 1;
            try {
                nextID = realm.where(UserProfile.class).max("id").intValue() + 1;
            } catch (Exception exp) {
                exp.printStackTrace();
            }
            final UserProfile userProfile = realm.createObject(UserProfile.class,nextID);
            userProfile.setName(nameText.getText().toString());
            userProfile.setEmail(emailText.getText().toString());
            userProfile.setPasswd(passwordText.getText().toString());
            realm.commitTransaction();

            setResult(RESULT_OK, null);
            finish();
        } else  {
            Toast.makeText(getBaseContext(), "This email is in use!", Toast.LENGTH_LONG).show();
        }
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }
}
