package com.example.eu_iv_forum.Province;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.eu_iv_forum.Account.AccountActivity;
import com.example.eu_iv_forum.Forum.ForumsActivity;
import com.example.eu_iv_forum.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ProvinceActivity extends AppCompatActivity {

    private BottomNavigationView mainbottomNav;
    private FirebaseFirestore firebaseFirestore;
    private List<City> city_list;
    private RecyclerView recyclerview;
    private CityAdapter cityAdapter;

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
                        Intent accountIntent = new Intent(ProvinceActivity.this, AccountActivity.class);
                        startActivity(accountIntent);
                        return true;

                    case R.id.bottom_action_stats:
                        Intent statsIntent = new Intent(ProvinceActivity.this, ProvinceActivity.class);
                        startActivity(statsIntent);
                        return true;

                    default:
                        return false;
                }
            }
        });


        city_list = new ArrayList<>();
        recyclerview = findViewById(R.id.province_list_view);
        cityAdapter = new CityAdapter(city_list);
        recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerview.setAdapter(cityAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Province").orderBy("name", Query.Direction.ASCENDING).addSnapshotListener(ProvinceActivity.this,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        String cityId=doc.getDocument().getId();
                        City city=doc.getDocument().toObject(City.class).withId(cityId);
                        city_list.add(city);
                        cityAdapter.notifyDataSetChanged();
                    }
                }
            }
        });




    }
}
