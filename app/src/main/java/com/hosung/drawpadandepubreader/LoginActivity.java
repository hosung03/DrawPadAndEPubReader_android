package com.hosung.drawpadandepubreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hosung.drawpadandepubreader.models.UserProfile;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

/**
 * Created by Hosung, Lee on 2017. 5. 23..
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_OBJSVR_SETTING = 0;

    private ProgressDialog progressDialog = null;

    EditText emailText = null;
    EditText passwordText = null;
    Button loginButton = null;
    TextView signupLink = null;
    TextView obssettingLink = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);
        obssettingLink = (TextView) findViewById(R.id.link_obssetting);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        obssettingLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ObjSvrSettingActivity.class);
                startActivityForResult(intent, REQUEST_OBJSVR_SETTING);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });


        // for test
        emailText.setText("test@localhost.io");
        passwordText.setText("1234");
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            //Toast.makeText(getBaseContext(), "Validate Error", Toast.LENGTH_LONG).show();
            return;
        }

        if(MainActivity.isSynced) {
            onLoginProcess();
        } else {
            loginButton.setEnabled(false);

            progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            final SyncCredentials syncCredentials = SyncCredentials.usernamePassword(MainActivity.realmID, MainActivity.realmPasswd);
            SyncUser.loginAsync(syncCredentials, MainActivity.syncAuthURL, new SyncUser.Callback() {
                @Override
                public void onSuccess(SyncUser user) {
                    final SyncConfiguration syncConfiguration
                            = new SyncConfiguration.Builder(user, MainActivity.syncServerURL).build();
                    Realm.setDefaultConfiguration(syncConfiguration);
                    MainActivity.isSynced = true;
                    //MainActivity.createInitialDataIfNeeded();

                    progressDialog.dismiss();
                    loginButton.setEnabled(true);
                    onLoginProcess();
                }

                @Override
                public void onError(ObjectServerError error) {
                    progressDialog.dismiss();
                    String errorMsg = null;
                    switch (error.getErrorCode()) {
                        case UNKNOWN_ACCOUNT:
                            errorMsg = "Account does not exists.";
                            break;
                        case INVALID_CREDENTIALS:
                            errorMsg = "User name and password does not match";
                            break;
                        default:
                            errorMsg = "Realm Server Connection Error!! ";
                    }
                    Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    loginButton.setEnabled(true);
                }
            });
        }
    }

    public void onLoginProcess() {
        Realm realm = Realm.getDefaultInstance();
        UserProfile user = realm.where(UserProfile.class)
                                .equalTo("email", emailText.getText().toString())
                                .findFirst();

        if(user!=null){
            if(user.getPasswd().equals(passwordText.getText().toString()))
                onLoginSuccess();
            else
                onLoginFailed("Your password is wrong!");
        } else  {
            onLoginFailed("Your email address is wrong!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG,"Call onActivityResult REQUEST_SIGNUP");
            }
        }
        else if (requestCode == REQUEST_OBJSVR_SETTING) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG,"Call onActivityResult REQUEST_OBJSVR_SETTING RESULT_OK");
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG,"Call onActivityResult REQUEST_OBJSVR_SETTING RESULT_CANCELED");
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserEmail",emailText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onLoginFailed(String errMsg) {
        if (errMsg!=null)
            Toast.makeText(getBaseContext(), errMsg, Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

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

        return valid;
    }
}
