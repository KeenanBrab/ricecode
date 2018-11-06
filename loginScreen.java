package com.example.keenan.scanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginScreen extends AppCompatActivity {

    public static final String USER_NAME = "John";

    Button mloginButton;
    EditText mUserNameInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mloginButton = (Button) findViewById(R.id.button_login);
        mUserNameInput = (EditText) findViewById(R.id.et_userName);
    }

    public void openNew(View view) {
        if (mUserNameInput.getText().toString().equals(USER_NAME)) {
            Toast.makeText(getApplicationContext(), "Logging in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            //you can add code here to send data..
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Username does not exist", Toast.LENGTH_SHORT).show();
        }
    }
}
