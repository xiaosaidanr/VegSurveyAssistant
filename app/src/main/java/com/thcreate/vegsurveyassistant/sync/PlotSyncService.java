package com.thcreate.vegsurveyassistant.sync;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.PlotPlotEntity;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSpecies;
import com.thcreate.vegsurveyassistant.db.entity.model.Picture;
import com.thcreate.vegsurveyassistant.db.entity.model.Samplepoint;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;

public class PlotSyncService implements ISyncService {

    @Override
    public void start() {

    }

    @Override
    public void cancel() {

    }

    static boolean savePlot(BaseSampleplot data){
        try {
            if (data.investigatedAt != null){
                data.investigatedAt = data.investigatedAt.split(" ")[0];
            }
            data.createAt = data.uploadAt;
            data.updateAt = data.uploadAt;

            SampleplotEntity remoteData = data.getEntity();

            SampleplotEntity localData = BasicApp.getAppliction().getSampleplotRepository().getSampleplotEntityByPlotIdSync(remoteData.plotId);
            if (localData == null){
                BasicApp.getAppliction().getSampleplotRepository().insertSampleplotEntityManualSync(remoteData);
                handlePlotOwnerList(data);
            }
            else {
                if (localData.updateAt.before(remoteData.updateAt)){
                    remoteData.id = localData.id;
                    BasicApp.getAppliction().getSampleplotRepository().updateSampleplotEntityManualSync(remoteData);
                    handlePlotOwnerList(data);
                }
            }
            return handlePictureList(data) && handleSpeciesList(data);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private static void handlePlotOwnerList(BaseSampleplot data){
        List<String> ownerList = data.ownerList.get("plot");
        if (ownerList != null){
            PlotPlotEntity localPlotPlotEntity = BasicApp.getAppliction().getDatabase().plotPlotDao().getPlotPlotEntityByChildIdSync(data.plotId);
            PlotPlotEntity remotePlotPlotEntity = new PlotPlotEntity();
            remotePlotPlotEntity.landId = data.landId;
            remotePlotPlotEntity.childId = data.plotId;
            remotePlotPlotEntity.childType = data.type;
            remotePlotPlotEntity.createAt = data.uploadAt;
            remotePlotPlotEntity.updateAt = data.uploadAt;
            remotePlotPlotEntity.uploadAt = data.uploadAt;
            if (ownerList.size() == 0){
                remotePlotPlotEntity.parentId = null;
                remotePlotPlotEntity.parentType = null;
            }
            if (ownerList.size() == 1){
                SampleplotEntity entity = BasicApp.getAppliction().getSampleplotRepository().getSampleplotEntityByPlotIdSync(ownerList.get(0));
                remotePlotPlotEntity.parentId = entity.plotId;
                remotePlotPlotEntity.parentType = entity.type;
            }
            // 因为从属关系是arbor>shrub>herb，所以ownerlist有两个的情况只能是herb->shrub->arbor这样的形式，所以的herb的直接上级必定是shrub
            if (ownerList.size() == 2){
                SampleplotEntity entity1 = BasicApp.getAppliction().getSampleplotRepository().getSampleplotEntityByPlotIdSync(ownerList.get(0));
                SampleplotEntity entity2 = BasicApp.getAppliction().getSampleplotRepository().getSampleplotEntityByPlotIdSync(ownerList.get(1));
                if (entity1.type.equals(Macro.SHRUB)){
                    remotePlotPlotEntity.parentId = entity1.plotId;
                    remotePlotPlotEntity.parentType = entity1.type;
                }
                else {
                    remotePlotPlotEntity.parentId = entity2.plotId;
                    remotePlotPlotEntity.parentType = entity2.type;
                }
            }
            if (localPlotPlotEntity == null){
                BasicApp.getAppliction().getSampleplotRepository().insertPlotPlotEntityManualSync(remotePlotPlotEntity);
            }
            else {
                remotePlotPlotEntity.id = localPlotPlotEntity.id;
                BasicApp.getAppliction().getSampleplotRepository().updatePlotPlotEntityManualSync(remotePlotPlotEntity);
            }
        }
    }

    private static boolean handlePictureList(BaseSampleplot data){
        try {
            if (data.pictureList != null && data.pictureList.size() > 0){
                for (Picture item :
                        data.pictureList) {
                    PlotPictureEntity localPlotPictureEntity = BasicApp.getAppliction().getPictureRepository().getPlotPictureEntityByPictureIdSync(item.pictureId);
                    if (localPlotPictureEntity == null){
                        PlotPictureEntity remotePlotPictureEntity = PlotPictureEntity.getInstance(item);
                        BasicApp.getAppliction().getPictureRepository().insertPlotPictureEntityManualSync(remotePlotPictureEntity);
                    }
                }
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private static boolean handleSpeciesList(BaseSampleplot data){
        try {
            boolean result = true;
            if (data.speciesList != null && data.speciesList.size() > 0){
                for (BaseSpecies item :
                        data.speciesList) {
                    item.plotId = data.plotId;
                    if (!SpeciesSyncService.saveSpecies(item)){
                        result = false;
                    }
                }
            }
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
