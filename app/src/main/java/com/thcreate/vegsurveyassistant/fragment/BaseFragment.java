package com.thcreate.vegsurveyassistant.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    private boolean isLog = true;

    @Override
    public void onAttach(Context context) {
        logProcess("onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        logProcess("onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        logProcess("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        logProcess("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        logProcess("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        logProcess("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        logProcess("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        logProcess("onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        logProcess("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        logProcess("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        logProcess("onDetach");
        super.onDetach();
    }

    public void logProcess(String processName){
        if (isLog){
            Log.d(this.getClass().getSimpleName(), processName + " " + this.toString());
        }
    }

}
