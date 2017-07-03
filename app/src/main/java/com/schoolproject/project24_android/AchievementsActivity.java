package com.schoolproject.project24_android;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AchievementsActivity extends AppCompatActivity {

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

                // Get the data
                JSONArray achievements = (JSONArray) jsonObject.get("achievements");

                // Define the views
                ArrayList<String> achievement_name_list = new ArrayList<>();
                ListView myListView = (ListView) findViewById(R.id.achievementList);

                // If there are no achievements
                if (achievements == null || achievements.length() <= 0) {
                    achievement_name_list.add("You have no achievements");
                } else {
                    // Loop over the data (sub)set
                    for (int i = 0; i < achievements.length(); i++) {
                        String achievement_name = (String) achievements.getJSONObject(i).get("name");

                        JSONObject game_set = (JSONObject) achievements.getJSONObject(i).get("game");
                        String achievement_game_name = (String) game_set.get("name");

                        achievement_name_list.add(achievement_game_name + ": " + achievement_name);

                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AchievementsActivity.this, android.R.layout.simple_list_item_1, achievement_name_list);

                myListView.setAdapter(arrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);


        DownloadTask t = new DownloadTask();
        String result = null;
        try {
            result = t.execute(GlobalVariables.API_URL + "api/v1-0/achievements/" + GlobalVariables.user_id, GlobalVariables.token).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        Log.i("Contents of URL achieve", result);
    }
}
