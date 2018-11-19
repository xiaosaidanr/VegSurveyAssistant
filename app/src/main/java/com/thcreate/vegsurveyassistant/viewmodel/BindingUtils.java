package com.thcreate.vegsurveyassistant.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class BindingUtils {

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(AppCompatSpinner pAppCompatSpinner, String newSelectedValue, final InverseBindingListener newTextAttrChanged) {
        pAppCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != null) {
            int pos = ((ArrayAdapter<String>) pAppCompatSpinner.getAdapter()).getPosition(newSelectedValue);
            pAppCompatSpinner.setSelection(pos, true);
        }
    }
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static String captureSelectedValue(AppCompatSpinner pAppCompatSpinner) {
        return (String) pAppCompatSpinner.getSelectedItem();
    }

    //    @InverseMethod("stringToDate")
//    public static String dateToString(EditText view, Date oldValue,
//                                      Date value) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
//        return formatter.format(value);
//    }
//
//    public static Date stringToDate(EditText view, String oldValue,
//                                    String value) {
//        // Converts String to long.
//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
//            return formatter.parse(value);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

}
