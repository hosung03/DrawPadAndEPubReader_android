package com.hosung.drawpadandepubreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ObjSvrSettingActivity extends AppCompatActivity {

    EditText serverIPText = null;
    EditText usernameText = null;
    EditText passwordText = null;
    Button saveButton = null;
    Button cancelButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obj_svr_setting);

        serverIPText = (EditText) findViewById(R.id.obj_serverip);
        usernameText = (EditText) findViewById(R.id.obj_username);
        passwordText = (EditText) findViewById(R.id.obj_password);
        saveButton = (Button) findViewById(R.id.btn_obj_save);
        cancelButton = (Button) findViewById(R.id.btn_obj_cancel);

        serverIPText.setText(MainActivity.realmServerIP);
        usernameText.setText(MainActivity.realmID);
        passwordText.setText(MainActivity.realmPasswd);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.realmServerIP = serverIPText.getText().toString();
                MainActivity.realmID = usernameText.getText().toString();
                MainActivity.realmPasswd = passwordText.getText().toString();

                MainActivity.syncServerURL = "realm://"+serverIPText.getText().toString()+":9080/~/DrawPad";
                MainActivity.syncAuthURL = "http://"+serverIPText.getText().toString()+":9080/auth";

                setResult(RESULT_OK, null);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        });
    }
}
