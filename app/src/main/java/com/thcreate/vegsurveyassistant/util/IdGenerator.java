package com.thcreate.vegsurveyassistant.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IdGenerator {

    public static String getId(int userId, int type){
        String dateStr = getTimeNowString();
        String postfix = null;
        switch (type){
            case Macro.YANGDIAN:
                postfix = "yangdian";
                break;
            case Macro.YANGDI:
                postfix = "yangdi";
                break;
            case Macro.CAOBEN_YANGFANG:
                postfix = "caobenyf";
                break;
            case Macro.GUANMU_YANGFANG:
                postfix = "guanmuyf";
                break;
            case Macro.QIAOMU_YANGFANG:
                postfix = "qiaomuyf";
                break;
            case Macro.CAOBEN_WUZHONG:
                postfix = "caobenwz";
                break;
            case Macro.GUANMU_WUZHONG:
                postfix = "guanmuwz";
                break;
            case Macro.QIAOMU_WUZHONG:
                postfix = "qiaomuwz";
                break;
            default:
                postfix = "";
                break;
        }
        return String.valueOf(userId) + "-" + dateStr + "-" + postfix;
    }

    private static String getTimeNowString(){
        Locale locale = Locale.CHINA;
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", locale).format(new Date());
    }

}
