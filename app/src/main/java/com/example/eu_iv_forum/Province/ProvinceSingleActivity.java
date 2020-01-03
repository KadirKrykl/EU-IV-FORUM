package com.example.eu_iv_forum.Province;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eu_iv_forum.Account.AccountActivity;
import com.example.eu_iv_forum.Forum.ForumsActivity;
import com.example.eu_iv_forum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProvinceSingleActivity extends AppCompatActivity {

    private BottomNavigationView mainbottomNav;
    private String cityId;

    private FirebaseFirestore firebaseFirestore;

    private TextView title_name;
    private TextView title_owner;
    private TextView title_continent;
    private TextView title_region;
    private TextView title_area;
    private TextView title_culture;
    private TextView title_cultureGroup;
    private TextView title_religion;
    private TextView title_development;
    private TextView title_bt;
    private TextView title_bp;
    private TextView title_bm;
    private TextView title_tradeGood;
    private TextView title_tradeNode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_single);

        mainbottomNav = findViewById(R.id.mainBottomNav);
        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.bottom_action_forum:
                        Intent forumIntent = new Intent(ProvinceSingleActivity.this, ForumsActivity.class);
                        startActivity(forumIntent);
                        return true;

                    case R.id.bottom_action_account:
                        Intent accountIntent = new Intent(ProvinceSingleActivity.this, AccountActivity.class);
                        startActivity(accountIntent);
                        return true;

                    case R.id.bottom_action_stats:
                        Intent statsIntent = new Intent(ProvinceSingleActivity.this, ProvinceActivity.class);
                        startActivity(statsIntent);
                        return true;

                    default:
                        return false;
                }
            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();


        title_name = findViewById(R.id.txt_provinceTitle);
        title_owner = findViewById(R.id.txt_province_Owner);
        title_continent = findViewById(R.id.txt_province_Continent);
        title_region = findViewById(R.id.txt_province_Region);
        title_area = findViewById(R.id.txt_province_Area);
        title_culture = findViewById(R.id.txt_province_Culture);
        title_cultureGroup = findViewById(R.id.txt_province_CultureGroup);
        title_religion = findViewById(R.id.txt_province_Religion);
        title_development = findViewById(R.id.txt_province_TotalDev);
        title_bt = findViewById(R.id.txt_province_BT);
        title_bp = findViewById(R.id.txt_province_BP);
        title_bm = findViewById(R.id.txt_province_BM);
        title_tradeGood = findViewById(R.id.txt_province_TradeGood);
        title_tradeNode = findViewById(R.id.txt_province_TradeNode);


        Bundle b = getIntent().getExtras();
        if(b != null) {
            cityId = b.getString("city_id");
        }




        firebaseFirestore.collection("Province").document(cityId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String forumTopicId = document.getId();
                        City city = document.toObject(City.class).withId(forumTopicId);
                        title_name.setText(city.getName());
                        title_owner.setText(city.getOwner());
                        title_continent.setText(city.getContinent());
                        title_region.setText(city.getRegion());
                        title_area.setText(city.getArea());
                        title_culture.setText(city.getCulture());
                        title_cultureGroup.setText(city.getCultureGroup());
                        title_religion.setText(city.getReligion());
                        title_development.setText(Integer.toString(city.getDevelopment()));
                        title_bt.setText(Integer.toString(city.getBt()));
                        title_bp.setText(Integer.toString(city.getBp()));
                        title_bm.setText(Integer.toString(city.getBm()));
                        title_tradeGood.setText(city.getTradeGood());
                        title_tradeNode.setText(city.getTradeNode());

                    }
                }
            }
        });

    }
}
