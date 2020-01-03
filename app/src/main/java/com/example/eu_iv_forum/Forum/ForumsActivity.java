package com.example.eu_iv_forum.Forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.example.eu_iv_forum.Account.AccountActivity;
import com.example.eu_iv_forum.Province.ProvinceActivity;
import com.example.eu_iv_forum.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ForumsActivity extends AppCompatActivity implements ForumCategorizeFragment.OnCateListInteractionListener,ForumSubjectFragment.OnTopicInteractionListener{

    private BottomNavigationView mainbottomNav;
    private ForumCategorizeFragment forumCategorizeFragment;
    private ForumSubjectFragment forumTopicFragment;
    private ForumTopicSingleFragment forumTopicSingleFragment;

    private String selectedCate;
    private String selectedTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums);
        mainbottomNav = findViewById(R.id.mainBottomNav);
        // FRAGMENTS
        forumCategorizeFragment = new ForumCategorizeFragment();
        forumTopicFragment = new ForumSubjectFragment();
        forumTopicSingleFragment = new ForumTopicSingleFragment();

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
                        Intent accountIntent = new Intent(ForumsActivity.this, AccountActivity.class);
                        startActivity(accountIntent);
                        return true;

                    case R.id.bottom_action_stats:
                        Intent statsIntent = new Intent(ForumsActivity.this, ProvinceActivity.class);
                        startActivity(statsIntent);
                        return true;

                    default:
                        return false;
                }
            }
        });


    }

    private void initializeFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle b = getIntent().getExtras();
        if(b != null) {
            selectedCate = b.getString("cate_id");
            selectedTopic = b.getString("topic_id");
            if (!TextUtils.isEmpty(selectedCate) && !TextUtils.isEmpty(selectedTopic)){
                forumTopicSingleFragment.setArguments(b);
                fragmentTransaction.hide(forumCategorizeFragment);
                fragmentTransaction.hide(forumTopicFragment);
                fragmentTransaction.add(R.id.forum_container,forumTopicSingleFragment);
                fragmentTransaction.show(forumTopicSingleFragment);
            }
            else if (!TextUtils.isEmpty(selectedCate)){
                forumTopicFragment.setArguments(b);
                fragmentTransaction.hide(forumCategorizeFragment);
                fragmentTransaction.hide(forumTopicSingleFragment);
                fragmentTransaction.add(R.id.forum_container, forumTopicFragment);
                fragmentTransaction.show(forumTopicFragment);
            }
        }
        else {
            fragmentTransaction.add(R.id.forum_container, forumCategorizeFragment);
            fragmentTransaction.hide(forumTopicFragment);
            fragmentTransaction.hide(forumTopicSingleFragment);
            fragmentTransaction.show(forumCategorizeFragment);
        }
        fragmentTransaction.commit();

    }

    @Override
    public void onCateSelected(String cate) {
        selectedCate = cate;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("cate_id", selectedCate);
        forumTopicFragment.setArguments(args);
        Log.d("SHOW", "onCateSelected: ");
        fragmentTransaction.hide(forumCategorizeFragment);
        fragmentTransaction.hide(forumTopicSingleFragment);
        fragmentTransaction.add(R.id.forum_container, forumTopicFragment);
        fragmentTransaction.show(forumTopicFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onTopicSelected(String topic,String cate) {
        selectedTopic = topic;
        selectedCate = cate;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("topic_id", selectedTopic);
        args.putString("cate_id", selectedCate);
        forumTopicSingleFragment.setArguments(args);
        Log.d("SHOW", "onTopicSelected: ");
        fragmentTransaction.hide(forumCategorizeFragment);
        fragmentTransaction.hide(forumTopicFragment);
        fragmentTransaction.add(R.id.forum_container,forumTopicSingleFragment);
        fragmentTransaction.show(forumTopicSingleFragment);
        fragmentTransaction.commit();
    }
}
