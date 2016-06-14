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
                R.id.map)).getMap(); //map������

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyListener gpsListener = new MyListener(); //�ؿ� ������
        long minTime = 10000; //���ʿ� �����Ұ�����
        float minDistance = 10; //��m���� ������ ������

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime,
                minDistance, gpsListener);
        // GPS�� �̿��� ��ġ ��û 10�ʿ� �ѹ� , 100m �̵��ø��� �ѹ�

        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                minTime, minDistance, gpsListener);
        // ��Ʈ��ũ�� �̿��� ��ġ ��û

        Toast.makeText(getApplicationContext(), "start",
                Toast.LENGTH_SHORT).show();
    }

    private class MyListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Marker marker; //��Ŀ
            List<Marker> markerList = new ArrayList<Marker>(); //��Ŀ����Ʈ��
            mMap.clear(); //���� Ŭ����
            markerList.clear(); //��Ŀ ������ �� Ŭ����
            latitude = location.getLatitude();
            longitude = location.getLongitude(); //���� �����浵 ����

            String msg = "Latitude : " + latitude + "\nLongitude:" + longitude;
            Log.i("GPSListener", msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
                    .show();
            final LatLng LOC = new LatLng(latitude, longitude); //���� ��ġ������ ���ؼ� LatLng

            marker = mMap.addMarker(new MarkerOptions().position(LOC));  //��Ŀ�� �׷��� �������� LOC(������ġ),
            markerList.add(marker); //��Ŀ����Ʈ�� �߰�

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOC, 15));
            //�ƿ�-> ������ �Űܰ��� �Լ� , �ܷ��� 15
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
            final LatLng LOC = new LatLng(latitude, longitude); //���� ��ġ ������
            mMap.addMarker(new MarkerOptions().position(LOC)); //��Ŀ�� �׷��� �������� LOC(������ġ),

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOC, 15));
            //�ƿ�-> ������ �Űܰ��� �Լ� , �ܷ��� 15*//*
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
