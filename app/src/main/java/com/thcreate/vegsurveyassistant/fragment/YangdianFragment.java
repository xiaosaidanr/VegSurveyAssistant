package com.thcreate.vegsurveyassistant.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.YangdianActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YangdianFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YangdianFragment extends BaseFragment {

    private static final String TAG = "YangdianFragment";

    private Toolbar mToolbar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public YangdianFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YangdianFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YangdianFragment newInstance(String param1, String param2) {
        YangdianFragment fragment = new YangdianFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onCreateView" + " " + this.toString());
        View view = inflater.inflate(R.layout.fragment_yangdian, container, false);
        mToolbar = view.findViewById(R.id.yangdian_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        inflater.inflate(R.menu.yangdian_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.yangdian_add:
                intent = new Intent(getActivity(), YangdianActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
//            setHasOptionsMenu(true);
//        }
//    }
}
