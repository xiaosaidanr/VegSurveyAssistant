package com.thcreate.vegsurveyassistant.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.ArborLandActivity;
import com.thcreate.vegsurveyassistant.activity.HerbLandActivity;
import com.thcreate.vegsurveyassistant.activity.SamplepointActivity;
import com.thcreate.vegsurveyassistant.activity.ShrubLandActivity;
import com.thcreate.vegsurveyassistant.util.Macro;

public class MarkerDetailInfoDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "MarkerDetailInfoDialogFragment";

    private static final String ARG_PARAM = "data";

    private Bundle mParam;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getBundle(ARG_PARAM);
        }
    }

    public static MarkerDetailInfoDialogFragment newInstance(Bundle data){
        MarkerDetailInfoDialogFragment fragment = new MarkerDetailInfoDialogFragment();
        Bundle args = new Bundle();
        args.putBundle(ARG_PARAM, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Double width = getActivity().getWindow().getDecorView().getWidth()*0.8;
        Double heigh = getActivity().getWindow().getDecorView().getHeight()*0.5;
        getDialog().getWindow().setLayout(width.intValue(), heigh.intValue());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.marker_detail_info, null);
        ImageButton closeImageButton = view.findViewById(R.id.closeImageButton);
        closeImageButton.setOnClickListener(v->closeMarkerDetailInfo(v));
        AppCompatButton viewDetailButton = view.findViewById(R.id.viewDetailButton);
        viewDetailButton.setOnClickListener(this);
        AppCompatTextView idTextView = view.findViewById(R.id.id);
        AppCompatTextView typeTextView = view.findViewById(R.id.type);
        String type = mParam.getString(Macro.TYPE);
        if (type.equals(Macro.LAND)){
            String landType = mParam.getString(Macro.SAMPLELAND_TYPE);
            String id = mParam.getString(Macro.SAMPLELAND_ID);
            idTextView.setText(String.format("编号：%s", id));
            switch (landType){
                case Macro.SAMPLELAND_TYPE_GRASS:
                    typeTextView.setText("类型：草地");
                    break;
                case Macro.SAMPLELAND_TYPE_BUSH:
                    typeTextView.setText("类型：灌丛");
                    break;
                case Macro.SAMPLELAND_TYPE_TREE:
                    typeTextView.setText("类型：森林");
                    break;
                default:
                    typeTextView.setText("类型：无");
                    break;
            }
        }
        else {
            String id = mParam.getString(Macro.SAMPLEPOINT_ID);
            idTextView.setText(String.format("编号：%s", id));
            typeTextView.setText("类型：样点");
        }
        AppCompatTextView latitudeTextView = view.findViewById(R.id.latitude);
        AppCompatTextView longitudeTextView = view.findViewById(R.id.longitude);
        latitudeTextView.setText(String.format("纬度：%s", mParam.getString(Macro.LATITUDE)));
        longitudeTextView.setText(String.format("经度：%s", mParam.getString(Macro.LONGITUDE)));
        return view;
    }

    @Override
    public void onClick(View v) {
        String type = mParam.getString(Macro.TYPE);
        if (type.equals(Macro.LAND)){
            String landType = mParam.getString(Macro.SAMPLELAND_TYPE);
            Intent intent = null;
            switch (landType){
                case Macro.SAMPLELAND_TYPE_GRASS:
                    intent = new Intent(getActivity(), HerbLandActivity.class);
                    break;
                case Macro.SAMPLELAND_TYPE_BUSH:
                    intent = new Intent(getActivity(), ShrubLandActivity.class);
                    break;
                case Macro.SAMPLELAND_TYPE_TREE:
                    intent = new Intent(getActivity(), ArborLandActivity.class);
                    break;
                default:
                    break;
            }
            if (intent != null){
                intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
                intent.putExtra(Macro.SAMPLELAND_ID, mParam.getString(Macro.SAMPLELAND_ID));
                intent.putExtra(Macro.SAMPLELAND_TYPE, mParam.getString(Macro.SAMPLELAND_TYPE));
                startActivity(intent);
            }
        }
        else {
            Intent intent = new Intent(getActivity(), SamplepointActivity.class);
            intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
            intent.putExtra(Macro.SAMPLEPOINT_ID, mParam.getString(Macro.SAMPLEPOINT_ID));
            startActivity(intent);
        }
        dismiss();
    }

    public void closeMarkerDetailInfo(View v){
        dismiss();
    }

}
