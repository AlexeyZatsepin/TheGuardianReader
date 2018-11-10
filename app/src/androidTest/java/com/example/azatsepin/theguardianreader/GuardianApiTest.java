package com.example.azatsepin.theguardianreader;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.azatsepin.theguardianreader.api.GuardianApi;
import com.example.azatsepin.theguardianreader.model.ListResponse;
import com.example.azatsepin.theguardianreader.model.ResponseWrapper;
import com.example.azatsepin.theguardianreader.model.SingleResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class GuardianApiTest {

    private GuardianApi api;

    @Before
    public void setup(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(GuardianApi.class);
    }

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.azatsepin.theguardianreader", appContext.getPackageName());
    }

    @Test
    public void listQueryTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        api.search(BuildConfig.API_KEY).enqueue(new Callback<ResponseWrapper<ListResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ListResponse>> call, Response<ResponseWrapper<ListResponse>> response) {
                assertTrue(response.isSuccessful());
                assertEquals(1, response.body().getResponse().getCurrentPage());
                assertEquals(10, response.body().getResponse().getResults().size());
                latch.countDown();
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ListResponse>> call, Throwable t) {
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void singleItemQueryTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        api.getArticle("us-news/2018/nov/09/matthew-whitaker-acting-attorney-general-wpm-scam",BuildConfig.DEFAULT_FIELDS ,BuildConfig.API_KEY).enqueue(new Callback<ResponseWrapper<SingleResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<SingleResponse>> call, Response<ResponseWrapper<SingleResponse>> response) {
                assertTrue(response.isSuccessful());
                assertNotNull(response.body());
                assertNotNull(response.body().getResponse().getContent());
                assertNotNull(response.body().getResponse().getContent().getFields());
                assertEquals("https://gu.com/p/9qx9k",response.body().getResponse().getContent().getFields().getShortUrl());
                latch.countDown();
            }

            @Override
            public void onFailure(Call<ResponseWrapper<SingleResponse>> call, Throwable t) {
                latch.countDown();
            }
        });
        latch.await();
    }




}
