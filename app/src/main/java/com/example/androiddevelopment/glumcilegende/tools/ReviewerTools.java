package com.example.androiddevelopment.glumcilegende.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androiddevelopment.glumcilegende.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by BBLOJB on 5.12.2017..
 */

public class ReviewerTools {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectionType(Integer type){
        switch (type){
            case 1:
                return "WIFI";
            case 2:
                return "Mobilni internet";
            default:
                return "";
        }
    }

    public static int calculateTimeTillNextSync(int minutes){
        return 1000 * 60 * minutes;
    }
    //Rad sa tekstualnim fajlovima u android-u
    public static void writeTF(String data, Context context, String fileName){
        try{
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public static String readFF(Context context, String file){

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(file);

            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String reciveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((reciveString = bufferedReader.readLine()) != null){
                    stringBuilder.append(reciveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    public static void fillAdapter(String[] glumci, Context context){
        //Create ArrayAdapter from the array of Strings
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.list_item, glumci);
        ListView listView = (ListView) ((Activity)context).findViewById(R.id.glumci);

        //Assign adapter to ListView
        listView.setAdapter(adapter);
    }

    public static void readFile(Context context){
        String text = ReviewerTools.readFF(context, "myfile.txt");
        String[] data = text.split("\n");

        fillAdapter(data, context);
    }

    public static boolean isFileExists(Context context, String fileName){
        File file = new File(context.getFilesDir().getAbsolutePath() + "/" + fileName);
        if(file.exists()){
            return true;
        }else {
            return false;
        }
    }
}

