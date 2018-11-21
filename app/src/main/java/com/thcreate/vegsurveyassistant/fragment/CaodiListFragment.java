package com.thcreate.vegsurveyassistant.fragment;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.adapter.YangdiAdapter;
import com.thcreate.vegsurveyassistant.databinding.FragmentCaodiListBinding;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.viewmodel.CaodiyangdiListViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaodiListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaodiListFragment extends Fragment {

    private static final String TAG = "CaodiListFragment";

    private FragmentCaodiListBinding mBinding;

    private YangdiAdapter mYangdiAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CaodiListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CaodiListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CaodiListFragment newInstance(String param1, String param2) {
        CaodiListFragment fragment = new CaodiListFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_caodi_list, container, false);
        mYangdiAdapter = new YangdiAdapter();
        mBinding.yangdiList.setAdapter(mYangdiAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final CaodiyangdiListViewModel viewModel = ViewModelProviders.of(this).get(CaodiyangdiListViewModel.class);

        subscribeUi(viewModel.getYangdiList());
    }

    private void subscribeUi(LiveData<List<Yangdi>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (yangdiList)->{
            if (yangdiList != null) {
                mBinding.setIsLoading(false);
                mYangdiAdapter.setYangdiList(yangdiList);
            } else {
                mBinding.setIsLoading(true);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

}
