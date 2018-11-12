package com.example.azatsepin.theguardianreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UpdateWorker extends Worker {

    public UpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i(UpdateWorker.class.getSimpleName(), "doWork: called");
        return Result.SUCCESS;
    }
}
