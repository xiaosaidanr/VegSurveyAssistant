package com.thcreate.vegsurveyassistant.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;
import com.thcreate.vegsurveyassistant.util.HTTP;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class PlotPicturePreviewActivity extends AppCompatActivity {

    private static final String TAG = "PlotPicturePreviewActivity";

    private ImageDownloadAsyncTask mImageDownloadAsyncTask;

    PhotoView photoView;
    String imageLocalPath;
    String imageRemotePath;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_preview);
        photoView = findViewById(R.id.photo_view);
        progressBar = findViewById(R.id.progress_bar);
        if (savedInstanceState == null){
            Intent intent = getIntent();
            imageLocalPath = intent.getStringExtra(Macro.IMAGE_LOCAL_PATH);
            imageRemotePath = intent.getStringExtra(Macro.IMAGE_REMOTE_PATH);
        }
        else{
            imageLocalPath = savedInstanceState.getString(Macro.IMAGE_LOCAL_PATH);
            imageLocalPath = savedInstanceState.getString(Macro.IMAGE_REMOTE_PATH);
        }
        processImage();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Macro.IMAGE_LOCAL_PATH, imageLocalPath);
        outState.putString(Macro.IMAGE_REMOTE_PATH, imageRemotePath);
    }

    private void processImage(){
        if (imageLocalPath == null){
            mImageDownloadAsyncTask = new ImageDownloadAsyncTask(this);
            mImageDownloadAsyncTask.execute(imageRemotePath);
        }
        else {
            showImage();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImageDownloadAsyncTask != null){
            mImageDownloadAsyncTask.cancel(true);
        }
    }

    private void showImage(){
        if (imageLocalPath != null){
            Glide.with(this).load(imageLocalPath).into(photoView);
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.picture_load_fail), Toast.LENGTH_SHORT).show();
        }
    }

    private static class ImageDownloadAsyncTask extends AsyncTask<String, Integer, Void>{

        private WeakReference<PlotPicturePreviewActivity> weakReferenceActivty;

        public ImageDownloadAsyncTask(PlotPicturePreviewActivity activity) {
            weakReferenceActivty = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (weakReferenceActivty.get() != null){
                weakReferenceActivty.get().progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            //get plot picture entity
            PlotPictureEntity entity = BasicApp.getAppliction().getPictureRepository().getPlotPictureEntityByUrlSync(strings[0]);
            if (entity == null){
                return null;
            }

            //download image from oss
            OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(HTTP.STS_SERVER);
            OSS oss = new OSSClient(BasicApp.getAppliction().getApplicationContext(), HTTP.END_POINT, credentialProvider);
            GetObjectRequest get = new GetObjectRequest(HTTP.BUCKET_NAME, strings[0]);
            InputStream is = null;
            byte[] buf = new byte[2048];
            int len = 0;
            FileOutputStream fos = null;
            try {
                GetObjectResult result = oss.getObject(get);
                if (result.getStatusCode() == 200){
                    is = result.getObjectContent();
                    File storeDir = BasicApp.getAppliction().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    File image = File.createTempFile(
                            entity.pictureId,
                            ".jpg",
                            storeDir
                    );
                    if (!image.exists()){
                        if (!image.createNewFile()){
                            return null;
                        }
                    }
                    fos = new FileOutputStream(image);
                    while ((len = is.read(buf))!=-1){
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    entity.localAddr = image.getAbsolutePath();
                    BasicApp.getAppliction().getPictureRepository().updatePlotPictureEntity(entity);
                    updateImageLocalPath(entity.localAddr);
                }
            }
            catch (IOException e){
                // 文件读写相关错误等
                e.printStackTrace();
            }
            catch (ClientException e) {
                // 本地异常，如网络异常等。
                e.printStackTrace();
            }
            catch (ServiceException e) {
                // 服务异常。
                Log.e("RequestId", e.getRequestId());
                Log.e("ErrorCode", e.getErrorCode());
                Log.e("HostId", e.getHostId());
                Log.e("RawMessage", e.getRawMessage());
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if (is != null){
                    try {
                        is.close();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
                if (fos != null){
                    try {
                        fos.close();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        private void updateImageLocalPath(String path){
            if (weakReferenceActivty.get() != null){
                weakReferenceActivty.get().imageLocalPath = path;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (weakReferenceActivty.get() != null){
                weakReferenceActivty.get().progressBar.setVisibility(View.INVISIBLE);
                weakReferenceActivty.get().showImage();
            }
        }
    }

}
