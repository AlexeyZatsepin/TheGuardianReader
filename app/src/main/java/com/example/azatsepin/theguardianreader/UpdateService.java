package com.example.azatsepin.theguardianreader;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.azatsepin.theguardianreader.datasource.api.GuardianApi;
import com.example.azatsepin.theguardianreader.domain.ListResponse;
import com.example.azatsepin.theguardianreader.domain.ResponseWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class UpdateService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: ");
        final ReaderApp instance = ReaderApp.getInstance();
        GuardianApi api = instance.getGuardianApi();
        api.search(BuildConfig.API_KEY).enqueue(new Callback<ResponseWrapper<ListResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ListResponse>> call, Response<ResponseWrapper<ListResponse>> response) {
                if (response.isSuccessful()) {
                    int newTotal = response.body().getResponse().getTotal();
                    if (newTotal!= instance.getLastCheckedTotal()) {
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(getApplicationContext())
                                        .setSmallIcon(R.drawable.ic_search)
                                        .setContentTitle(getString(R.string.notification_title))
                                        .setContentText(getString(R.string.notification_text));

                        Notification notification = builder.build();

                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(1, notification);
                    }
                    instance.setLastCheckedTotal(response.body().getResponse().getTotal());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ListResponse>> call, Throwable t) { }
        });
        ReaderApp.scheduleJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
