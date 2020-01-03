package com.example.eu_iv_forum.Forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eu_iv_forum.Province.ProvinceActivity;
import com.example.eu_iv_forum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ForumTopicCreate extends AppCompatActivity {
    private BottomNavigationView mainbottomNav;
    private String cateId;

    private EditText txt_title;
    private EditText txt_content;
    private Button btn_create;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String current_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_topic_create);

        mainbottomNav = findViewById(R.id.mainBottomNav);
        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.bottom_action_forum:
                        Intent forumIntent = new Intent(ForumTopicCreate.this, ForumsActivity.class);
                        startActivity(forumIntent);
                        return true;

                    case R.id.bottom_action_account:
                        /*
                        Intent forumIntent = new Intent(ForumsActivity.this, ForumsActivity.class);
                        startActivity(forumIntent);
                        return true;
                         */

                    case R.id.bottom_action_stats:
                        Intent statsIntent = new Intent(ForumTopicCreate.this, ProvinceActivity.class);
                        startActivity(statsIntent);
                        return true;

                    default:
                        return false;
                }
            }
        });

        //------------------
        //CREATE A NEW TOPIC
        //------------------

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        current_user_id = firebaseAuth.getCurrentUser().getUid();

        //Category ID received
        Bundle b = getIntent().getExtras();
        if(b != null) {
            cateId = b.getString("cate_id");
        }

        txt_title=findViewById(R.id.txt_title);
        txt_content=findViewById(R.id.txt_content);
        btn_create=findViewById(R.id.btn_topic_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = txt_title.getText().toString();
                String content = txt_content.getText().toString();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)){
                    Map<String, Object> postMap = new HashMap<>();
                    postMap.put("title", title);
                    postMap.put("content", content);
                    postMap.put("user_id", current_user_id);
                    postMap.put("time_stamp", FieldValue.serverTimestamp());

                    firebaseFirestore.collection("Forum/"+cateId+"/Topics").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                String createdId = task.getResult().getId();
                                Toast.makeText(ForumTopicCreate.this, "Post was added", Toast.LENGTH_LONG).show();
                                Intent mainIntent = new Intent(ForumTopicCreate.this, ForumsActivity.class);
                                Bundle b = new Bundle();
                                b.putString("cate_id", cateId);
                                b.putString("topic_id", createdId);
                                mainIntent.putExtras(b);
                                startActivity(mainIntent);
                                finish();

                            }
                        }
                    });
                }
            }
        });


    }
}
