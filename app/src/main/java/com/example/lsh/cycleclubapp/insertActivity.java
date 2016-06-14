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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class insertActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Button saveButton,resetButton;
    private EditText titleEdit,whereEdit,dateEdit,timeEdit,whoEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_insert));

        saveButton = (Button) findViewById(R.id.saveButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        titleEdit = (EditText) findViewById(R.id.titleEdit);
        whereEdit = (EditText) findViewById(R.id.whereEdit);
        dateEdit = (EditText) findViewById(R.id.dateEdit);
        timeEdit = (EditText) findViewById(R.id.timeEdit);
        whoEdit = (EditText) findViewById(R.id.whoEditFix);

        saveButton.setOnClickListener(listener);
        resetButton.setOnClickListener(listener);
    }

    Button.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.saveButton :
                    new insertJson().execute("http://172.20.10.5:8080/CycleClub/cycleclub/club/club.do", titleEdit.getText().toString() , whereEdit.getText().toString(),
                            dateEdit.getText().toString(),whoEdit.getText().toString(),"Ainsert");
                    break;
                case R.id.resetButton :
                    break;
            }
        }
    };

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_insert, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    class insertJson extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String jsonData = "";
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                String cname = URLEncoder.encode(params[1],"UTF-8");
                String cplace = URLEncoder.encode(params[2],"UTF-8");
                String ctime = URLEncoder.encode(params[3],"UTF-8");
                String mid = URLEncoder.encode(params[4],"UTF-8");
                String action = URLEncoder.encode(params[5],"UTF-8");
                nameValuePairs.add(new BasicNameValuePair("cname", cname));
                nameValuePairs.add(new BasicNameValuePair("cplace", cplace));
                nameValuePairs.add(new BasicNameValuePair("ctime", ctime));
                nameValuePairs.add(new BasicNameValuePair("mid", mid));
                nameValuePairs.add(new BasicNameValuePair("action", action));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                jsonData = EntityUtils.toString(httpEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonData;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.trim().equals("1")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "등록 실패", Toast.LENGTH_SHORT).show();
                //dao에서 예외처리 할것 !!
            }
        }
    }


    public void onSectionAttached(int number) {
        Intent intent;
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                intent = new Intent(insertActivity.this,listActivity.class);
                startActivity(intent);
                finish();
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                intent = new Intent(insertActivity.this,detailActivity.class);
                startActivity(intent);
                finish();
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                intent = new Intent(insertActivity.this,updateActivity.class);
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

    /**
     * A placeholder fragment containing a simple view.
     */
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
            ((insertActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}