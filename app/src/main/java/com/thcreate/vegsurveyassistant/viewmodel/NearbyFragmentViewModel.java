package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.LandMainInfo;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PointMainInfo;
import com.thcreate.vegsurveyassistant.service.ContinuousLocationLiveData;
import com.thcreate.vegsurveyassistant.util.Macro;

public class NearbyFragmentViewModel extends AndroidViewModel {

    public ContinuousLocationLiveData myContinuousLocationData;

    private final BitmapDescriptor mPointMarkerIcon;
    private final BitmapDescriptor mTreeMarkerIcon;
    private final BitmapDescriptor mBushMarkerIcon;
    private final BitmapDescriptor mGrassMarkerIcon;

    public NearbyFragmentViewModel(@NonNull Application application) {
        super(application);
        myContinuousLocationData = new ContinuousLocationLiveData(application);
        LayoutInflater inflater = (LayoutInflater)getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.marker, null);
        AppCompatTextView textView = view.findViewById(R.id.marker_title);
        textView.setText(getApplication().getResources().getString(R.string.samplepoint));
        mPointMarkerIcon = BitmapDescriptorFactory.fromBitmap(getViewBitmap(view));
        textView.setText(getApplication().getResources().getString(R.string.forest));
        mTreeMarkerIcon = BitmapDescriptorFactory.fromBitmap(getViewBitmap(view));
        textView.setText(getApplication().getResources().getString(R.string.bush));
        mBushMarkerIcon = BitmapDescriptorFactory.fromBitmap(getViewBitmap(view));
        textView.setText(getApplication().getResources().getString(R.string.grass));
        mGrassMarkerIcon = BitmapDescriptorFactory.fromBitmap(getViewBitmap(view));
//        mMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_mapmarker);
    }

    public @Nullable MarkerOptions getLandMarkerOptionFromData(LandMainInfo land){
        try {
            if (land == null){
                return null;
            }
            if (land.lat == null || land.lng == null){
                return null;
            }
            LatLng position = new LatLng(Double.valueOf(land.lat), Double.valueOf(land.lng));
            Bundle data = new Bundle();
            data.putString(Macro.ACTION, Macro.ACTION_EDIT);
            data.putString(Macro.LATITUDE, land.lat);
            data.putString(Macro.LONGITUDE, land.lng);
            data.putString(Macro.TYPE, Macro.LAND);
            data.putString(Macro.SAMPLELAND_ID, land.landId);
            data.putString(Macro.SAMPLELAND_TYPE, land.type);
            switch (land.type){
                case Macro.SAMPLELAND_TYPE_GRASS:
                    return new MarkerOptions()
                            .position(position)
                            .icon(mGrassMarkerIcon)
                            .draggable(false)
                            .extraInfo(data);
                case Macro.SAMPLELAND_TYPE_BUSH:
                    return new MarkerOptions()
                            .position(position)
                            .icon(mBushMarkerIcon)
                            .draggable(false)
                            .extraInfo(data);
                case Macro.SAMPLELAND_TYPE_TREE:
                    return new MarkerOptions()
                            .position(position)
                            .icon(mTreeMarkerIcon)
                            .draggable(false)
                            .extraInfo(data);
                default:
                    return null;
            }

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public @Nullable MarkerOptions getPointMarkerOptionFromData(PointMainInfo point){
        try {
            if (point == null){
                return null;
            }
            if (point.lat == null || point.lng == null){
                return null;
            }
            LatLng position = new LatLng(Double.valueOf(point.lat), Double.valueOf(point.lng));
            Bundle data = new Bundle();
            data.putString(Macro.ACTION, Macro.ACTION_EDIT);
            data.putString(Macro.LATITUDE, point.lat);
            data.putString(Macro.LONGITUDE, point.lng);
            data.putString(Macro.TYPE, Macro.POINT);
            data.putString(Macro.SAMPLEPOINT_ID, point.pointId);
            return new MarkerOptions()
                    .position(position)
                    .icon(mPointMarkerIcon)
                    .draggable(false)
                    .extraInfo(data);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getViewBitmap(View view){
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onCleared() {
        try {
            mPointMarkerIcon.recycle();
            mGrassMarkerIcon.recycle();
            mBushMarkerIcon.recycle();
            mTreeMarkerIcon.recycle();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        super.onCleared();
        //回收bitmap资源
    }
}
