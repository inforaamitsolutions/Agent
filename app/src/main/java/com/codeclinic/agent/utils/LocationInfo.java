package com.codeclinic.agent.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


import com.codeclinic.agent.Interface.LocationCallBacks;

import java.util.List;
import java.util.Locale;

public class LocationInfo {

    public static double latitude = 0.00;
    public static double longitude = 0.00;

    public static String locationName;
    public static Location location = null;
    public static boolean isLocationNameCleared = true;
    public static String provinceName;

    @SuppressLint("MissingPermission")
    public static void getLastLocation(final Context context, LocationCallBacks locationCallBacks) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                Log.e("location", location.getLatitude() + "");
               LocationInfo.location = location;
                if (location != null) {
                    //latLong = new LatLng(((Activity) context).getIntent().getDoubleExtra("LATITUDE", LocationInfo.location.getLatitude()), ((Activity) context).getIntent().getDoubleExtra("LONGITUDE", LocationInfo.location.getLongitude()));
                    locationCallBacks.onLocationChange();
                }

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
    }

    public static String getCompleteAddressString(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(",");
                }

                strAdd = strReturnedAddress.toString();
                provinceName = addresses.get(0).getAdminArea();

                Log.i("Current loction address", strReturnedAddress.toString());
            } else {
                Log.i("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

}
