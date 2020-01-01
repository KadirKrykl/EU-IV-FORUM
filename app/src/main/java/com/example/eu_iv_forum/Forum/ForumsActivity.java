package com.example.eu_iv_forum.Forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.eu_iv_forum.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ForumsActivity extends AppCompatActivity implements ForumCategorizeFragment.OnCateListInteractionListener{

    private BottomNavigationView mainbottomNav;
    private ForumCategorizeFragment forumCategorizeFragment;
    private ForumSubjectFragment forumTopicFragment;

    private String selectedCate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums);

        mainbottomNav = findViewById(R.id.mainBottomNav);

        // FRAGMENTS
        forumCategorizeFragment = new ForumCategorizeFragment();
        forumTopicFragment = new ForumSubjectFragment();
        initializeFragment();

        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.bottom_action_forum:
                        Intent forumIntent = new Intent(ForumsActivity.this, ForumsActivity.class);
                        startActivity(forumIntent);
                        return true;

                    case R.id.bottom_action_account:
                        /*
                        Intent forumIntent = new Intent(ForumsActivity.this, ForumsActivity.class);
                        startActivity(forumIntent);
                        return true;
                         */

                    case R.id.bottom_action_stats:
                        /*
                        Intent forumIntent = new Intent(ForumsActivity.this, ForumsActivity.class);
                        startActivity(forumIntent);
                        return true;
                        */

                    default:
                        return false;
                }
            }
        });
    }

    private void initializeFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.forum_container, forumCategorizeFragment);

        fragmentTransaction.hide(forumTopicFragment);

        fragmentTransaction.commit();

    }

    @Override
    public void onCateSelected(String cate) {
        selectedCate = cate;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("id", selectedCate);
        forumTopicFragment.setArguments(args);
        Log.d("SHOW", "onCateSelected: ");
        fragmentTransaction.hide(forumCategorizeFragment);
        fragmentTransaction.add(R.id.forum_container, forumTopicFragment);
        fragmentTransaction.show(forumTopicFragment);
        fragmentTransaction.commit();
    }



}
