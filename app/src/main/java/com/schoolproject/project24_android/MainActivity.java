package com.schoolproject.project24_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    protected EditText usernameField;
    protected EditText passwordField;

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

            } catch(Exception e) {

            }

            return result;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        DownloadTask t = new DownloadTask();
        String result = null;
        try {
            result = t.execute("http://www.example.com").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        Log.i("Contents of URL", result);

    }

    public void loginButton(View view) {

        Log.i("Info", "Username: " + usernameField.getText() + " Password: " + passwordField.getText());

        Toast.makeText(MainActivity.this, "Hello " + usernameField.getText(), Toast.LENGTH_LONG).show();

    }

    public void registerButton(View view) {

        Log.i("Info", "Username: " + usernameField + " Password: " + passwordField);

        Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
    }

    public void goToNavigation(View view) {
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
    }

}
