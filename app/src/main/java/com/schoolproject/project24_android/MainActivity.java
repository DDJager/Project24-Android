package com.schoolproject.project24_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    protected EditText usernameField;
    protected EditText passwordField;

    public class RegisterTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                URL url = new URL(args[0]); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.connect();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", args[1]);
                jsonObject.put("password", args[2]);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(jsonObject.toString());
                wr.flush();
                wr.close();
                Log.i("Register", Integer.toString(httpURLConnection.getResponseCode()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

    }

    public class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            String credentials = args[1] + ":" + args[2];
            String credBase64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT).replace("\n", "");

            try {
                URL url = new URL(args[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Authorization", "Basic " + credBase64);
                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() == 200) {
                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);

                    int data = reader.read();

                    while (data != -1) {
                        char current = (char) data;

                        result += current;

                        data = reader.read();
                    }
                }

                Log.i("Testje123", Integer.toString(httpURLConnection.getResponseCode()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("Response", result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.isEmpty()) {
                Toast.makeText(MainActivity.this, "Wrong username/password combination", Toast.LENGTH_LONG).show();
            } else {
                try {
                    // Put the HTTP Get results in a JSONObject
                    JSONObject jsonObject = new JSONObject(result);

                    // Get the data
                    String token = (String) jsonObject.get("token");
                    JSONObject userObject = jsonObject.getJSONObject("user");
                    String user_id = userObject.getString("id");
                    String username = userObject.getString("username");

                    // Set the globals
                    GlobalVariables.token = token;
                    GlobalVariables.user_id = Integer.parseInt(user_id);
                    GlobalVariables.username = username;

                    // Display a toast message and redirect to the navigation activity
                    Toast.makeText(MainActivity.this, "Welcome " + usernameField.getText(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                    startActivity(intent);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);


    }

    public void loginButton(View view) {

//        ((GlobalVariables) this.getApplication()).setSomeVariable("foo");


        LoginTask task = new LoginTask();
        task.execute("http://145.37.150.210:5000/api/v1-0/signin", usernameField.getText().toString(), passwordField.getText().toString());


    }

    public void registerButton(View view) {

        RegisterTask task = new RegisterTask();
        task.execute("http://145.37.150.210:5000/api/v1-0/signup", usernameField.getText().toString(), passwordField.getText().toString());

        Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
    }

    public void goToNavigation(View view) {
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
    }

}
