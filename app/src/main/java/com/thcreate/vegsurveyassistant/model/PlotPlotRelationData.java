package com.thcreate.vegsurveyassistant.model;

import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.util.Macro;

public class PlotPlotRelationData {

    public String parentId;

    public String parentCode;

    public String parentType;

    public String childId;

    public String childCode;

    public String childType;

    public PlotPlotRelationData(String childId, String childCode, String childType) {
        this.childId = childId;
        this.childCode = childCode;
        this.childType = childType;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (childId == null){
            result.append("无");
        }
        else {
            result.append(childCode);
            if (childType.equals(Macro.HERB)){
                result.append("(草本样方)");
            }
            if (childType.equals(Macro.SHRUB)){
                result.append("(灌木样方)");
            }
            if (childType.equals(Macro.ARBOR)){
                result.append("(乔木样方)");
            }
            if (parentId != null){
                result.append("->");
                result.append(parentCode);
                if (parentType.equals(Macro.HERB)){
                    result.append("(草本样方)");
                }
                if (parentType.equals(Macro.SHRUB)){
                    result.append("(灌木样方)");
                }
                if (parentType.equals(Macro.ARBOR)){
                    result.append("(乔木样方)");
                }
            }
        }
        return result.toString();
    }
}
