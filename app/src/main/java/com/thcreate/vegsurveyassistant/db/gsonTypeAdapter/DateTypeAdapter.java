package com.thcreate.vegsurveyassistant.db.gsonTypeAdapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

public class DateTypeAdapter extends TypeAdapter<Date> {

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value != null){
            out.value(String.valueOf(value.getTime()));
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
