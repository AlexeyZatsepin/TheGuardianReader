package com.example.azatsepin.theguardianreader.datasource.api;

import com.example.azatsepin.theguardianreader.domain.ListResponse;
import com.example.azatsepin.theguardianreader.domain.SingleResponse;
import com.example.azatsepin.theguardianreader.domain.ResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GuardianApi {

    @GET("/search")
    Call<ResponseWrapper<ListResponse>> search(@Query("api-key") String key);

    @GET("/search")
    Call<ResponseWrapper<ListResponse>> search(@Query("api-key") String key,
                                               @Query("show-fields") String fields,
                                               @Query("q") String text,
                                               @Query("page") int page);

    @GET
    Call<ResponseWrapper<SingleResponse>> getArticle(@Url String url,
                                                     @Query("show-fields") String fields,
                                                     @Query("api-key") String key);

}
