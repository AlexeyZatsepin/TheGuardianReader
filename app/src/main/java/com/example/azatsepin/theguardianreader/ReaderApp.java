package com.example.azatsepin.theguardianreader;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.azatsepin.theguardianreader.datasource.api.GuardianApi;
import com.example.azatsepin.theguardianreader.datasource.AppDatabase;
import com.example.azatsepin.theguardianreader.datasource.ArticleDao;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReaderApp extends Application {

    private ArticleDao articleDao;
    private GuardianApi guardianApi;

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
        articleDao = db.articleDao();
    }

    public ArticleDao getArticleDao() {
        return articleDao;
    }

    public GuardianApi getGuardianApi() {
        return guardianApi;
    }
}
