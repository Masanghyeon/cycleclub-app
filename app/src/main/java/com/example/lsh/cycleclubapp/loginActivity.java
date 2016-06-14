package com.example.lsh.cycleclubapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class loginActivity extends ActionBarActivity {
    Button joinButton,loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        joinButton = (Button) findViewById(R.id.joinButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        joinButton.setOnClickListener(listener);
        loginButton.setOnClickListener(listener);
    }

    Button.OnClickListener listener = new Button.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.joinButton:
                    intent = new Intent(loginActivity.this,joinActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.loginButton:
                    intent = new Intent(loginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;

            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
