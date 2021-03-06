package com.thcreate.vegsurveyassistant.util;

import java.util.HashMap;
import java.util.Map;

public class Macro {

    //Intent传递参数使用
    public static final String ACTION = "action_key";
    public static final String ACTION_ADD = "add";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_ADD_RESTORE = "add_restore";
    public static final String ACTION_EDIT_RESTORE = "edit_restore";
    public static final String ACTION_TEMP_SAVE = "temp_save";
    public static final String ACTION_TEMP_SAVE_RESTORE = "temp_save_restore";

    public static final String SAMPLEPOINT_ID = "samplepoint_id";
    public static final String SAMPLELAND_ID = "sampleland_id";
    public static final String SAMPLEPLOT_ID = "sampleplot_id";
    public static final String SPECIES_ID = "species_id";

    public static final String SAMPLELAND_TYPE = "sampleland_type";
    public static final String SAMPLELAND_TYPE_GRASS = "grass";
    public static final String SAMPLELAND_TYPE_BUSH = "bush";
    public static final String SAMPLELAND_TYPE_TREE = "tree";

    public static final String TYPE = "type";
    public static final String LAND = "land";
    public static final String POINT = "point";
    public static final String PLOT = "plot";

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    public static final String IMAGE_LOCAL_PATH = "image_local_path";
    public static final String IMAGE_REMOTE_PATH = "image_remote_path";

    public static final String PARENT_PLOT_ID = "parentPlotId";
    public static final String PARENT_PLOT_TYPE = "parentPlotType";

    public static final String HERB = "herb";
    public static final String SHRUB = "shrub";
    public static final String ARBOR = "arbor";

    public final static String CoorType_GCJ02 = "gcj02";
    public final static String CoorType_BD09LL= "bd09ll";
    public final static String CoorType_BD09MC= "bd09";

    //后台worker用
    public final static String UPLOAD = "upload";
    public final static String DATA_UPLOAD_UNIQUE_NAME = "com.thcreate.vegsurveyassistant:data_upload";
    public final static String IMAGE_UPLOAD_UNIQUE_NAME = "com.thcreate.vegsurveyassistant:image_upload";
    public final static String CLEAN = "clean";
    public final static String IMAGE_CLEAN_UNIQUE_NAME = "com.thcreate.vegsurveyassistant:image_clean";

    public final static Map<String, String> LAND_TYPE_MAP;
    static {
        {
            LAND_TYPE_MAP = new HashMap<String, String>();
            LAND_TYPE_MAP.put("grass", "草地样地");
            LAND_TYPE_MAP.put("bush", "灌丛样地");
            LAND_TYPE_MAP.put("tree", "森林样地");
        }
    }

}
