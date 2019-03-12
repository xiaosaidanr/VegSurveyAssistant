package com.thcreate.vegsurveyassistant.upload.oss;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;
import com.thcreate.vegsurveyassistant.upload.IUploadService;
import com.thcreate.vegsurveyassistant.util.HTTP;

import java.io.File;
import java.util.List;

import top.zibin.luban.Luban;

public class PlotPictureOssService implements IUploadService {

    private OSS oss = null;

    public PlotPictureOssService() {
    }

    @Override
    public void start() {
        if (oss != null){
            cancel();
        }
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(HTTP.STS_SERVER);
        oss = new OSSClient(BasicApp.getAppliction().getApplicationContext(), HTTP.END_POINT, credentialProvider);
        executeSync();
    }

    private void executeSync(){
        List<PlotPictureEntity> dataList = BasicApp.getAppliction().getPictureRepository().getPlotPictureEntityListNeedAddRemote();
        for (PlotPictureEntity entity :
                dataList) {
            if (entity.url == null){
                String filePath = entity.localAddr;
                String fileName = filePath.substring(filePath.lastIndexOf('/')+1);
                if (compressImage(filePath)){
                    try {
                        if (!oss.doesObjectExist(HTTP.BUCKET_NAME, fileName)){
                            PutObjectRequest put = new PutObjectRequest(HTTP.BUCKET_NAME, fileName, filePath);
                            oss.putObject(put);
                        }
                        entity.url = fileName;
                        onUploadSuccess(entity);
                    }
                    catch (ClientException e) {
                        // 本地异常，如网络异常等。
                        e.printStackTrace();
                        onUploadFail(entity);
                    } catch (ServiceException e) {
                        // 服务异常。
                        Log.e("RequestId", e.getRequestId());
                        Log.e("ErrorCode", e.getErrorCode());
                        Log.e("HostId", e.getHostId());
                        Log.e("RawMessage", e.getRawMessage());
                        onUploadFail(entity);
                    }
                }
            }
        }
        cancel();
    }

    private boolean compressImage(String filePath){
        try {
            File result = Luban.with(BasicApp.getAppliction().getApplicationContext())
                    .setTargetDir(filePath.substring(0, filePath.lastIndexOf('/')))
                    .ignoreBy(2000)
                    .get(filePath);
            if (result == null || result.length() == 0){
                return false;
            }
            return result.renameTo(new File(filePath));
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void onUploadSuccess(PlotPictureEntity data){
        BasicApp.getAppliction().getPictureRepository().updatePlotPictureEntityUrlByPictureId(data.pictureId, data.url);
    }
    private void onUploadFail(PlotPictureEntity data){

    }

    @Override
    public void cancel() {
        oss = null;
    }

}
