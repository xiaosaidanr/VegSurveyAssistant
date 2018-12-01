package com.thcreate.vegsurveyassistant.fragment;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.SenlinyangdiActivity;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.adapter.YangdiAdapter;
import com.thcreate.vegsurveyassistant.databinding.FragmentSenlinListBinding;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.SenlinyangdiListViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SenlinListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SenlinListFragment extends Fragment {

    private static final String TAG = "SenlinListFragment";

    private FragmentSenlinListBinding mBinding;

    private YangdiAdapter mYangdiAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SenlinListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SenlinListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SenlinListFragment newInstance(String param1, String param2) {
        SenlinListFragment fragment = new SenlinListFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_senlin_list, container, false);
        mYangdiAdapter = new YangdiAdapter(mYangdiItemClickCallback);
        mBinding.yangdiList.setAdapter(mYangdiAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SenlinyangdiListViewModel viewModel = ViewModelProviders.of(this).get(SenlinyangdiListViewModel.class);

        subscribeUi(viewModel.getYangdiList());
    }

    private final ItemClickCallback<Yangdi> mYangdiItemClickCallback = (yangdi) -> {
        Intent intent = new Intent(getActivity(), SenlinyangdiActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGDI_CODE, yangdi.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_TREE);
        startActivity(intent);
    };

    private void subscribeUi(LiveData<List<Yangdi>> liveData) {
        liveData.observe(this, (yangdiList)->{
            if (yangdiList != null) {
                mBinding.setIsLoading(false);
                mYangdiAdapter.setYangdiList(yangdiList);
            } else {
                mBinding.setIsLoading(true);
            }
            mBinding.executePendingBindings();
        });
    }

}
