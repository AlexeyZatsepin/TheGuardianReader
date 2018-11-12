package com.example.azatsepin.theguardianreader;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.azatsepin.theguardianreader.datasource.AsyncArticleRepository;
import com.example.azatsepin.theguardianreader.datasource.api.GuardianApi;
import com.example.azatsepin.theguardianreader.datasource.AppDatabase;

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
        app = this;
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
}
