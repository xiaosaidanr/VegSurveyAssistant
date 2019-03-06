package com.thcreate.vegsurveyassistant.worker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UploadWorker extends Worker {

    private static final String TAG = UploadWorker.class.getSimpleName();

    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        //TODO PeriodicWork
        Log.e(TAG, "testtesttest");
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }
}
