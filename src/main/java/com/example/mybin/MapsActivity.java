package com.example.mybin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private List<LatLng> trashCanLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // 위치 권한 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                //현재 나의 위치 찾기
                super.onLocationResult(locationResult);
                // 현재 위치 마커 생성 및 설정
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    LatLng currentLocation = new LatLng(locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude());
                    mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker in Current Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
                }
            }
        };

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            }
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 위치 권한 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            // 위치 권한이 있으면 현재 위치 표시
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        // CSV 파일에서 쓰레기통 위치 정보 읽어오기
        readTrashCanLocationsFromCSV();

        // 쓰레기통 위치 마커 표시
        showTrashCanLocationsOnMap();

        // 사용자의 위치 아이콘 크기
        float userIconSize = 48;

        // 쓰레기통 아이콘 크기
        float trashCanIconSize = 48;

        // 쓰레기통 아이콘 생성 및 크기 조정
        BitmapDescriptor trashCanIcon = BitmapDescriptorFactory.fromResource(R.drawable.trash_can);
        BitmapDescriptor trashCanIconResized = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(((BitmapDrawable)
                getResources().getDrawable(R.drawable.trash_can)).getBitmap(), (int) trashCanIconSize, (int) trashCanIconSize, false));

        // 쓰레기통 마커 생성 및 설정
        for (LatLng location : trashCanLocations) {
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("쓰레기통")
                    .icon(trashCanIconResized));
        }
    }

    private void readTrashCanLocationsFromCSV() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.trash_can);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // CSV 파일의 각 줄을 쉼표로 분리하여 배열에 저장
                try {
                    double latitude = Double.parseDouble(parts[0]); // 위도
                    double longitude = Double.parseDouble(parts[1]); // 경도
                    trashCanLocations.add(new LatLng(latitude, longitude)); // 쓰레기통 위치 추가
                } catch (NumberFormatException e) {
                    Log.e("MapsActivity", "Error parsing latitude and longitude: " + e.getMessage());
                }
            }
            reader.close();
        } catch (IOException e) {
            Log.e("MapsActivity", "Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showTrashCanLocationsOnMap() {
        for (LatLng location : trashCanLocations) {
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("쓰레기통"));
        }
    }
}