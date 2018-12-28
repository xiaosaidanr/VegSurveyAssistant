package com.thcreate.vegsurveyassistant.activity;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.util.DeviceStatus;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.BaseSampleplotActivityViewModel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public class BaseSampleplotActivity<U extends BaseSampleplotActivityViewModel> extends BaseActivity {

    private final int REQUEST_IMAGE_CAPTURE = 39;
    private final int SDK_PERMISSION_REQUEST = 127;

    private String mCurrentPicturePath;
    private Uri mCurrentPictureUri;
    private String mPictureId;

    U mViewModel;

    Class<U> clazzU;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsHandleBackPressed(true);
        clazzU = (Class <U>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = ViewModelProviders.of(this).get(clazzU);
        initViewModel(savedInstanceState);
        initOnBackPressed();
    }
    private void initViewModel(Bundle savedInstanceState){
        if (savedInstanceState == null){
            savedInstanceState = new Bundle();
            Intent intent = getIntent();
            savedInstanceState.putString(Macro.SAMPLELAND_ID, intent.getStringExtra(Macro.SAMPLELAND_ID));
            savedInstanceState.putString(Macro.SAMPLELAND_TYPE, intent.getStringExtra(Macro.SAMPLELAND_TYPE));
            savedInstanceState.putString(Macro.ACTION, intent.getStringExtra(Macro.ACTION));
            savedInstanceState.putString(Macro.SAMPLEPLOT_ID, intent.getStringExtra(Macro.SAMPLEPLOT_ID));
        }
        mViewModel.init(savedInstanceState);
    }

    private void initOnBackPressed(){
        if (mViewModel.action.equals(Macro.ACTION_EDIT) || mViewModel.action.equals(Macro.ACTION_EDIT_RESTORE)){
            setmAlertDialog("放弃本次样方编辑?", null, null);
        }
        else{
            setmAlertDialog("放弃本次样方添加?", null, null);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = mViewModel.onSaveViewModelState(outState);
        super.onSaveInstanceState(outState);
    }

    public void onAutoLocation(View v){
        StringBuffer hint = new StringBuffer();
        if (!DeviceStatus.checkDeviceLocationEnabled((LocationManager)getSystemService(getApplicationContext().LOCATION_SERVICE))){
            hint.append("未打开位置开关，");
        }
        if (!DeviceStatus.checkDeviceHasInternet((ConnectivityManager)getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))){
            hint.append("无网络连接，");
        }
        if (hint.length() > 0){
            hint.append("可能导致定位失败或定位不准");
            Toast.makeText(this, hint.toString(), Toast.LENGTH_SHORT).show();
        }
        mViewModel.getLocation();
    }


    @Override
    public void onPositiveButtonPressed() {
        mViewModel.onCancel();
        super.onPositiveButtonPressed();
    }

    public void savePlot(View v){
        String result = mViewModel.save();
        if (result == null){
            finish();
        }
        else {
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }

    public void onTakePhoto(View v){
        checkTakePhotoRelatedPermissions();
    }

    private File createImageFile() throws IOException {
        //TODO userid1
        mPictureId = IdGenerator.getId(1);
        File storeDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                mPictureId,
                ".jpg",
                storeDir
        );
        mCurrentPicturePath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePhotoIntent(){
        mCurrentPicturePath = null;
        mCurrentPictureUri = null;
        mPictureId = null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            if (photoFile != null){
                if (Build.VERSION.SDK_INT >= 24){
                    mCurrentPictureUri = FileProvider.getUriForFile(
                            this,
                            "com.thcreate.vegsurveyassistant.fileprovider",
                            photoFile
                    );
                }
                else {
                    mCurrentPictureUri = Uri.fromFile(photoFile);
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPictureUri);
                mViewModel.onGoForward();
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    private void checkTakePhotoRelatedPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ArrayList<String> permissions = new ArrayList<>();
            /*
             * 摄像头权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.CAMERA);
            }
            /*
             * 写数据权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissions.size() > 0){
                ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
            else {
                dispatchTakePhotoIntent();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case SDK_PERMISSION_REQUEST:
                if (grantResults.length>0){
                    int grantedCount = 0;
                    for (int result:
                         grantResults) {
                        if (result == PackageManager.PERMISSION_GRANTED){
                            grantedCount += 1;
                        }
                    }
                    if (grantedCount == grantResults.length){
                        dispatchTakePhotoIntent();
                    }
                    else {
                        Toast.makeText(this, "获取权限失败！", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "获取权限失败！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            if (mViewModel.savePicture(mPictureId, mCurrentPicturePath)){
                Toast.makeText(this, "图片保存成功！", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "图片保存失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
