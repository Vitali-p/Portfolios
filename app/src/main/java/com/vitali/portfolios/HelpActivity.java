package com.vitali.portfolios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

/**
 * Created by Vitali
 */

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        WebView wv = (WebView) findViewById(R.id.webview);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("file:///android_asset/help.html");

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu); // or standard menu init.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.help_menu, menu);
        return true;
    }

    //Menu actions, added.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_back: {
                //Return back to the game
                finish();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
