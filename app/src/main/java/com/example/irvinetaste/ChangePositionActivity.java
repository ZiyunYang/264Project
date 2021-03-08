package com.example.irvinetaste;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ChangePositionActivity extends AppCompatActivity implements OnMapReadyCallback {

    //if yes, ask for permission of current phone. If no, default location is UCI location
    private Button authConfirmButton;
    private Button authCancelButton;
    private double oldLatitude;
    private double oldLongitude;
    private MapView mapView;
    private double latitude = 0.0;
    private double longitude = 0.0;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_position);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        authConfirmButton = (Button)findViewById(R.id.authconfirm);
        authCancelButton = (Button)findViewById(R.id.authcancel);
        Intent intent = getIntent();
        oldLatitude = intent.getDoubleExtra("latitude", 0);
        oldLongitude = intent.getDoubleExtra("longitude", 0);

        mapView.getMapAsync(this);

        authConfirmButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePositionActivity.this,HomepageActivity.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                System.out.println(latitude + " the location is " + longitude);
                startActivity(intent);
                finish();
            }
        });

        authCancelButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePositionActivity.this,HomepageActivity.class);
                intent.putExtra("latitude",oldLatitude);
                intent.putExtra("longitude",oldLongitude);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        final LatLng irvineLocation = new LatLng(oldLatitude,oldLongitude);
        Marker IrvineMark = googleMap.addMarker(new MarkerOptions().position(irvineLocation).draggable(true));

        //constrain the original camera at Irvine
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(irvineLocation));
        googleMap.setMinZoomPreference(10);

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener(){

            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                latitude = IrvineMark.getPosition().latitude;
                longitude = IrvineMark.getPosition().longitude;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
