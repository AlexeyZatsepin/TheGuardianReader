package com.example.azatsepin.theguardianreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.azatsepin.theguardianreader.api.GuardianApi;

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
    }
}
