package com.example.nguberpklui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements RecycleViewInterface {

    private TextView txtnama;
    private FirebaseUser firebaseUser;
    private RecyclerView recyclerViewmost;
    private ImageButton imgbtnmenuback;

    int[] pedagangImage = {R.drawable.download, R.drawable.download__1_, R.drawable.picture_1526992511, R.drawable.otak_otak_mix_cireng_mercon_foto_resep_utama };

    ArrayList<PedagangModel> pedagangModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        getSupportActionBar().hide();

        txtnama = findViewById(R.id.txt_menu_nama);

        imgbtnmenuback = findViewById(R.id.imgbtn_menu_back);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser!=null) {
            txtnama.setText("Halo, " + firebaseUser.getDisplayName());
        }
        else {
            Toast.makeText(getApplicationContext(), "Akun tidak berhasil masuk!", Toast.LENGTH_SHORT).show();
        }
        setupPedagang();
        recyclerViewmost = findViewById(R.id.viewMostView);
        PKL_RecycleViewAdapter adapter = new PKL_RecycleViewAdapter(this, pedagangModels, this);
        recyclerViewmost.setAdapter(adapter);
        recyclerViewmost.setLayoutManager(new LinearLayoutManager(this));

        imgbtnmenuback.setOnClickListener(op);

    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imgbtn_menu_back:tohome();break;
            }
        }
    };

    private void setupPedagang () {
        String[] juduldagangan = getResources().getStringArray(R.array.judul_dagangan);
        String[] nohp = getResources().getStringArray(R.array.noHP);
        String[] alamatdagang = getResources().getStringArray(R.array.alamat_pedagang);
        String[] latlongdagang = getResources().getStringArray(R.array.latlong_pedagang);
        String[] ketdagang = getResources().getStringArray(R.array.keterangan_dagangan);

        for (int i=0; i<juduldagangan.length; i++ ) {
            pedagangModels.add(new PedagangModel(juduldagangan[i],
                    nohp[i], alamatdagang[i], latlongdagang[i], ketdagang[i],
                    pedagangImage[i]));
        }
    }

    private void tohome () {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MenuActivity.this, DetailActivity.class);

        intent.putExtra("NAME", pedagangModels.get(position).getJudulDagangan());
        intent.putExtra("NOHP", pedagangModels.get(position).getNoHP());
        intent.putExtra("ALAMAT", pedagangModels.get(position).getAlamatPedagang());
        intent.putExtra("LATLONG", pedagangModels.get(position).getLatLongDagang());
        intent.putExtra("DESKRIPSI", pedagangModels.get(position).getDetailKeterangan());
        intent.putExtra("IMAGE", pedagangModels.get(position).getImage());

        startActivity(intent);
    }
}