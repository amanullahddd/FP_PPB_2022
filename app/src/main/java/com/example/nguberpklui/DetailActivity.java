package com.example.nguberpklui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private Button btndetailback, btndetailmap;
    String judul, nohp, latlong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        getSupportActionBar().hide();

        judul = getIntent().getStringExtra("NAME");
        nohp = getIntent().getStringExtra("NOHP");
        String alamat = getIntent().getStringExtra("ALAMAT");
        latlong = getIntent().getStringExtra("LATLONG");
        String deskripsi = getIntent().getStringExtra("DESKRIPSI");
        int imagedetail = getIntent().getIntExtra("IMAGE", 0);

        TextView txtjudul = findViewById(R.id.txt_detail_judul);
        TextView txtnohp = findViewById(R.id.txt_detail_nohp);
        TextView txtalamat = findViewById(R.id.txt_detail_alamat);
        TextView txtdeskripsi = findViewById(R.id.txt_detail_deskripsi);
        ImageView imgdetail = findViewById(R.id.img_detail);

        btndetailback = findViewById(R.id.btn_detail_back);
        btndetailmap = findViewById(R.id.btn_detail_panggil);

        txtjudul.setText(judul);
        txtnohp.setText("WA " + nohp);
        txtalamat.setText(alamat);
        txtdeskripsi.setText(deskripsi);
        imgdetail.setImageResource(imagedetail);

        btndetailback.setOnClickListener(op);
        btndetailmap.setOnClickListener(op);

    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_detail_back:tohome();break;
                case R.id.btn_detail_panggil:tomap();break;
            }
        }
    };

    private void tohome () {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    private void tomap () {
        Intent intent = new Intent(DetailActivity.this, MapsActivity.class);

        intent.putExtra("JUDUL", judul);
        intent.putExtra("NOMORHP", nohp);
        intent.putExtra("LATLONGP", latlong);

        startActivity(intent);
    }
}