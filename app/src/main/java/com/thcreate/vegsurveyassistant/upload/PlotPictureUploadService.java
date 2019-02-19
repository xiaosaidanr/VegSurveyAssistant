package com.thcreate.vegsurveyassistant.upload;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.Picture;
import com.thcreate.vegsurveyassistant.http.api.PictureApi;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;
import com.thcreate.vegsurveyassistant.util.HTTP;

import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlotPictureUploadService implements IUploadService {

//    private Retrofit mRetrofit;
    private PictureApi mRequest;

    public PlotPictureUploadService() {
    }

    @Override
    public void start() {
        if (mRequest == null){
//            if (mRetrofit == null){
//                mRetrofit = new Retrofit.Builder()
//                        .baseUrl(HTTP.SERVICE_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//            }
//            mRequest = mRetrofit.create(PictureApi.class);
            mRequest = HttpServiceGenerator.createService(PictureApi.class);
        }
    }

    @Override
    public void cancel() {

    }

    private void executeSync(){
        deleteDataList(getDataListNeedDelete());
        addDataListRemote(getDataListNeedAddRemote());
    }

    private List<PlotPictureEntity> getDataListNeedDelete(){
        return BasicApp.getAppliction().getPictureRepository().getPlotPictureEntityListNeedDelete();
    }
    private void deleteDataList(List<PlotPictureEntity> dataList){
        for (PlotPictureEntity item :
                dataList) {
            if (item.uploadAt != null && item.uploadAt.before(item.updateAt)){
                deleteDataRemote(item);
            }
            else{
                deleteDataLocal(item);
            }
        }
    }
    private void deleteDataLocal(PlotPictureEntity data){
        BasicApp.getAppliction().getPictureRepository().deletePlotPictureEntity(data);
        deletePictureFile(data.localAddr);

    }
    private void deleteDataRemote(PlotPictureEntity data){
        try {
            Call<ResponseBody> call = mRequest.deletePicture(data.pictureId);
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()){
                onDeleteDataRemoteSuccess(data);
            }
            else {
                onDeleteDataRemoteFail(data);
            }
        }
        catch (Exception e){
            onDeleteDataRemoteFail(data);
            e.printStackTrace();
        }
    }
    private void onDeleteDataRemoteSuccess(PlotPictureEntity data){
        deleteDataLocal(data);
    }
    private void onDeleteDataRemoteFail(PlotPictureEntity data){

    }
    private void deletePictureFile(String path){
        //TODO delete picture file
    }

    private List<PlotPictureEntity> getDataListNeedAddRemote(){
        return BasicApp.getAppliction().getPictureRepository().getPlotPictureEntityListNeedAddRemote();
    }
    private void addDataListRemote(List<PlotPictureEntity> dataList){
        for (PlotPictureEntity item :
                dataList) {
            addDataRemote(item);
        }
    }
    private void addDataRemote(PlotPictureEntity data){
        try {
            if (data.url == null){
                data.url = pushPictureFileToOSS(data);
            }
            Call<ResponseBody> call = mRequest.addPicture(new Picture(data));
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()){
                onAddDataRemoteSuccess(data, new Date().getTime());
            }
            else {
                onAddDataRemoteFail(data);
            }
        }
        catch (Exception e){
            onAddDataRemoteFail(data);
            e.printStackTrace();
        }
    }
    private String pushPictureFileToOSS(PlotPictureEntity data){
        //TODO push to oss
        return null;
    }
    private void onAddDataRemoteSuccess(PlotPictureEntity data, long uploadAt){
        BasicApp.getAppliction().getPictureRepository().updatePlotPictureEntityUploadAtByPictureId(data.pictureId, uploadAt);
    }
    private void onAddDataRemoteFail(PlotPictureEntity data){

    }

}
