package com.schoolproject.project24_android;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {

            String result = "";
            String credentials = args[1] + ":" + "";
            String credBase64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT).replace("\n", "");

            try {
                URL url = new URL(args[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Authorization", "Basic " + credBase64);
                httpURLConnection.connect();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

            } catch(Exception e) {
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("resultset", result);

            try {

                JSONObject jsonObject = new JSONObject(result);

                JSONObject user_set = (JSONObject) jsonObject.get("user");
                String username = (String) user_set.get("username");
                String user_description = (String) user_set.get("description");

                TextView usernameTextView = (TextView) findViewById(R.id.profileUsername);
                usernameTextView.setText(username);

                TextView usernameDescriptionTextView = (TextView) findViewById(R.id.profileDescription);
                usernameDescriptionTextView.setText(user_description);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public class DownloadHistoryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {

            String result = "";
            String credentials = args[1] + ":" + "";
            String credBase64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT).replace("\n", "");

            try {
                URL url = new URL(args[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Authorization", "Basic " + credBase64);
                httpURLConnection.connect();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

            } catch(Exception e) {
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("resultset", result);

            try {

                JSONObject jsonObject = new JSONObject(result);

                JSONObject history_set = (JSONObject) jsonObject.get("games");
                int played = (Integer) history_set.get("played");
                int won = (Integer) history_set.get("won");
                int lost = (Integer) history_set.get("lost");



                TextView playedTextView = (TextView) findViewById(R.id.playedText);
                playedTextView.setText(Integer.toString(played));

                TextView wonTextView = (TextView) findViewById(R.id.winText);
                wonTextView.setText(Integer.toString(won));

                TextView lostTextView = (TextView) findViewById(R.id.lossText);
                lostTextView.setText(Integer.toString(lost));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DownloadTask t = new DownloadTask();
        DownloadHistoryTask t2 = new DownloadHistoryTask();
        String result = null;
        String result2 = null;
        try {
            result = t.execute(GlobalVariables.API_URL + "api/v1-0/user/" + GlobalVariables.user_id, GlobalVariables.token).get();
            result2 = t2.execute(GlobalVariables.API_URL + "api/v1-0/history/" + GlobalVariables.user_id, GlobalVariables.token).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i("Contents of URL profile", result);
    }
}
