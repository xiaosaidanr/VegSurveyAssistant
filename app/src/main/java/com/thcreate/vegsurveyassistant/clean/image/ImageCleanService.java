package com.thcreate.vegsurveyassistant.clean.image;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public class ImageCleanService {

    private static final String TAG = "ImageCleanService";

    private boolean mIsStarted;
    private CleanTask mCleanTask;
    private Context mContext;

    public ImageCleanService(Context context) {
        mIsStarted = false;
        this.mContext = context;
    }

    public void start(){
        if (!mIsStarted){
            mCleanTask = new CleanTask(mContext);
            mCleanTask.execute();
        }
    }

    public boolean isStarted(){
        return mIsStarted;
    }

    public void stop(){
        if (mCleanTask != null){
            mCleanTask.cancel(true);
        }
    }

    private class CleanTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<Context> mWeakReferenceContext;

        public CleanTask(Context context) {
            this.mWeakReferenceContext = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mIsStarted = true;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Context context = mWeakReferenceContext.get();
                File imageStoreDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mIsStarted = false;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mIsStarted = false;
        }
    }

}
