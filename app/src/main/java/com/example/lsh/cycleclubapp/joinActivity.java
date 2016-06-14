package com.example.lsh.cycleclubapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class joinActivity extends ActionBarActivity {
    EditText joinId;
    EditText joinPw;
    EditText joinPhoneNo;
    EditText joinName;
    Button joinSubmitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        joinId = (EditText)findViewById(R.id.joinId);
        joinPw = (EditText)findViewById(R.id.joinPw);
        joinName = (EditText)findViewById(R.id.joinName);
        joinPhoneNo = (EditText)findViewById(R.id.joinPhoneNo);
        joinSubmitButton = (Button) findViewById(R.id.joinSubmitButton);

        joinSubmitButton.setOnClickListener(listener);
    }

    Button.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.joinSubmitButton :
                    new JoinJson().execute("http://172.20.10.5:8080/CycleClub/cycleclub/member/member.do",
                            joinName.getText().toString(), joinId.getText().toString() , joinPw.getText().toString(),
                            joinPhoneNo.getText().toString(),"Ajoin"); //Action°ªÀº Ajoin
                    break;
            }
        }
    };

    class JoinJson extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            String jsonData = "";
            String jsonData1 = "";
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                String mname = URLEncoder.encode(params[1],"UTF-8");
                String mid = URLEncoder.encode(params[2],"UTF-8");
                String mpw = URLEncoder.encode(params[3],"UTF-8");
                String mphone = URLEncoder.encode(params[4],"UTF-8");
                String action = URLEncoder.encode(params[5],"UTF-8");
                nameValuePairs.add(new BasicNameValuePair("mname", mname));
                nameValuePairs.add(new BasicNameValuePair("mid", mid));
                nameValuePairs.add(new BasicNameValuePair("mpw", mpw));
                nameValuePairs.add(new BasicNameValuePair("mphone", mphone));
                nameValuePairs.add(new BasicNameValuePair("action", action));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                jsonData = EntityUtils.toString(httpEntity);
                jsonData1 = URLDecoder.decode(jsonData, "UTF-8");
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
            if(result.trim().equals("1")) {
                Toast.makeText(getApplicationContext(), "Register Sucess", Toast.LENGTH_SHORT).show();
            }else if(result.trim().equals("0")){
                Toast.makeText(getApplicationContext(), "Register Fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_join, menu);
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