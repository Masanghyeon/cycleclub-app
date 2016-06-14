package com.example.lsh.cycleclubapp;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MapActivity extends ActionBarActivity {

    Double latitude = 0.0;
    Double longitude = 0.0;
    GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map)).getMap(); //map꺼내옴

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyListener gpsListener = new MyListener(); //밑에 구현함
        long minTime = 10000; //몇초에 갱신할것인지
        float minDistance = 10; //몇m마다 갱신할 것인지

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime,
                minDistance, gpsListener);
        // GPS를 이용한 위치 요청 10초에 한번 , 100m 이동시마다 한번

        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                minTime, minDistance, gpsListener);
        // 네트워크를 이용한 위치 요청

        Toast.makeText(getApplicationContext(), "start",
                Toast.LENGTH_SHORT).show();
    }

    private class MyListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Marker marker; //마커
            List<Marker> markerList = new ArrayList<Marker>(); //마커리스트들
            mMap.clear(); //지도 클리어
            markerList.clear(); //마커 찍혔던 것 클리어
            latitude = location.getLatitude();
            longitude = location.getLongitude(); //현재 위도경도 얻어옴

            String msg = "Latitude : " + latitude + "\nLongitude:" + longitude;
            Log.i("GPSListener", msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
                    .show();
            final LatLng LOC = new LatLng(latitude, longitude); //현재 위치정보를 통해서 LatLng

            marker = mMap.addMarker(new MarkerOptions().position(LOC));  //마커가 그려질 포지션은 LOC(현재위치),
            markerList.add(marker); //마커리스트에 추가

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOC, 15));
            //아웃-> 줌으로 옮겨가는 함수 , 줌레벨 15
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

/*    private class MyListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            String msg = "Latitude : " +latitude +"\n Longitude :"+longitude;
            Log.i("GPSListener", msg);
            //  new NowLocation().execute("http://172.20.10.5:8081/CycleClub/",latitude,longitude);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            final LatLng LOC = new LatLng(latitude, longitude); //현재 위치 정보를
            mMap.addMarker(new MarkerOptions().position(LOC)); //마커가 그려질 포지션은 LOC(현재위치),

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOC, 15));
            //아웃-> 줌으로 옮겨가는 함수 , 줌레벨 15*//*
        }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
