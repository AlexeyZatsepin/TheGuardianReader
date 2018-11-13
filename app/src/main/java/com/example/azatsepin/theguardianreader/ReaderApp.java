package com.example.azatsepin.theguardianreader;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.Context;

import com.example.azatsepin.theguardianreader.datasource.AsyncArticleRepository;
import com.example.azatsepin.theguardianreader.datasource.api.GuardianApi;
import com.example.azatsepin.theguardianreader.datasource.AppDatabase;

import java.util.Objects;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReaderApp extends Application {

    private AsyncArticleRepository repository;
    private GuardianApi guardianApi;
    private static ReaderApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        guardianApi = retrofit.create(GuardianApi.class);

        AppDatabase db = Room.inMemoryDatabaseBuilder(
                getApplicationContext(),
                AppDatabase.class)
                .build();
        repository = new AsyncArticleRepository(db.articleDao());
        app = ReaderApp.this;
    }

    public AsyncArticleRepository getRepository() {
        return repository;
    }

    public GuardianApi getGuardianApi() {
        return guardianApi;
    }

    public static ReaderApp getInstance() {
        return app;
    }

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, UpdateService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(30 * 1000); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        Objects.requireNonNull(jobScheduler).schedule(builder.build());
    }
}
