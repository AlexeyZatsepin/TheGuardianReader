package com.example.azatsepin.theguardianreader.api;

import com.example.azatsepin.theguardianreader.model.ListResponse;
import com.example.azatsepin.theguardianreader.model.SingleResponse;
import com.example.azatsepin.theguardianreader.model.ResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GuardianApi {

    @GET("/search")
    Call<ResponseWrapper<ListResponse>> search(@Query("api-key") String key);

    @GET
    Call<ResponseWrapper<SingleResponse>> getArticle(@Url String url, @Query("show-fields") String fields, @Query("api-key") String key);

}
