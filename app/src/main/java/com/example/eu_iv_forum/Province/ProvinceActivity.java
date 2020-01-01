package com.example.eu_iv_forum.Province;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.eu_iv_forum.Forum.ForumsActivity;
import com.example.eu_iv_forum.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProvinceActivity extends AppCompatActivity {

    private BottomNavigationView mainbottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);

        mainbottomNav = findViewById(R.id.mainBottomNav);
        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.bottom_action_forum:
                        Intent forumIntent = new Intent(ProvinceActivity.this, ForumsActivity.class);
                        startActivity(forumIntent);
                        return true;

                    case R.id.bottom_action_account:
                        /*
                        Intent statsIntent = new Intent(MainActivity.this, ProvinceActivity.class);
                        startActivity(statsIntent);
                        return true;
                         */

                    case R.id.bottom_action_stats:
                        Intent statsIntent = new Intent(ProvinceActivity.this, ProvinceActivity.class);
                        startActivity(statsIntent);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
}
