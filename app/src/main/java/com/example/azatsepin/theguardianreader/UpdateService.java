package com.example.azatsepin.theguardianreader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class UpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType
                (NetworkType.CONNECTED).build();
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(UpdateWorker.class, 15, TimeUnit.MINUTES)
                .addTag(UpdateWorker.class.getSimpleName())
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance().enqueue(workRequest);
        return super.onStartCommand(intent, flags, startId);
    }
}
