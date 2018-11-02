package com.thcreate.vegsurveyassistant.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.thcreate.vegsurveyassistant.CaodiyangdiActivity;
import com.thcreate.vegsurveyassistant.GuancongyangdiActivity;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.SenlinyangdiActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YangfangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YangfangFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public YangfangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YangfangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YangfangFragment newInstance(String param1, String param2) {
        YangfangFragment fragment = new YangfangFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_yangfang, container, false);
        Toolbar yangfangToolbar = view.findViewById(R.id.yangfang_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(yangfangToolbar);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.yangfang_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.caodiyangfang_add:
                intent = new Intent(getActivity(), CaodiyangdiActivity.class);
                startActivity(intent);
                return true;
            case R.id.guancongyangfang_add:
                intent = new Intent(getActivity(), GuancongyangdiActivity.class);
                startActivity(intent);
                return true;
            case R.id.senlinyangfang_add:
                intent = new Intent(getActivity(), SenlinyangdiActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}
