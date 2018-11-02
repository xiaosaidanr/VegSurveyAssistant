package com.thcreate.vegsurveyassistant.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NearbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearbyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public NearbyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NearbyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NearbyFragment newInstance(String param1, String param2) {
        NearbyFragment fragment = new NearbyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        Log.d("11111111111111111", "onAttach NearbyFragment"+this.toString());
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("11111111111111111", "onCreate NearbyFragment"+this.toString());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("11111111111111111", "onCreateView NearbyFragment"+this.toString());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("11111111111111111", "onActivityCreated NearbyFragment"+this.toString());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("11111111111111111", "onStart NearbyFragment"+this.toString());
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("11111111111111111", "onResume NearbyFragment"+this.toString());
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("11111111111111111", "onPause NearbyFragment"+this.toString());
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("11111111111111111", "onStop NearbyFragment"+this.toString());
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("11111111111111111", "onDestroyView NearbyFragment"+this.toString());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("11111111111111111", "onDestroy NearbyFragment"+this.toString());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("11111111111111111", "onDetach NearbyFragment"+this.toString());
        super.onDetach();
    }
}
