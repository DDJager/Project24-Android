package com.schoolproject.project24_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected EditText usernameField;
    protected EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);

    }

    public void loginButton(View view) {

        Log.i("Info", "Username: " + usernameField.getText() + " Password: " + passwordField.getText());

        Toast.makeText(MainActivity.this, "Hello " + usernameField.getText(), Toast.LENGTH_LONG).show();

    }

    public void registerButton(View view) {

        Log.i("Info", "Username: " + usernameField + " Password: " + passwordField);

        Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
    }
}
