package com.thcreate.vegsurveyassistant.worker;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ImageCleanWorker extends Worker {

    private static final String TAG = ImageCleanWorker.class.getSimpleName();

    public ImageCleanWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            File imageStoreDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File[] imageFiles = imageStoreDir.listFiles();
            List<PlotPictureEntity> aliveImageList = BasicApp.getAppliction().getPictureRepository().getAllAlivePlotPictureEntity();
            HashMap<String, String> aliveImagesHashMap = new HashMap<>();
            for (PlotPictureEntity entity :
                    aliveImageList) {
                aliveImagesHashMap.put(entity.localAddr, "");
            }
            for (File file :
                    imageFiles) {
                if (!aliveImagesHashMap.containsKey(file.getAbsolutePath())){
                    file.delete();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }

}
