package com.example.androiddevelopment.glumcilegende.async;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by BBLOJB on 7.12.2017..
 */

public class CommentService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        String title = intent.getExtras().getString("title");
        String comment = intent.getExtras().getString("comment");

        new CommentTask(getApplicationContext()).execute(title, comment);
        stopSelf();

        return START_NOT_STICKY;
    }
}
