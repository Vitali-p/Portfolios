package com.vitali.portfolios;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/*
    - All network communication in this app are done by background threads, through asynctack.
    - All relevent data are stored through preference manager.
*/

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Add classes.
    UrlClass urlFunc = new UrlClass();

    // Activities and Async Tasks.
    static MainActivity activActivity;
    static AsyncTaskUrl asyncURL;


    //Global data.
    LinearLayout mainLinLayout;
    RelativeLayout mainRelLayout;


    String url = "https://www.google.com/finance/info?q=CPH:VWS";
    String url2 = "https://www.google.com/finance/info?q=CPH:PNDORA";
    String url3 = "https://www.google.com/finance/info?q=CPH:NOVO-B";


    public SharedPreferences app_preferences;
    SharedPreferences.Editor app_editor;

    int counter;
    //Name(String), Market Name(String), Market Value(String), Time(String), Value Perc(String), Value(String).
//    ArrayList<String> mapList = new ArrayList<String>();

    // Create global styles.
    TextView textText;
    Button btText, btNet;
    MenuItem btRefreshMenuItem;
    ProgressBar progressBarMain;

    ProgressBar progressBar; // TEST
    static AsyncTaskUdskifteligAktivitet asyncTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        //Get the this app shared preferences. And derfine editor.
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        app_editor = app_preferences.edit();


        //Get the value for the run counter.
        counter = app_preferences.getInt("counter", 0);
        //Increment the counter, and commit the result.
        app_editor.putInt("counter", ++counter).commit();

        //Show how many time this app has been runned.
        try{
            //textText.setText(String.valueOf(counter));
        }catch (Exception e) {
            Toast.makeText(this, "ERROR:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }*/

        //Find all elements by ID.
        mainLinLayout = (LinearLayout) findViewById(R.id.LinLayout);
        mainRelLayout = (RelativeLayout) findViewById(R.id.RelLayout);
        btRefreshMenuItem = (MenuItem) findViewById(R.id.menu_refresh);

        btText = (Button) findViewById(R.id.bttext);
        btNet = (Button) findViewById(R.id.btnet);

        textText = (TextView) findViewById(R.id.textTest);

        progressBarMain = (ProgressBar) findViewById(R.id.progressBarID);

        // Setup styles.
        //progressBarData = new ProgressBar(this, null, android.R.attr.progressBarStyle);
//        progressBarRoundMenuItem = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
        progressBarMain.setMax(99);

        // Setup click listeners.
        btText.setOnClickListener(this);  //Start
        btNet.setOnClickListener(this);   //Cancel


        //TEST!
        btText.setText("AsyncTask with updated result");
        btNet.setText("Annullér AsyncTask");
        btNet.setVisibility(View.GONE); // hide

        // AsyncTask Config.

        activActivity = this;

        if(asyncURL != null){
            btText.setEnabled(false);
            btNet.setVisibility(View.VISIBLE);
        }


//OLD
//        // Check activity.
//        activActivity = this;
//
//        // Recycle async tack, if an old one if active.
//        if(asyncURL != null){
//            // Perform actions...
//        }

        //TEST_______________________________________________
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setMax(100);
        activActivity = this;


        // Hvis der er sket en konfigurationsændring så kan det være vi har en gammel
        // asynctask som vi skal genbruge
        if (asyncTask != null) {
            btNet.setEnabled(false);
        }





    }

    // Button events.
    @Override
    public void onClick(View v) {

//        if(v == btText){
//
//            textText.setText(null);
//            mapList.clear();
//
//            for(int i = 0; i < portLinLayout.getChildCount(); i++ ){
//                if(portLinLayout.getChildAt(i) instanceof TextView ){
//                    //String view = (TextView) portLinLayout.getChildAt(i);
//                    //mapList.put(1,(TextView) portLinLayout.getChildAt(i));
//
//                   // myTextList.add( (TextView) portLinLayout.getChildAt(i) );
//                   // myTextListID.add( portLinLayout.getId() );
//                }
//            }
//
//            textText.setText(String.valueOf(myTextList.size()));
//            textText.setText("\n"+myTextListID.toString());
//
//        }
//        if (v == btNet) {
//            asyncTask = new AsyncTaskUdskifteligAktivitet();
//            asyncTask.execute(500, 50);
//        }
        // Start async.
        if (v == btText){
            asyncURL = new AsyncTaskUrl();
            asyncURL.execute(url,url2,url3);

            btText.setEnabled(false); //Deactivate button start.
            btNet.setVisibility(View.VISIBLE); // Show stop.
        }
        // Stop async.
        if (v == btNet){
            asyncURL.cancel(false);
        }

    }

    // Menu bar, created.
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu); // or standard menu init.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.res_menu, menu);
        return true;
    }

    // Menu actions, added.
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_refresh: {
                // Refresh list of portfolios.

//                item.setActionView(new ProgressBar(this));


//                asyncURL = (AsyncTaskUrl) new AsyncTaskUrl().execute(url);

//                item.getActionView().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        item.setActionView(null);
//                    }
//                }, 300);


                return true;
            }
            case R.id.menu_add: {
                //Add intet for adding extra portfolios into list.
                addToPortfolie();
                return true;
            }
            case R.id.menu_settings: {
                //Open Wifi network settings.
                try{
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Executing intent of 'Settings' went wrong:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                return true;
            }
            case R.id.menu_help: {
                try {
                    startActivity(new Intent(this, HelpActivity.class));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Executing 'Help' went wrong:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                return true;
            }
            case R.id.menu_end: {
                moveTaskToBack(true);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void addToPortfolie (){

        TextView addName  = new TextView(MainActivity.this);
        TextView addMarketName  = new TextView(MainActivity.this);
        TextView addMarketValue  = new TextView(MainActivity.this);
        TextView addTime  = new TextView(MainActivity.this);
        TextView addValue  = new TextView(MainActivity.this);
        TextView addValuePercentage  = new TextView(MainActivity.this);

        addName.setText("COLO");

        mainLinLayout.setBackgroundColor(Color.YELLOW);
        mainLinLayout.addView(addName);
        mainLinLayout.addView(addMarketName);
        mainLinLayout.addView(addMarketValue);
        mainLinLayout.addView(addTime);
        mainLinLayout.addView(addValue);
        mainLinLayout.addView(addValuePercentage);
    }

    public void updateData () {
        try {
            String titler = app_preferences.getString("titler", "(Loading, just a moment..)"); // Hent fra prefs
            textText.setText("Loadning... this is it:\n" + titler);

            new AsyncTask() {
                //Do in background thread.
                @Override
                protected Object doInBackground(Object... arg0) {
                    try {
                        String ulr = "https://www.google.com/finance/info?q=CPH:VWS";
                        String data = readUrl(ulr); // https://www.google.com/finance

//                        String titler = findTitler(data);
                        // prefs.edit().putString("titler", titler).commit();     // Gem i prefs
//                        return titler;
                        return null;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return e;
                    }
                }

                //Do in front thread.
                @Override
                protected void onPostExecute(Object titler) {
                    textText.setText("New titler: \n" + titler);
                }
            }.execute();

        } catch (Exception e) {
            Toast.makeText(this, "ERROR:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onDestroy() {
        // Clear activities instant from the memory (Important, not to let it hang).
        activActivity = null;
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();  // Always calling the superclass method first.


        // Release the .. because we don't need it when paused
        // and other activities might need to use it.
        /*if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }*/
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();

        // save the current draft, because the activity is stopping, and we want to be sure the current drafts progress isn't lost.


        /*
        // do this update in background on an AsyncQueryHandler or equivalent
        mAsyncQueryHandler.startUpdate (
                mToken,  // int token to correlate calls
                null,    // cookie, not used here
                mUri,    // The URI for the note to update.
                values,  // The map of column names and new values to apply to them.
                null,    // No SELECT criteria are used.
                null     // No WHERE columns are used.
        );*/
    }

    static class AsyncTaskUrl extends AsyncTask <String,String,String> {
        //Do in background thread.
        @Override
        protected String doInBackground(String... urls) {
            int nrURLs = urls.length;  // How many urls to read.
            int timerDelay = 3000; //MiliSec.
            String data = null, dataSent= null;  // URL read data.

            for(int i = 0; i < nrURLs; i++){
                SystemClock.sleep(timerDelay); // Wait for 1 sec.

                if (isCancelled()){
                    return null; // Break, without result!
                }

                try {
                    data = readUrl(urls[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dataSent = findData(data);
                double percent = i * 100.0 / nrURLs;
                double timeLeft = (nrURLs - i) * timerDelay / 100 / 10.0;

                publishProgress(String.valueOf(percent),String.valueOf(timeLeft),dataSent); // Send as parameter to onProgressUpdate()
            }
            return "Done reading URLs!";
        }

        @Override
        protected  void onProgressUpdate(String... progress){
            if (activActivity == null) { return; }

            // Show progress.
            activActivity.textText.setText("Working - "+progress[0]+"% done, missing "+progress[1]+" sec"+"\n"+progress[2]);
            activActivity.progressBarMain.setProgress((int) Double.parseDouble(progress[0]));
        }

        //Do in front thread.
        @Override
        protected void onPostExecute(String result) {
            if (activActivity == null) { return; }

            activActivity.btText.setText(result);
            activActivity.btText.setEnabled(true);
            activActivity.btNet.setVisibility((View.GONE)); // Hide button.
//            textText.setText("URL titler: \n" + titler);

//            try{
//                refreshMenuItem.setActionView(null);
//
//            } catch (Exception e) {
//                System.out.println("ERROR CODE: "+e);
//            }
            asyncURL = null;
        }

        @Override
        protected void onCancelled() {
            if (activActivity == null) { return; }

            activActivity.btText.setText("Cancled not done!");
            activActivity.btText.setEnabled(true);
            activActivity.btNet.setVisibility((View.GONE)); // Hide button.

//            item.setActionView(null);
//            refreshMenu.getItem(0).setActionView(null);

            asyncURL = null;
        }
    }



    static class AsyncTaskUdskifteligAktivitet extends AsyncTask<Integer, Double, String> {

        @Override
        protected String doInBackground(Integer... param) {
            int antalSkridt = param[0];
            int ventetidPrSkridtIMilisekunder = param[1];
            for (int i = 0; i < antalSkridt; i++) {
                SystemClock.sleep(ventetidPrSkridtIMilisekunder);
                if (isCancelled()) {
                    return null; // stop uden resultat
                }
                double procent = i * 100.0 / antalSkridt;
                double resttidISekunder = (antalSkridt - i) * ventetidPrSkridtIMilisekunder / 100 / 10.0;
                publishProgress(procent, resttidISekunder); // sendes som parameter til onProgressUpdate()
            }
            return "færdig med doInBackground()!"; // resultat (String) sendes til onPostExecute()
        }

        @Override
        protected void onProgressUpdate(Double... progress) {
            if (activActivity == null) return;
            double procent = progress[0];
            double resttidISekunder = progress[1];
            String tekst = "arbejder - " + procent + "% færdig, mangler " + resttidISekunder + " sekunder endnu";
            Log.d("AsyncTask", tekst);
            activActivity.btNet.setText(tekst);
            activActivity.progressBar.setProgress((int) procent);
        }

        @Override
        protected void onPostExecute(String resultat) {
            if (activActivity == null) return;
            activActivity.btNet.setText(resultat);
            activActivity.btNet.setEnabled(true);
            asyncTask = null;
        }

        @Override
        protected void onCancelled() {
            if (activActivity == null) return;
            activActivity.btNet.setText("Annulleret før tid");
            activActivity.btNet.setEnabled(true);
            asyncTask = null;
        }
    }



    public static String readUrl(String url) throws IOException {
//        System.out.println("Reading URL: "+url);

        BufferedReader readURLbuffer = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder stringBuild = new StringBuilder();
        String lineRead = readURLbuffer.readLine();

//        System.out.println("PRINTING_NOW________________________________________________");
        while (lineRead != null) {
            stringBuild.append(lineRead + "\n");
            lineRead = readURLbuffer.readLine();

//            System.out.println(lineRead);
        }
//        System.out.println("PRINTING_END________________________________________________");

        readURLbuffer.close();
        return stringBuild.toString();
    }
    public static String findData(String urlData){
        /* Surffix names:
            e = Stock Exchange
            t = Ticker (Stock Name)
            l = Last price traded
            lt = Last trade date and time
            c = Change since close
            cp = Change percentage

          { "id": "521441849995195"
            ,"t" : "VWS"
            ,"e" : "CPH"
            ,"l" : "580.00"
            ,"l_fix" : "580.00"
            ,"l_cur" : "DKK580.00"
            ,"s": "0"
            ,"ltt":"4:59PM GMT+2"
            ,"lt" : "Apr 24, 4:59PM GMT+2"
            ,"lt_dts" : "2017-04-24T16:59:37Z"
            ,"c" : "+9.00"
            ,"c_fix" : "9.00"
            ,"cp" : "1.58"
            ,"cp_fix" : "1.58"
            ,"ccol" : "chg"
            ,"pcls_fix" : "571"
          }
          */

        String[] nrSurffix = {"t","e","lt_dts","l","c_fix","cp"};
        String stockData[] = new String[nrSurffix.length];

        for (int count = 0; count < nrSurffix.length; count++) {
            int theIndex = urlData.indexOf(nrSurffix[count]);
            int startIndex = 0, endIndex = 0, charCount = 0;

            // Look for "
            for (int atIndex = theIndex; atIndex < urlData.length(); atIndex++){
                if (urlData.charAt(atIndex) == 0x22) {charCount++; }
                if (charCount == 2 && startIndex == 0){
                    startIndex = atIndex;
                }
                if (charCount == 3){
                    endIndex = atIndex;
                    break;
                }
            }
            stockData[count] = urlData.substring(startIndex+1,endIndex);

        }
        String back = TextUtils.join(",",stockData);
        return back;
    }


    // Need onSave and onRestor.




    //___BACKUP_____________________________________
//    static class AsyncTaskUrl extends AsyncTask <String,Void,String> {
//        //Do in background thread.
//        @Override
//        protected String doInBackground(String... urls) {
//            int numberOfurls = urls.length;
//            String dataFromurl = null;
//
//            for (int i = 0; i < numberOfurls; i++) {
//
//                String data = null;
//                try {
//                    data = readUrl(urls[i]);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                dataFromurl = findData(data);
//                if (isCancelled()){ break;}
//            }
//
//            return dataFromurl;
//
//
//        }
//
//        //Do in front thread.
//        @Override
//        protected void onPostExecute(String titler) {
//            if (activActivity == null) { return; }
////            textText.setText("URL titler: \n" + titler);
//
////            try{
////                refreshMenuItem.setActionView(null);
////
////            } catch (Exception e) {
////                System.out.println("ERROR CODE: "+e);
////            }
//
//            asyncURL = null;
//        }
//
//        @Override
//        protected  void onProgressUpdate(Void... progress){
//            if (activActivity == null) { return; }
//        }
//
//        @Override
//        protected void onCancelled() {
//            if (activActivity == null) { return; }
//
//
////            item.setActionView(null);
////            refreshMenu.getItem(0).setActionView(null);
//
//
//            asyncURL = null;
//        }
//    }


}

