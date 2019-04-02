package com.thcreate.vegsurveyassistant.sync;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSpecies;

public class SpeciesSyncService implements ISyncService {

    @Override
    public void start() {

    }

    @Override
    public void cancel() {

    }

    static boolean saveSpecies(BaseSpecies data){
        try {
            data.createAt = data.uploadAt;
            data.updateAt = data.uploadAt;

            SpeciesEntity remoteData = data.getEntity();
            SpeciesEntity localData = BasicApp.getAppliction().getSpeicesRepository().getSpeciesEntityBySpeciesIdSync(remoteData.speciesId);
            if (localData == null){
                BasicApp.getAppliction().getSpeicesRepository().insertSpeciesEntityManualSync(remoteData);
            }
            else {
                if (localData.updateAt.before(remoteData.updateAt)){
                    remoteData.id = localData.id;
                    BasicApp.getAppliction().getSpeicesRepository().updateSpeciesEntityManualSync(remoteData);
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
