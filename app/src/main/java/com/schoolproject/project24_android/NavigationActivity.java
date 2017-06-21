package com.schoolproject.project24_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    public void profileButton(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }

    public void achievementsButton(View view) {
        Intent intent = new Intent(getApplicationContext(), AchievementsActivity.class);
        startActivity(intent);
    }

    public void matchHistoryButton(View view) {
        Intent intent = new Intent(getApplicationContext(), MatchHistoryActivity.class);
        startActivity(intent);
    }

}
