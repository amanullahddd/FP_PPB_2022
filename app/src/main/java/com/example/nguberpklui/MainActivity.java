package com.example.nguberpklui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /*private RecyclerView.Adapter adapterView;
    private RecyclerView recyclerViewmost;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

       /* recyclerViewmost = findViewById(R.id.viewMostView);

        LinearLayoutManager linearLayoutManagerView = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewmost.setLayoutManager(linearLayoutManagerView);

        ArrayList<MostViewedDomain> mostViewed = new ArrayList<>();
        mostViewed.add(new MostViewedDomain("Cilok Kang Asep", "Cilok aci, tahu aci, gorengan tambah saus kacang", "pic_1"));
        mostViewed.add(new MostViewedDomain("Batagor Siomay khas Bandung", "Batagor Siomay asli Bandung menerima pesanan", "pic_2"));
        mostViewed.add(new MostViewedDomain("Batagor puyuh", "Batagor puyuh mas Hadi", "pic_3"));
        mostViewed.add(new MostViewedDomain("Cireng Otak-otak Mas Adi", "Cireng, Otak-otak, Tahu kress depan Masjid", "pic_1"));

        adapterView = new MostViewAdapter(mostViewed);
        recyclerViewmost.setAdapter(adapterView);*/

        Button btnsplashtologin = (Button) findViewById(R.id.btn_splash_to_login);
        Button btnsplashtoregister = (Button) findViewById(R.id.btn_splash_to_register);

        btnsplashtologin.setOnClickListener(op);
        btnsplashtoregister.setOnClickListener(op);
    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_splash_to_login:splashtologin();break;
                case R.id.btn_splash_to_register:splashtoregister();break;
            }
        }
    };

    private void splashtologin () {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void splashtoregister () {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }
}