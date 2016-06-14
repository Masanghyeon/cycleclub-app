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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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


public class detailActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    String cname, ccode, cplace, ctime, mid;
    TextView title_data, code_data, place_data, time_data, master_data;
    ToggleButton join_bt;
    Button update_bt;
    Intent intent = getIntent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title_data = (TextView)findViewById(R.id.title_data);
        code_data = (TextView)findViewById(R.id.code_data);
        place_data = (TextView)findViewById(R.id.place_data);
        time_data = (TextView)findViewById(R.id.time_data);
        master_data = (TextView)findViewById(R.id.master_data);
        join_bt = (ToggleButton)findViewById(R.id.join_bt);
        update_bt = (Button)findViewById(R.id.update_bt);


        // list에서 putExtra 한것을 가져옴
//        cname = intent.getStringExtra("cname");
//        ccode = intent.getStringExtra("ccode");
//        cplace = intent.getStringExtra("cplace");
//        ctime = intent.getStringExtra("ctime");
//        mid = intent.getStringExtra("mid");

        // TextView에 뿌려줌(상세내용)
//        title_data.setText(cname);
//        title_data.setText(ccode);
//        title_data.setText(cplace);
//        title_data.setText(ctime);
//        title_data.setText(mid);

        // 모임 참가 버튼 이벤트
//        join_bt.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                // 모임이 참가 했을 때
//                if(join_bt.isChecked()){
//                    new ClubJoinJson().execute("http://192.168.0.3:8080/CycleClub/cycleclub/member/member.do",
//                            ccode, mid, "Ajoin");
//
//                } // 모임을 취소 했을 때
//                else {
//                    new ClubUnJoinJson().execute("http://192.168.0.3:8080/CycleClub/cycleclub/member/member.do",
//                            mid, "Aunjoin");
//                }
//            }
//        });
        join_bt.setVisibility(View.VISIBLE);
        // 모임 수정 버튼 이벤트
//        update_bt.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent updateintent = new Intent(detailActivity.this,updateActivity.class);
//                // updateActivity에 putExtra로 값 전송
//                updateintent.putExtra("cname",cname);
//                updateintent.putExtra("ccode",ccode);
//                updateintent.putExtra("cplace",cplace);
//                updateintent.putExtra("ctime",ctime);
//                updateintent.putExtra("mid",mid);
//
//                startActivity(updateintent);
//                finish();
//            }
//        });

        // 네비게이션 생성
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer. 실행시 네비게이션 그려주기
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_detail));
    }


    class ClubJoinJson extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String jsonData = "";
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);
                List<NameValuePair> clubValuePairs = new ArrayList<NameValuePair>(2);
                clubValuePairs.add(new BasicNameValuePair("ccode", params[1]));
                clubValuePairs.add(new BasicNameValuePair("mid", params[2]));
                clubValuePairs.add(new BasicNameValuePair("action", params[3]));
                httpPost.setEntity(new UrlEncodedFormEntity(clubValuePairs));

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
            Toast.makeText(getApplicationContext(), "모임 참여가 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    class ClubUnJoinJson extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String jsonData = "";
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);
                List<NameValuePair> clubValuePairs = new ArrayList<NameValuePair>(2);
                clubValuePairs.add(new BasicNameValuePair("mid", params[1]));
                clubValuePairs.add(new BasicNameValuePair("action", params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(clubValuePairs));

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
            Toast.makeText(getApplicationContext(), "모임 참여가 취소되었습니다.", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_detail, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        Intent intent;
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                intent = new Intent(detailActivity.this,updateActivity.class);
                startActivity(intent);
                finish();
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                intent = new Intent(detailActivity.this,listActivity.class);
                startActivity(intent);
                finish();
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                intent = new Intent(detailActivity.this,updateActivity.class);
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
            ((detailActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
