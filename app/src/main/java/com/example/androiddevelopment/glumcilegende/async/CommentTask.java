package com.example.androiddevelopment.glumcilegende.async;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by BBLOJB on 7.12.2017..
 */

public class CommentTask extends AsyncTask<String, Void, String[]>{

    private Context context;

    public CommentTask(Context context){
        this.context = context;
    }

    @Override
    protected String[] doInBackground(String... params) {

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return params;
    }

    @Override
    protected void onPostExecute(String[] s){
        super.onPostExecute(s);

        Intent ints = new Intent("MY_COMMENT");
        ints.putExtra("title", s[0]);
        ints.putExtra("comment", s[1]);
        context.sendBroadcast(ints);
    }
}
