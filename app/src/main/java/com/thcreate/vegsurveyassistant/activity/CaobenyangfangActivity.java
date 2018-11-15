package com.thcreate.vegsurveyassistant.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityCaobenyangfangBinding;
import com.thcreate.vegsurveyassistant.viewmodel.CaobenyangfangActivityViewModel;

import java.util.Calendar;

public class CaobenyangfangActivity extends AppCompatActivity {

    private CaobenyangfangActivityViewModel mViewModel;
    private ActivityCaobenyangfangBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CaobenyangfangActivityViewModel.Factory factory = new CaobenyangfangActivityViewModel.Factory(
                getApplication(), "testtesttest"
        );
        mViewModel = ViewModelProviders.of(this, factory)
                .get(CaobenyangfangActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_caobenyangfang);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);

//        setContentView(R.layout.activity_caobenyangfang);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mBinding.toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        mBinding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Log.d("test", String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day));
            // Do something with the date chosen by the user
            TextView textView = getActivity().findViewById(R.id.date_selected);
            if (textView != null){
                textView.setText(String.valueOf(String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day)));
            }
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onAddWuzhong(View v){
        Intent intent = new Intent(CaobenyangfangActivity.this, CaobenwuzhongActivity.class);
        startActivity(intent);
    }
}
