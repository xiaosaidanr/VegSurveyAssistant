package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.clean.image.ImageCleanService;
import com.thcreate.vegsurveyassistant.db.entity.UserEntity;
import com.thcreate.vegsurveyassistant.repository.UserRepository;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.upload.LandUploadService;
import com.thcreate.vegsurveyassistant.upload.PlotUploadService;
import com.thcreate.vegsurveyassistant.upload.PointUploadService;
import com.thcreate.vegsurveyassistant.upload.SpeciesUploadService;
import com.thcreate.vegsurveyassistant.upload.oss.PlotPictureOssService;

public class MyFragmentViewModel extends AndroidViewModel {

    public LiveData<UserEntity> user;

    private UserRepository mUserRepository;

    private ImageCleanService mImageCleanService;

    public MyFragmentViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = ((BasicApp)application).getUserRepository();
        mImageCleanService = new ImageCleanService(getApplication());
        int userId = SessionManager.getLoggedInUserId();
        user = mUserRepository.getUserByIdAsync(userId);
    }

    public void logout(View view){
        ((BasicApp)getApplication()).Logout();
    }

    public void clean(View view){
        mImageCleanService.start();
        Toast.makeText(view.getContext(), getApplication().getResources().getString(R.string.clean_success), Toast.LENGTH_SHORT).show();
    }

    public void uploadTest(View view){
        UploadTestTask uploadTestTask = new UploadTestTask();
        uploadTestTask.execute();
    }

    private class UploadTestTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
//            PointUploadService pointUploadService = new PointUploadService();
//            pointUploadService.start();
//            LandUploadService landUploadService = new LandUploadService();
//            landUploadService.start();
//            if (landUploadService.isSuccess()) {
//                PlotUploadService plotUploadService = new PlotUploadService();
//                plotUploadService.start();
//                if (plotUploadService.isSuccess()) {
//                    SpeciesUploadService speciesUploadService = new SpeciesUploadService();
//                    speciesUploadService.start();
//                }
//            }
            PlotPictureOssService plotPictureOssService = new PlotPictureOssService();
            plotPictureOssService.start();
            return null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mImageCleanService.stop();
    }
}
