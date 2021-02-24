package com.example.irvinetaste;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.irvinetaste.utils.LocationUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PositionAuthActivity extends AppCompatActivity implements OnMapReadyCallback {

    //if yes, ask for permission of current phone. If no, default location is UCI location
    private Button authYesButton;
    private Button authNoButton;
    private MapView mapView;
    private Marker IrvineMark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positionauth);

        authYesButton = (Button)findViewById(R.id.authyes);
        authNoButton = (Button)findViewById(R.id.authno);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        authYesButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PositionAuthActivity.this,HomepageActivity.class);
                intent.putExtra("latitude",IrvineMark.getPosition().latitude);
                intent.putExtra("longitude",IrvineMark.getPosition().longitude);
                startActivity(intent);
            }
        });

        authNoButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PositionAuthActivity.this,HomepageActivity.class);
                intent.putExtra("latitude",34.04);
                intent.putExtra("longitude",-118.15);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final LatLng irvineLocation = new LatLng(34.04,-118.15);
        IrvineMark = googleMap.addMarker(new MarkerOptions().position(irvineLocation).draggable(true));


        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
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
