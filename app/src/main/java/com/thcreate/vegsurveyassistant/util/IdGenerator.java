package com.thcreate.vegsurveyassistant.util;

import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IdGenerator {

    public static <T> String getId(int userId, Class<T> type){
        String dateStr = getTimeNowString();
        String postfix = "";
        if (type == Yangdian.class){
            postfix = "yangdian";
        }
        if (type == Yangdi.class){
            postfix = "yangdi";
        }
        if (type == CaobenYangfang.class){
            postfix = "caobenyf";
        }
        if (type == GuanmuYangfang.class){
            postfix = "guanmuyf";
        }
        if (type == QiaomuYangfang.class){
            postfix = "qiaomuyf";
        }
        if (type == CaobenWuzhong.class){
            postfix = "caobenwz";
        }
        if (type == GuanmuWuzhong.class){
            postfix = "guanmuwz";
        }
        if (type == QiaomuWuzhong.class){
            postfix = "qiaomuwz";
        }
//        switch (type){
//            case Macro.YANGDIAN:
//                postfix = "yangdian";
//                break;
//            case Macro.YANGDI:
//                postfix = "yangdi";
//                break;
//            case Macro.CAOBEN_YANGFANG:
//                postfix = "caobenyf";
//                break;
//            case Macro.GUANMU_YANGFANG:
//                postfix = "guanmuyf";
//                break;
//            case Macro.QIAOMU_YANGFANG:
//                postfix = "qiaomuyf";
//                break;
//            case Macro.CAOBEN_WUZHONG:
//                postfix = "caobenwz";
//                break;
//            case Macro.GUANMU_WUZHONG:
//                postfix = "guanmuwz";
//                break;
//            case Macro.QIAOMU_WUZHONG:
//                postfix = "qiaomuwz";
//                break;
//            default:
//                postfix = "";
//                break;
//        }
        return String.valueOf(userId) + "-" + dateStr + "-" + postfix;
    }

    private static String getTimeNowString(){
        Locale locale = Locale.CHINA;
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", locale).format(new Date());
    }

}
