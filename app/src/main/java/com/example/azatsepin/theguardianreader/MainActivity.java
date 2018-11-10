package com.example.azatsepin.theguardianreader;

import android.os.FileUriExposedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.azatsepin.theguardianreader.api.GuardianApi;
import com.example.azatsepin.theguardianreader.model.ListResponse;
import com.example.azatsepin.theguardianreader.model.ResponseWrapper;
import com.example.azatsepin.theguardianreader.model.Result;
import com.example.azatsepin.theguardianreader.model.SingleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GuardianApi api = retrofit.create(GuardianApi.class);

        api.search(BuildConfig.API_KEY).enqueue(new Callback<ResponseWrapper<ListResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ListResponse>> call, Response<ResponseWrapper<ListResponse>> response) {
                if (response.isSuccessful()) {
                    ResponseWrapper<ListResponse> fullResponse = response.body();
                    List<Result> resultList = fullResponse.getResponse().getResults();

                    api.getArticle(resultList.get(0).getId(), BuildConfig.DEFAULT_FIELDS ,BuildConfig.API_KEY).enqueue(new Callback<ResponseWrapper<SingleResponse>>() {
                        @Override
                        public void onResponse(Call<ResponseWrapper<SingleResponse>> call, Response<ResponseWrapper<SingleResponse>> response) {
                            ResponseWrapper<SingleResponse> fullResponse = response.body();
                            fullResponse.getResponse().getContent();
                        }

                        @Override
                        public void onFailure(Call<ResponseWrapper<SingleResponse>> call, Throwable t) {
                        }
                    });
                } else {
                }

            }

            @Override
            public void onFailure(Call<ResponseWrapper<ListResponse>> call, Throwable t) {
            }
        });

    }
}
