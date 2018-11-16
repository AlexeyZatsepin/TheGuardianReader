package com.example.azatsepin.theguardianreader.datasource.api;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.example.azatsepin.theguardianreader.BuildConfig;
import com.example.azatsepin.theguardianreader.ReaderApp;
import com.example.azatsepin.theguardianreader.domain.Article;
import com.example.azatsepin.theguardianreader.domain.ListResponse;
import com.example.azatsepin.theguardianreader.domain.ResponseWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkDataSource extends PositionalDataSource<Article> {

    private final static String TAG = NetworkDataSource.class.getSimpleName();
    private GuardianApi api;

    public NetworkDataSource(GuardianApi api) {
        this.api = api;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Article> callback) {
        Log.d(TAG, "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        api.search(BuildConfig.API_KEY, BuildConfig.DEFAULT_FIELDS,1).enqueue(new Callback<ResponseWrapper<ListResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ListResponse>> call, Response<ResponseWrapper<ListResponse>> response) {
                ReaderApp.getInstance().setLastCheckedTotal(response.body().getResponse().getTotal());
                callback.onResult(response.body().getResponse().getResults(), 0);
            }
            @Override
            public void onFailure(Call<ResponseWrapper<ListResponse>> call, Throwable t) { }
        });

    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Article> callback) {
        Log.d(TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        api.search(BuildConfig.API_KEY, BuildConfig.DEFAULT_FIELDS,params.startPosition/10).enqueue(new Callback<ResponseWrapper<ListResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ListResponse>> call, Response<ResponseWrapper<ListResponse>> response) {
                ReaderApp.getInstance().setLastCheckedTotal(response.body().getResponse().getTotal());
                callback.onResult(response.body().getResponse().getResults());
            }
            @Override
            public void onFailure(Call<ResponseWrapper<ListResponse>> call, Throwable t) { }
        });
    }

}
