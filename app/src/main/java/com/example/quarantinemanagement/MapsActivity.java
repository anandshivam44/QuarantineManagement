package com.example.quarantinemanagement;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseReference;

    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;

    private EditText txt_Latitude;
    private EditText txt_Longitude;
    public Button update;
    String user="Random user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toast.makeText(MapsActivity.this,"Make sure your GPS is on",Toast.LENGTH_SHORT).show();
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(MapsActivity.this,"click on update",Toast.LENGTH_SHORT).show();
            }
        },2000);




        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        txt_Longitude = (EditText) findViewById(R.id.txt_longitude);
        txt_Latitude = (EditText) findViewById(R.id.txt_latitude);
        update = (Button) findViewById(R.id.btn_update);


        databaseReference = FirebaseDatabase.getInstance().getReference("Loaction");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String databaseLatitudeString=dataSnapshot.child("latitude").getValue().toString().substring(1,dataSnapshot.child("latitude").getValue().toString().length()-1);
                    String databaseLongitudeString=dataSnapshot.child("longitude").getValue().toString().substring(1,dataSnapshot.child("longitude").getValue().toString().length()-1);
/**
 * this part is important because it is retreving data and arranging*/
//processing of data.
                    String[] stringLat=databaseLatitudeString.split(",");
                    Arrays.sort(stringLat); //sorting the Array in the way the data was stored in the database.
                    String latitude=stringLat[stringLat.length-1].split("=")[1];

                    String[] stringLong=databaseLongitudeString.split(",");
                    Arrays.sort(stringLong); //sorting the Array in the way the data was stored in the database.
                    String longitude=stringLong[stringLong.length-1].split("=")[1];

                    LatLng latLng=new LatLng (Double.parseDouble(latitude),Double.parseDouble(longitude));
                    mMap.addMarker(new MarkerOptions().position(latLng).title(latitude+","+longitude));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

//                    LatLng sydney = new LatLng(-3400, 1510);
//                    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker check"));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {
                    //for getting the new location{latitude,langitude} here we have used these methods [location.getLatitude()] and [location.getLongitude()]
                    txt_Latitude.setText(Double.toString(location.getLatitude()));
                    txt_Longitude.setText(Double.toString(location.getLongitude()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public  void  updateButtonOnClick(View view)
    {
        String user=getIntent().getStringExtra("username");
        databaseReference.child("latitude").push().setValue(user+":"+txt_Latitude.getText().toString());
        databaseReference.child("longitude").push().setValue(user+":"+txt_Longitude.getText().toString());
        Toast.makeText(getApplicationContext(),"thanks for updating👍",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MapsActivity.this,MenuListActivity.class));


    }
}
