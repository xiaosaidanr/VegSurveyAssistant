package com.thcreate.vegsurveyassistant.worker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.thcreate.vegsurveyassistant.upload.oss.PlotPictureOssService;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ImageOssWorker extends Worker {

    private static final String TAG = ImageOssWorker.class.getSimpleName();
    private PlotPictureOssService mService;

    public ImageOssWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mService = new PlotPictureOssService();
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAG, "DoImageOssWork");
        mService.start();
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
        if (mService != null){
            mService.cancel();
        }
    }
}
