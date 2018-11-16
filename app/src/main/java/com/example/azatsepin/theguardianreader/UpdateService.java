package com.example.azatsepin.theguardianreader;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.azatsepin.theguardianreader.datasource.api.GuardianApi;
import com.example.azatsepin.theguardianreader.domain.ListResponse;
import com.example.azatsepin.theguardianreader.domain.ResponseWrapper;

import java.util.function.Supplier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class UpdateService extends JobService implements LifecycleObserver {

    private final String NOTIFICATION_CHANNEL_ID = "service_chanel";
    private Runnable action;

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded() {
        action = this::notifyActivity;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        action = this::pushNotification;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: ");
        final ReaderApp instance = ReaderApp.getInstance();
        GuardianApi api = instance.getGuardianApi();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        api.search(BuildConfig.API_KEY).enqueue(new Callback<ResponseWrapper<ListResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ListResponse>> call, Response<ResponseWrapper<ListResponse>> response) {
                if (response.isSuccessful()) {
                    int newTotal = response.body().getResponse().getTotal();
                    Log.i(UpdateService.class.getSimpleName(),ProcessLifecycleOwner.get().getLifecycle().getCurrentState().name());
                    if (newTotal!= instance.getLastCheckedTotal() && action != null) {
                        action.run();
                    }
                    instance.setLastCheckedTotal(response.body().getResponse().getTotal());
                }
                ProcessLifecycleOwner.get().getLifecycle().removeObserver(UpdateService.this);
                ReaderApp.scheduleJob(getApplicationContext());
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ListResponse>> call, Throwable t) { }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private void notifyActivity() {
        sendBroadcast(new Intent(BuildConfig.BROADCAST_ACTION));
    }

    private void pushNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_search)
                        .setContentTitle(getString(R.string.notification_title))
                        .setContentText(getString(R.string.notification_text));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }
}
