package com.thcreate.vegsurveyassistant.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converters {

    public static class DateConverter{

        @TypeConverter
        public static Date fromTimestamp(Long value){
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public static Long dateToTimestamp(Date date){
            return date == null ? null : date.getTime();
        }

    }

}
