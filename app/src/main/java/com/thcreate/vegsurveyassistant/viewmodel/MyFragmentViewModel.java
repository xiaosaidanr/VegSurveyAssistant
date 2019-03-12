package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.clean.image.ImageCleanService;
import com.thcreate.vegsurveyassistant.db.entity.UserEntity;
import com.thcreate.vegsurveyassistant.repository.UserRepository;
import com.thcreate.vegsurveyassistant.service.SessionManager;

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

    @Override
    protected void onCleared() {
        super.onCleared();
        mImageCleanService.stop();
    }
}
