package com.thcreate.vegsurveyassistant.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.CaodiyangdiActivity;
import com.thcreate.vegsurveyassistant.activity.GuancongyangdiActivity;
import com.thcreate.vegsurveyassistant.activity.SenlinyangdiActivity;
import com.thcreate.vegsurveyassistant.databinding.FragmentNearbyBinding;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.NearbyFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NearbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearbyFragment extends BaseFragment implements BaiduMap.OnMarkerClickListener {

    private static final String TAG = "NearbyFragment";

    private NearbyFragmentViewModel mViewModel;
    private FragmentNearbyBinding mBinding;

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private SearchView mSearchView;

    private Marker[] mMarkers;

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
        super.onAttach(context);
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

        Log.e(this.getClass().getSimpleName(), "onCreateView" + " " + this.toString());
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_nearby, container, false);
        mMapView = mBinding.mapView;
        mSearchView = mBinding.searchView;
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMarkerClickListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(this.getClass().getSimpleName(), "onActivityCreated" + " " + this.toString());
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NearbyFragmentViewModel.class);
        subscribeUi();
    }

    private void subscribeUi(){
        mViewModel.myContinuousLocationData.observe(this, locationData -> {
            if (mBaiduMap != null){
                if (locationData.isValid){
                    mBaiduMap.setMyLocationEnabled(true);
                    MyLocationData data = new MyLocationData.Builder()
                            .latitude(locationData.latitude)
                            .longitude(locationData.longitude)
                            .build();
                    mBaiduMap.setMyLocationData(data);
                }
                else {
                    Toast.makeText(getActivity(), "获取定位失败！请检查是否已经打开定位功能与网络连接", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mViewModel.getYangdiList().observe(this, yangdis -> {
            clearOverlay(null);
            if (yangdis != null && yangdis.size() > 0){
                mMarkers = new Marker[yangdis.size()];
                for (int i = 0; i < yangdis.size(); i++) {
                    MarkerOptions tmp = mViewModel.getMarkerOptionFromData(yangdis.get(i));
                    if (tmp != null){
                        mMarkers[i] = (Marker)mBaiduMap.addOverlay(tmp);
                    }
                }
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Bundle data = marker.getExtraInfo();
        String type = data.getString(Macro.YANGDI_TYPE);
        Intent intent = null;
        switch (type){
            case Macro.YANGDI_TYPE_GRASS:
                intent = new Intent(getActivity(), CaodiyangdiActivity.class);
                break;
            case Macro.YANGDI_TYPE_BUSH:
                intent = new Intent(getActivity(), GuancongyangdiActivity.class);
                break;
            case Macro.YANGDI_TYPE_TREE:
                intent = new Intent(getActivity(), SenlinyangdiActivity.class);
                break;
            default:
                break;
        }
        if (intent != null){
            intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
            intent.putExtra(Macro.YANGDI_CODE, data.getString(Macro.YANGDI_CODE));
            intent.putExtra(Macro.YANGDI_TYPE, data.getString(Macro.YANGDI_TYPE));
            startActivity(intent);
        }
        return true;
    }

    public void clearOverlay(View v){
        mBaiduMap.clear();
        if (mMarkers != null){
            for (int i = 0; i < mMarkers.length; i++) {
                mMarkers[i] = null;
            }
            mMarkers = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearOverlay(null);
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
    }
}
