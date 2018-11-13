package com.example.azatsepin.theguardianreader;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import static android.support.constraint.Constraints.TAG;

public class UpdateService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob: ");

        ReaderApp.scheduleJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}
