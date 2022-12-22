package com.example.nguberpklui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button btntxttomenu, btntxttohistory, btntxttomap, btntxttoaccount;
    private ImageButton btnimgtomenu, btnimgtohistory, btnimgtomap, btnimgtoaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        getSupportActionBar().hide();

        btnimgtomenu = findViewById(R.id.btn_img_to_menu);
        btnimgtohistory = findViewById(R.id.btn_img_to_history);
        btnimgtomap = findViewById(R.id.btn_img_to_map);
        btnimgtoaccount = findViewById(R.id.btn_img_to_account);

        btntxttomenu = findViewById(R.id.btn_txt_to_menu);
        btntxttohistory = findViewById(R.id.btn_txt_to_history);
        btntxttomap = findViewById(R.id.btn_txt_to_map);
        btntxttoaccount = findViewById(R.id.btn_txt_to_account);

        btnimgtomenu.setOnClickListener(op);
        btntxttomenu.setOnClickListener(op);
        btnimgtohistory.setOnClickListener(op);
        btntxttohistory.setOnClickListener(op);
        btnimgtomap.setOnClickListener(op);
        btntxttomap.setOnClickListener(op);
        btnimgtoaccount.setOnClickListener(op);
        btntxttoaccount.setOnClickListener(op);

    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_img_to_menu:
                case R.id.btn_txt_to_menu:
                    tomenu();break;
                case R.id.btn_img_to_account:
                case R.id.btn_txt_to_account:
                    toaccount();break;
                case R.id.btn_img_to_history:
                case R.id.btn_txt_to_history:
                    tohistory();break;
                case R.id.btn_img_to_map:
                case R.id.btn_txt_to_map:
                    tomap();break;
            }
        }
    };


    private void tomenu () {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }

    private void tohistory () {
        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
    }

    private void tomap () {
        startActivity(new Intent(getApplicationContext(), LayoutMapActivity.class));

    }

    private void toaccount () {
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }
}