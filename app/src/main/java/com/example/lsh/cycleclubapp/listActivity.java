package com.example.lsh.cycleclubapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class listActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    static final String KEY_CODE = "ccode";
    static final String KEY_NAME = "cname";
    static final String KEY_PLACE = "cplace";
    static final String KEY_TIME = "ctime";
    static final String KEY_ID = "mid";
    ListView list;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        list = (ListView)findViewById(R.id.listView);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_list));

        new ListJson().execute("http://172.20.10.5:8080/CycleClub/cycleclub/club/club.do","Alist");

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_list, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        Intent intent;
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                intent = new Intent(listActivity.this,insertActivity.class);
                startActivity(intent);
                finish();
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                intent = new Intent(listActivity.this,detailActivity.class);
                startActivity(intent);
                finish();
                break;
            case 4:
                mTitle = "모임 수정";
                intent = new Intent(listActivity.this,updateActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ListJson extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String jsonData = "";
            String jsonData1="";
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("action", params[1]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                jsonData = EntityUtils.toString(httpEntity);
                jsonData1 = URLDecoder.decode(jsonData,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonData1;
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            int count=0;
            String[] clubName = new String[]{};
            String[] clubTime = new String[]{};
            ArrayList<Map<String,String>> dataList = new ArrayList<Map<String, String>>();
            Map<String,String> data = new HashMap<String,String>();
            // SimpleAdapter adapter = new SimpleAdapter(this,dataList, android.R.layout.simple_list_item_2,new String[]{"name","time"},new int[]{android.R.id.text1, android.R.id.text2});

            Type collectionType = new TypeToken<ArrayList<ClubVO>>(){}.getType();
            List<ClubVO> lists = gson.fromJson(result, collectionType);
            Log.d("DATA", lists.toString());
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            ArrayList<HashMap<String, String>> clubList = new ArrayList<HashMap<String, String>>();


            for(ClubVO vo : lists) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_CODE, vo.getCcode()+"");
                map.put(KEY_NAME, vo.getCname()+"");
                map.put(KEY_PLACE,vo.getCplace()+"");
                map.put(KEY_TIME,vo.getCname()+"");
                map.put(KEY_ID, vo.getMid() + "");
                clubList.add(map);
                count++;
            }

            for(int i=0; i<count; i++){
                clubName[i] = clubList.get(i).get(KEY_NAME);
                clubTime[i] =  clubList.get(i).get(KEY_TIME);
                data.put("name",clubName[i]);
                data.put("time",clubTime[i]);
                dataList.add(data);
            }

            //        adapter.SimpleAdapter(this, dataList, android.R.layout.simple_list_item_2, new String[]{"name", "time"}, new int[]{android.R.id.text1, android.R.id.text2});
            //list.setAdapter(adapter);


        }
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((listActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}