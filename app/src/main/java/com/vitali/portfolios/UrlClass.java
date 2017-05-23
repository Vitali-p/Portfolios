package com.vitali.portfolios;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;

/**
 * Created by Vitali
 */

public class UrlClass {


//    public static String readUrl(String url) throws IOException {
////        System.out.println("Reading URL: "+url);
//
//        BufferedReader readURLbuffer = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
//        StringBuilder stringBuild = new StringBuilder();
//        String lineRead = readURLbuffer.readLine();
//
////        System.out.println("PRINTING_NOW________________________________________________");
//        while (lineRead != null) {
//            stringBuild.append(lineRead + "\n");
//             lineRead = readURLbuffer.readLine();
//
////            System.out.println(lineRead);
//        }
////        System.out.println("PRINTING_END________________________________________________");
//
//        readURLbuffer.close();
//        return stringBuild.toString();
//    }

//    public static String findData(String urlData){
//        /* Surffix names:
//            e = Stock Exchange
//            t = Ticker (Stock Name)
//            l = Last price traded
//            lt = Last trade date and time
//            c = Change since close
//            cp = Change percentage
//
//          { "id": "521441849995195"
//            ,"t" : "VWS"
//            ,"e" : "CPH"
//            ,"l" : "580.00"
//            ,"l_fix" : "580.00"
//            ,"l_cur" : "DKK580.00"
//            ,"s": "0"
//            ,"ltt":"4:59PM GMT+2"
//            ,"lt" : "Apr 24, 4:59PM GMT+2"
//            ,"lt_dts" : "2017-04-24T16:59:37Z"
//            ,"c" : "+9.00"
//            ,"c_fix" : "9.00"
//            ,"cp" : "1.58"
//            ,"cp_fix" : "1.58"
//            ,"ccol" : "chg"
//            ,"pcls_fix" : "571"
//          }
//          */
//
//        String[] nrSurffix = {"t","e","lt_dts","l","c_fix","cp"};
//        String stockData[] = new String[nrSurffix.length];
//
//        for (int count = 0; count < nrSurffix.length; count++) {
//            int theIndex = urlData.indexOf(nrSurffix[count]);
//            int startIndex = 0, endIndex = 0, charCount = 0;
//
//            // Look for "
//            for (int atIndex = theIndex; atIndex < urlData.length(); atIndex++){
//                if (urlData.charAt(atIndex) == 0x22) {charCount++; }
//                if (charCount == 2 && startIndex == 0){
//                    startIndex = atIndex;
//                }
//                if (charCount == 3){
//                    endIndex = atIndex;
//                    break;
//                }
//            }
//            stockData[count] = urlData.substring(startIndex+1,endIndex);
//
//        }
//        String back = TextUtils.join(",",stockData);
//        return back;
//    }

    // Creating new class AsyncTack.
     /* The three types used by an asynchronous task are following:
          1) Params, the type of the parameters sent to the task upon execution.
          2) Progress, the type of the progress units published during the background computation.
          3) Result, the type of the result of the background computation.
          Unused) By type "Void)
        When an asynchronous task is executed, the task goes through 4 steps; onPreExecute(), doInBackground(Params...), onProgressUpdate(Progress...), onPostExecute(Result)
     */




}
