package com.thcreate.vegsurveyassistant.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.ArborLandActivity;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.adapter.RecyclerViewSwipeDismissController;
import com.thcreate.vegsurveyassistant.adapter.SamplelandAdapter;
import com.thcreate.vegsurveyassistant.databinding.FragmentArborlandListBinding;
import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.LandMainInfo;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.ArborLandListViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArborLandListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArborLandListFragment extends Fragment {

    private static final String TAG = "ArborLandListFragment";

    private ArborLandListViewModel mViewModel;
    private FragmentArborlandListBinding mBinding;

    private SamplelandAdapter mSamplelandAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ArborLandListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArborLandListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArborLandListFragment newInstance(String param1, String param2) {
        ArborLandListFragment fragment = new ArborLandListFragment();
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
        RecyclerViewSwipeDismissController controller = new RecyclerViewSwipeDismissController(
                0,
                ItemTouchHelper.LEFT,
                ContextCompat.getDrawable(getActivity(), R.drawable.ic_delete_forever));
        controller.setOnDeleteCallback((id, position)->{
            mViewModel.deleteSamplelandEntityById(id);
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(controller);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_arborland_list, container, false);
        mSamplelandAdapter = new SamplelandAdapter(mItemClickCallback);
        mBinding.arborlandList.setAdapter(mSamplelandAdapter);
        itemTouchHelper.attachToRecyclerView(mBinding.arborlandList);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ArborLandListViewModel.class);

        subscribeUi();
    }

    private final ItemClickCallback<LandMainInfo> mItemClickCallback = (data) -> {
        Intent intent = new Intent(getActivity(), ArborLandActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.SAMPLELAND_ID, data.landId);
        intent.putExtra(Macro.SAMPLELAND_TYPE, Macro.SAMPLELAND_TYPE_TREE);
        startActivity(intent);
    };

    private void subscribeUi() {
        mViewModel.getSamplelandEntityList().observe(this, (dataList)->{
            if (dataList != null) {
                mBinding.setIsLoading(false);
                mSamplelandAdapter.setDataList(dataList);
            } else {
                mBinding.setIsLoading(true);
            }
            mBinding.executePendingBindings();
        });
    }

}
