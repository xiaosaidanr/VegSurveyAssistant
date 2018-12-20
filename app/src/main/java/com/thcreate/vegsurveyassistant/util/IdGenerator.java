package com.thcreate.vegsurveyassistant.util;

import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IdGenerator {

    public static String getId(int userId){
        String dateStr = getTimeNowString();
        return String.valueOf(userId) + "_" + dateStr;
    }

    private static String getTimeNowString(){
        Locale locale = Locale.CHINA;
        return new SimpleDateFormat("yyyyMMddHHmmssSSS", locale).format(new Date());
    }

}
