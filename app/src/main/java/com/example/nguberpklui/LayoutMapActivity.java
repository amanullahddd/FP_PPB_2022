package com.example.nguberpklui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.nguberpklui.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class LayoutMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private LocationManager lm;
    private LocationListener ll;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutmap_activity);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fr_maplayout);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        else {
            Toast.makeText(LayoutMapActivity.this, "Peta bermasalah!", Toast.LENGTH_SHORT).show();
        }

        Button btnlayoutmap = (Button) findViewById(R.id.btn_maplayout_back);
        btnlayoutmap.setOnClickListener(op);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new lokasiListener();

        if (ActivityCompat.checkSelfPermission(LayoutMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LayoutMapActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 200, ll);
        mMap.setMyLocationEnabled(true);
    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_maplayout_back:
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
}

