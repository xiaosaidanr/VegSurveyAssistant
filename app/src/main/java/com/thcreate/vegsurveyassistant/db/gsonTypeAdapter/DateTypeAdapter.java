package com.thcreate.vegsurveyassistant.db.gsonTypeAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTypeAdapter extends TypeAdapter<Date> {

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value != null){
//            out.value(String.valueOf(value.getTime()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            out.value(simpleDateFormat.format(value));
        }
        else {
            out.nullValue();
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        try {
            return new Date(Long.valueOf(in.nextString()));
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
