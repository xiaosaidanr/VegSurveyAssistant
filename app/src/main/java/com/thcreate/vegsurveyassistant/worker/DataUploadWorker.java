package com.thcreate.vegsurveyassistant.worker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.upload.LandUploadService;
import com.thcreate.vegsurveyassistant.upload.PlotPictureUploadService;
import com.thcreate.vegsurveyassistant.upload.PlotUploadService;
import com.thcreate.vegsurveyassistant.upload.PointUploadService;
import com.thcreate.vegsurveyassistant.upload.SpeciesUploadService;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DataUploadWorker extends Worker {

    private static final String TAG = DataUploadWorker.class.getSimpleName();

    public DataUploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        //TODO PeriodicWork
        Log.e(TAG, "Do work!");

        //data upload process
        if (SessionManager.isLoggedIn()&&!isStopped()){

            PointUploadService pointUploadService = new PointUploadService();
            pointUploadService.start();

            LandUploadService landUploadService = new LandUploadService();
            landUploadService.start();
            if (landUploadService.isSuccess()&&!isStopped()){
                PlotUploadService plotUploadService = new PlotUploadService();
                plotUploadService.start();
                if (plotUploadService.isSuccess()&&!isStopped()){
                    PlotPictureUploadService plotPictureUploadService = new PlotPictureUploadService();
                    plotPictureUploadService.start();
                    SpeciesUploadService speciesUploadService = new SpeciesUploadService();
                    speciesUploadService.start();
                }
            }
        }

        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }

}
