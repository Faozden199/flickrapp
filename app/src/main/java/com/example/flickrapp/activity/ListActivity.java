package com.example.flickrapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.flickrapp.adapter.MyAdapter;
import com.example.flickrapp.R;
import com.example.flickrapp.async.AsyncFlickrJSONDataForList;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

public class ListActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView list = findViewById(R.id.list);

        Log.i("TEST", "Création de l'adaptateur");

        adapter = new MyAdapter(this);
        list.setAdapter(adapter);

        Log.i("TEST", "Created");

        final Location[] loc = {null};
        AsyncTask<String, Void , JSONObject> asyncTask = new AsyncFlickrJSONDataForList(adapter);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If not granted => Ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 99);
            Log.i("perm", "Permission Granted");
            return;
        }else{
            Log.i("perm", "Permission already Granted");
        }
        /*
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        loc[0] = location;
                        Log.i("loc", "Found one :" + location);
                        if (location != null) {
                            Log.i("loc", "Nothing found");

                        }
                    }
                });

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        Log.i("loc", "Location found : " + location);
                    }
                }
            }
        };
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
*/

        LocationServices.getFusedLocationProviderClient(this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.i("loc", "Location found 2 : " + location);
                loc[0] = location;
                String url = new String("https://api.flickr.com/services/rest/?" +
                        "method=flickr.photos.search" +
                        "&api_key=3de74449966b84b74511d2831fe52e2f" +
                        "&has_geo=1&lat=" + 48.973005 +
                        "&lon=" + 2.306742 + "&format=json");
                Log.i("url", url);
                Log.i("loc", "Latitude : " + loc[0].getLatitude() + ", Longidtude :" + loc[0].getLongitude());
                asyncTask.execute(url);
            }
        });



    }
}
