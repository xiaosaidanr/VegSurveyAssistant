package com.thcreate.vegsurveyassistant.fragment;


import android.arch.lifecycle.LiveData;
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
import com.thcreate.vegsurveyassistant.activity.GuancongyangdiActivity;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.adapter.RecyclerViewSwipeDismissController;
import com.thcreate.vegsurveyassistant.adapter.YangdiAdapter;
import com.thcreate.vegsurveyassistant.databinding.FragmentGuancongListBinding;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.GuancongyangdiListViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuancongListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuancongListFragment extends Fragment {

    private static final String TAG = "GuancongListFragment";

    private GuancongyangdiListViewModel mViewModel;
    private FragmentGuancongListBinding mBinding;

    private YangdiAdapter mYangdiAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public GuancongListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuancongListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuancongListFragment newInstance(String param1, String param2) {
        GuancongListFragment fragment = new GuancongListFragment();
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
        controller.setOnDeleteCallback((id, position)-> mViewModel.deleteYangdiById(id));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(controller);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_guancong_list, container, false);
        mYangdiAdapter = new YangdiAdapter(mYangdiItemClickCallback);
        mBinding.yangdiList.setAdapter(mYangdiAdapter);
        itemTouchHelper.attachToRecyclerView(mBinding.yangdiList);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(GuancongyangdiListViewModel.class);
        subscribeUi();
    }
    private final ItemClickCallback<Yangdi> mYangdiItemClickCallback = (yangdi) -> {
        Intent intent = new Intent(getActivity(), GuancongyangdiActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGDI_CODE, yangdi.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_BUSH);
        startActivity(intent);
    };
    private void subscribeUi() {
        mViewModel.getYangdiList().observe(this, (yangdiList)->{
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
