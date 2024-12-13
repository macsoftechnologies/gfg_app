package com.example.merchantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.skyfishjy.library.RippleBackground;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SearchProductsMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 2;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView addressTextView, selectlocationbtn;
    private RippleBackground rippleBackground;
    private ImageView markerImageView;
    private Marker locationMarker;
    private LatLng currentLocation;
    private PlacesClient placesClient;
    private String productsmapsAddressLine;
    private AutoCompleteTextView searchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products_map);
        addressTextView = findViewById(R.id.Location);
        rippleBackground = findViewById(R.id.rippleSearchBackground);
        markerImageView = findViewById(R.id.markerSearchImageView);
        searchBar = findViewById(R.id.searchproductsLocation_bar);
        selectlocationbtn = findViewById(R.id.selectProductLocationBtn);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapsFragment);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showLocationPermissionDialog();
        } else {
            getCurrentLocation();
        }

        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        placesClient = Places.createClient(this);

        searchBar.setOnClickListener(view -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(SearchProductsMapActivity.this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        });

        selectlocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("addresLine", productsmapsAddressLine);
                intent.putExtra("latitude", currentLocation.latitude);
                intent.putExtra("longitude", currentLocation.longitude);

                Toast.makeText(SearchProductsMapActivity.this, " "+currentLocation.latitude, Toast.LENGTH_SHORT).show();

                setResult(MapActivity.RESULT_OK, intent);
                finish();
            }
        });

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.setOnCameraIdleListener(() -> {
            LatLng center = googleMap.getCameraPosition().target;
            updateLocation(center);
            if (!rippleBackground.isRippleAnimationRunning()) {
                rippleBackground.startRippleAnimation();
            }
        });

        googleMap.setOnMapClickListener(latLng -> googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng)));
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                            updateLocation(currentLocation);
                            fusedLocationProviderClient.removeLocationUpdates(this);
                        }
                    }
                }
            }, getMainLooper());
        }
    }

    private void updateLocation(LatLng latLng) {
        if (latLng != null) {
            currentLocation = latLng;
            getAddress(latLng);
        }
    }


    private void getAddress(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                productsmapsAddressLine = String.valueOf(address.getAddressLine(0));
                addressTextView.setText(address.getAddressLine(0));
            } else {
                addressTextView.setText("No address found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            addressTextView.setText("Error getting address");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    updateLocation(latLng);
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("Places", status.getStatusMessage());
            }
        }
    }


    private void showLocationPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Location Permission Needed")
                .setMessage("This app needs the Location permission, please accept to use location functionality")
                .setPositiveButton("OK", (dialogInterface, i) -> ActivityCompat.requestPermissions(SearchProductsMapActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE))
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}