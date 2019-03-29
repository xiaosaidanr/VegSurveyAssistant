package com.thcreate.vegsurveyassistant.sync;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.ArborSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.ArborSpecies;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.HerbSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.ShrubSampleplot;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;
import java.util.Map;

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

            SampleplotEntity remoteData = new SampleplotEntity();
            remoteData.initCommonFromPlot(data);
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            remoteData.data = gson.toJson(data.genericData);

            SampleplotEntity localData = BasicApp.getAppliction().getSampleplotRepository().getSampleplotEntityByPlotIdSync(remoteData.plotId);
            if (localData == null){
                BasicApp.getAppliction().getSampleplotRepository().insertSampleplotEntityManualSync(remoteData);
            }
            else {
                if (localData.updateAt.before(remoteData.updateAt)){
                    remoteData.id = localData.id;
                    BasicApp.getAppliction().getSampleplotRepository().updateSampleplotEntityManualSync(remoteData);
                }
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
