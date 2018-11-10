package com.example.azatsepin.theguardianreader.persistent;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.azatsepin.theguardianreader.BuildConfig;
import com.example.azatsepin.theguardianreader.api.GuardianApi;
import com.example.azatsepin.theguardianreader.model.Article;
import com.example.azatsepin.theguardianreader.model.ListResponse;
import com.example.azatsepin.theguardianreader.model.ResponseWrapper;

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
                callback.onResult(response.body().getResponse().getResults());
            }
            @Override
            public void onFailure(Call<ResponseWrapper<ListResponse>> call, Throwable t) { }
        });
    }

}