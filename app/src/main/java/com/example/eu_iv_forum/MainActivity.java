package com.example.eu_iv_forum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eu_iv_forum.Account.AccountActivity;
import com.example.eu_iv_forum.Forum.ForumsActivity;
import com.example.eu_iv_forum.Province.ProvinceActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button forumBtn;
    private Button statsBtn;
    private Button accountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forumBtn = (Button) findViewById(R.id.btn_menu_forum);
        statsBtn = (Button) findViewById(R.id.btn_menu_provinces);
        accountBtn = (Button) findViewById(R.id.btn_menu_account);

        forumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forumIntent = new Intent(MainActivity.this, ForumsActivity.class);
                startActivity(forumIntent);
            }
        });

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statsIntent = new Intent(MainActivity.this, ProvinceActivity.class);
                startActivity(statsIntent);
            }
        });

        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountIntent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(accountIntent);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser == null){
            Intent loginIntent = new Intent(MainActivity.this , LoginActivity.class);
            startActivity(loginIntent);
            finish();

        }
    }
}
