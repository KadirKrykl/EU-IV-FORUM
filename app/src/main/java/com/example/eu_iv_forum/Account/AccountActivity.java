package com.example.eu_iv_forum.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eu_iv_forum.LoginActivity;
import com.example.eu_iv_forum.R;
import com.example.eu_iv_forum.SetupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private TextView user_name;

    private ImageView profile_img;

    private Button logoutButton;
    private Button editProfileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        user_name = findViewById(R.id.profile_username);
        profile_img = findViewById(R.id.profile_img);
        logoutButton = findViewById(R.id.profile_logout_btn);
        editProfileButton = findViewById(R.id.profile_edit_btn);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore.collection("Users").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    String userName = task.getResult().getString("name");
                    String userImage = task.getResult().getString("image");

                    user_name.setText(userName);

                    RequestOptions placeholderOption = new RequestOptions();
                    placeholderOption.placeholder(R.drawable.default_image);

                    Glide.with(getBaseContext()).applyDefaultRequestOptions(placeholderOption).load(userImage).into(profile_img);

                } else {

                    //Firebase Exception

                }

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent=new Intent(AccountActivity.this, SetupActivity.class);
                startActivity(editIntent);
                finish();
            }
        });
    }

    private void logOut() {

        firebaseAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(AccountActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }

}
