package com.thcreate.vegsurveyassistant.upload;

import android.util.ArrayMap;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.ArborSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSpecies;
import com.thcreate.vegsurveyassistant.db.entity.model.HerbSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.ShrubSampleplot;
import com.thcreate.vegsurveyassistant.http.api.PlotApi;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class PlotUploadService implements IUploadService {

    private PlotApi mRequest;
    private boolean mIsSuccess;

    public PlotUploadService() {
        mIsSuccess = true;
    }

    @Override
    public void start() {
        if (mRequest == null){
            mRequest = HttpServiceGenerator.getInstance().createServiceAuth(PlotApi.class);
        }
        executeSync();
    }

    @Override
    public void cancel() {

    }

    public boolean isSuccess(){
        return mIsSuccess;
    }

    private void executeSync(){
        deleteDataListRemote(getDataListNeedDeleteRemote());
        deleteDataListLocal();
        addDataListRemote(getDataListNeedAddRemote());
        updateDataListRemote(getDataListNeedUpdateRemote());
    }

    private List<SampleplotEntity> getDataListNeedDeleteRemote(){
        return BasicApp.getAppliction().getSampleplotRepository().getSampleplotEntityListNeedDeleteRemote();
    }
    private void deleteDataListLocal(){
        BasicApp.getAppliction().getSampleplotRepository().deleteSampleplotEntitiesNeedDeleteLocal();
    }
    private void deleteDataListRemote(List<SampleplotEntity> dataList){
        for (SampleplotEntity data:
             dataList) {
            deleteDataRemote(data);
        }
    }
    private void deleteDataRemote(SampleplotEntity data){
        try {
            Call<ResponseBody> call = mRequest.deletePlot(data.landId, data.plotId);
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
    private void onDeleteDataRemoteSuccess(SampleplotEntity data){
        BasicApp.getAppliction().getSampleplotRepository().deleteSampleplotEntity(data);
    }
    private void onDeleteDataRemoteFail(SampleplotEntity data){
        mIsSuccess = false;
    }

    private List<SampleplotEntity> getDataListNeedAddRemote(){
        return BasicApp.getAppliction().getSampleplotRepository().getSampleplotEntityNeedAddRemote();
    }
    private void addDataListRemote(List<SampleplotEntity> dataList){
        for (SampleplotEntity data :
                dataList) {
            addDataRemote(data);
        }
    }
    private void addDataRemote(SampleplotEntity data){
        addDataRemoteGeneric(generateDataForAddRemote(data));
    }
    private <T extends BaseSampleplot> void addDataRemoteGeneric(T data){
        if (data == null){
            return;
        }
        try {
            Call<ResponseBody> call = null;
            if(data instanceof ArborSampleplot){
                call = mRequest.addArborPlot(data.landId, (ArborSampleplot)data);
            }
            if(data instanceof HerbSampleplot){
                call = mRequest.addHerbPlot(data.landId, (HerbSampleplot)data);
            }
            if(data instanceof ShrubSampleplot){
                call = mRequest.addShrubPlot(data.landId, (ShrubSampleplot)data);
            }
//            Call<ResponseBody> call = mRequest.addPlot(data.landId, data);
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()){
                onAddDataRemoteSuccess(data);
            }
            else {
                mIsSuccess = false;
                onAddDataRemoteFail(data);
            }
        }
        catch (Exception e){
            mIsSuccess = false;
            onAddDataRemoteFail(data);
            e.printStackTrace();
        }
    }
    public static <T extends BaseSampleplot> T generateDataForAddRemote(SampleplotEntity entity){
        entity.uploadAt = new Date();
        List<SpeciesEntity> speciesEntityList = getNotDeletedSpeciesEntityListByPlotId(entity.plotId);
        List<String> parentPlotIdList = getParentPlotIdList(entity.plotId);
        List<BaseSpecies> speciesList = new ArrayList<>();
        Map<String, List<String>> ownerList = new ArrayMap<>();
        if (parentPlotIdList != null && parentPlotIdList.size() > 0){
            ownerList.put("plot", parentPlotIdList);
        }
        if (speciesEntityList != null && speciesEntityList.size() > 0){
            for (SpeciesEntity item :
                    speciesEntityList) {
                speciesList.add(SpeciesUploadService.generateDataForAddRemote(item));
            }
        }
        switch (entity.type){
            case Macro.HERB:
                HerbSampleplot herbPlotData = HerbSampleplot.getInstance(entity);
                herbPlotData.speciesList = speciesList;
                herbPlotData.ownerList = ownerList;
                return (T)herbPlotData;
            case Macro.SHRUB:
                ShrubSampleplot shrubPlotData = ShrubSampleplot.getInstance(entity);
                shrubPlotData.speciesList = speciesList;
                shrubPlotData.ownerList = ownerList;
                return (T)shrubPlotData;
            case Macro.ARBOR:
                ArborSampleplot arborPlotData = ArborSampleplot.getInstance(entity);
                arborPlotData.speciesList = speciesList;
                arborPlotData.ownerList = ownerList;
                return (T)arborPlotData;
            default:
                return null;
        }
    }
    public static <T extends BaseSampleplot> void onAddDataRemoteSuccess(T plot){
        BasicApp.getAppliction().getSampleplotRepository().updateSampleplotEntityUploadAtByPlotId(plot.plotId, plot.uploadAt.getTime());
        for (BaseSpecies item :
                plot.speciesList) {
            SpeciesUploadService.onAddDataRemoteSuccess(item);
        }
    }
    private static <T extends BaseSampleplot> void onAddDataRemoteFail(T plot){

    }
    private static List<SpeciesEntity> getNotDeletedSpeciesEntityListByPlotId(String plotId){
        return BasicApp.getAppliction().getSpeicesRepository().getNotDeletedSpeciesEntityListByPlotId(plotId);
    }


    private List<SampleplotEntity> getDataListNeedUpdateRemote(){
        return BasicApp.getAppliction().getSampleplotRepository().getSampleplotEntityNeedUpdateRemote();
    }
    private void updateDataListRemote(List<SampleplotEntity> dataList){
        for (SampleplotEntity entity :
                dataList) {
            updateDataRemote(entity);
        }
    }
    private void updateDataRemote(SampleplotEntity data){
        data.uploadAt = new Date();
        Map<String, List<String>> ownerList = new ArrayMap<>();
        List<String> parentPlotIdList = getParentPlotIdList(data.plotId);
        if (parentPlotIdList != null && parentPlotIdList.size() > 0){
            ownerList.put(Macro.PLOT, parentPlotIdList);
        }
        Call<ResponseBody> call = null;
        switch (data.type){
            case Macro.HERB:
                HerbSampleplot herbPlot = HerbSampleplot.getInstance(data);
                herbPlot.ownerList = ownerList;
                call = mRequest.updateHerbPlot(data.landId, data.plotId, herbPlot);
                break;
            case Macro.SHRUB:
                ShrubSampleplot shrubPlot = ShrubSampleplot.getInstance(data);
                shrubPlot.ownerList = ownerList;
                call = mRequest.updateShrubPlot(data.landId, data.plotId, shrubPlot);
                break;
            case Macro.ARBOR:
                ArborSampleplot arborPlot = ArborSampleplot.getInstance(data);
                arborPlot.ownerList = ownerList;
                call = mRequest.updateArborPlot(data.landId, data.plotId, arborPlot);
                break;
            default:
                break;
        }
        if (call != null){
            try {
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()){
                    onUpdateDataRemoteSuccess(data);
                }
                else {
                    onUpdateDataRemoteFail(data);
                }
            }
            catch (Exception e){
                onUpdateDataRemoteFail(data);
                e.printStackTrace();
            }
        }
    }
    private void onUpdateDataRemoteSuccess(SampleplotEntity data){
        BasicApp.getAppliction().getSampleplotRepository().updateSampleplotEntityManual(data);
    }
    private void onUpdateDataRemoteFail(SampleplotEntity data){
        mIsSuccess = false;
    }


    private static List<String> getParentPlotIdList(String plotId){
        List<String> result = new ArrayList<>();
        BasicApp.getAppliction().getSampleplotRepository().getParentPlotPlotEntityByChildIdRecursively(result, plotId);
        return result;
    }

}
