package com.example.irvinetaste;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.irvinetaste.utils.LocationUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//reference: https://github.com/googlemaps/android-samples/blob/master/ApiDemos/java/app/src/gms/java/com/example/mapdemo/MyLocationDemoActivity.java
public class PositionAuthActivity extends AppCompatActivity implements OnMapReadyCallback {

    //if yes, ask for permission of current phone. If no, default location is UCI location
    private Button authYesButton;
    private Button authNoButton;
    private MapView mapView;
    private double latitude = 0.0;
    private double longitude = 0.0;


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
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                System.out.println(latitude + " the location is " + longitude);
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
        final LatLng irvineLocation = new LatLng(33.6405,-117.8443);
        latitude = irvineLocation.latitude;
        longitude = irvineLocation.longitude;
        Marker IrvineMark = googleMap.addMarker(new MarkerOptions().position(irvineLocation).draggable(true));
        //constrain the original camera at Irvine
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(irvineLocation));
        googleMap.setMinZoomPreference(10);

        //TODO maybe add setMyLocation


        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);

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
