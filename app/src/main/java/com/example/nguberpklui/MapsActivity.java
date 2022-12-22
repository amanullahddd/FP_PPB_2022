package com.example.nguberpklui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.nguberpklui.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private LocationManager lm;
    private LocationListener ll;
    DatabaseHelper myDB;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    double latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String latlongp = MapsActivity.this.getIntent().getStringExtra("LATLONGP");
        String[] arrSplit = latlongp.split(", ");
        latitude = Double.parseDouble(arrSplit[0]);
        longitude = Double.parseDouble(arrSplit[1]);

        myDB = new DatabaseHelper(this);


        Button btnmappanggil = (Button) findViewById(R.id.btn_map_panggil);
        Button btnmapback = (Button) findViewById(R.id.btn_map_back);
        btnmappanggil.setOnClickListener(op);
        btnmapback.setOnClickListener(op);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float hue = 120;

        LatLng lokasi = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(lokasi).title("Marker in location").icon(BitmapDescriptorFactory
                .defaultMarker(hue)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasi,13));

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new lokasiListener();

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 200, ll);
    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_map_panggil:showDialog();break;
                case R.id.btn_map_back:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                    break;
            }
        }
    };


    private class lokasiListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double gpslat = location.getLatitude();
            double gpslong = location.getLongitude();
            LatLng lokasi = new LatLng(gpslat, gpslong);
            mMap.addMarker(new MarkerOptions().position(lokasi).title("Marker in location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasi,13));
            Toast.makeText(getBaseContext(),"Lokasi Anda di tanda merah",
                    Toast.LENGTH_LONG).show();
            hitungJarak(latitude, longitude, gpslat, gpslong);

        }

        @Override
        public void onLocationChanged(@NonNull List<Location> locations) {
            LocationListener.super.onLocationChanged(locations);
        }

        @Override
        public void onFlushComplete(int requestCode) {
            LocationListener.super.onFlushComplete(requestCode);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListener.super.onStatusChanged(provider, status, extras);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    }

    private void showDialog () {
        LayoutInflater li = LayoutInflater.from(this);
        View inputnya = li.inflate(R.layout.dialog_map,null);
        AlertDialog.Builder dialognya = new AlertDialog.Builder(this);
        dialognya.setView(inputnya);
        dialognya.setTitle("Sedang perjalanan!");
        dialognya.setMessage("Pedagang meluncur...");

        String judul = MapsActivity.this.getIntent().getStringExtra("JUDUL");
        String nohp = MapsActivity.this.getIntent().getStringExtra("NOMORHP");

        final TextView txtjudul = (TextView) inputnya.findViewById(R.id.txt_mapdialog_judul);
        final TextView txtnohp = (TextView) inputnya.findViewById(R.id.txt_mapdialog_nohp);


        txtjudul.setText(judul);
        txtnohp.setText(nohp);

        Date d = new Date();
        CharSequence s  = DateFormat.format("MM-dd-yy hh:mm:ss", d.getTime());

        AddData(judul, nohp, s.toString(), "Selesai");

        dialognya
                .setCancelable(false)
                .setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateData("Dibatalkan", nohp);
                        dialog.dismiss();
                        //dialog.cancel();
                    }
                });
        dialognya.show();
    }

    public void AddData(String judul, String nohp, String tanggal, String status) {

        boolean insertData = myDB.addData(judul, nohp, tanggal, status);

        if(insertData==true){
            Toast.makeText(this, "Panggilan berhasil!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Ada masalah!", Toast.LENGTH_LONG).show();
        }
    }

    public void UpdateData(String status, String nohp) {

        boolean changeData = myDB.updateData(status, nohp);

        if(changeData==true){
            Toast.makeText(this, "Panggilan dibatalkan!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Ada masalah!", Toast.LENGTH_LONG).show();
        }
    }

    private void hitungJarak(double latAsal, Double lngAsal, double latTujuan, double lngTujuan) {
        int EARTH_RADIUS_KM = 6371;
        double lat1,lon1,lat2,lon2,lat1Rad,lat2Rad,deltaLonRad,dist;
        lat1 = latAsal;
        lon1 = lngAsal;
        lat2 = latTujuan;
        lon2 = lngTujuan;

        lat1Rad = Math.toRadians(lat1);
        lat2Rad = Math.toRadians(lat2);

        deltaLonRad = Math.toRadians(lon2 - lon1);
        dist = Math.acos(Math.sin(lat1Rad) * Math.sin(lat2Rad) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.cos(deltaLonRad)) * EARTH_RADIUS_KM;
        String strDistance = String.format("%.3f", dist);
        TextView txtmapjarak = findViewById(R.id.txt_map_jarak);
        txtmapjarak.setText("Jarak : " + strDistance + " km");
        Toast.makeText(getBaseContext(), "Jarak didapatkan!",Toast.LENGTH_LONG).show();
    }

}