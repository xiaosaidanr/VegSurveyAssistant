package com.thcreate.vegsurveyassistant.fragment;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.YangdianActivity;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.adapter.RecyclerViewSwipeDismissController;
import com.thcreate.vegsurveyassistant.adapter.YangdianAdapter;
import com.thcreate.vegsurveyassistant.databinding.FragmentYangdianBinding;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.YangdianListViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YangdianFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YangdianFragment extends BaseFragment {

    private static final String TAG = "YangdianFragment";

    private Toolbar mToolbar;

    YangdianListViewModel mViewModel;
    private FragmentYangdianBinding mBinding;

    private YangdianAdapter mYangdianAdapter;

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

        RecyclerViewSwipeDismissController controller = new RecyclerViewSwipeDismissController(
                0,
                ItemTouchHelper.LEFT,
                ContextCompat.getDrawable(getActivity(), R.drawable.ic_delete_forever));
        controller.setOnDeleteCallback((id, position)->{
            mViewModel.deleteYangdianById(id);
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(controller);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_yangdian, container, false);
        mToolbar = mBinding.yangdianToolbar;

        mYangdianAdapter = new YangdianAdapter(mYangdianItemClickCallback);

        mBinding.yangdianList.setAdapter(mYangdianAdapter);
        itemTouchHelper.attachToRecyclerView(mBinding.yangdianList);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(YangdianListViewModel.class);

        subscribeUi();
    }

    private void subscribeUi() {
        mViewModel.getYangdianList().observe(this, (yangdianList)->{
            if (yangdianList != null) {
                mBinding.setIsLoading(false);
                mYangdianAdapter.setYangdianList(yangdianList);
            } else {
                mBinding.setIsLoading(true);
            }
            mBinding.executePendingBindings();
        });
    }

    private final ItemClickCallback<Yangdian> mYangdianItemClickCallback = new ItemClickCallback<Yangdian>() {
        @Override
        public void onClick(Yangdian item) {
            Intent intent = new Intent(getActivity(), YangdianActivity.class);
            intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
            intent.putExtra(Macro.YANGDIAN_CODE, item.yangdianCode);
            startActivity(intent);
        }
    };

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
                intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
                //TODO userid1
                intent.putExtra(Macro.YANGDIAN_CODE, IdGenerator.getId(1, Yangdian.class));
                startActivity(intent);
                return true;
        }
        return false;
    }
}
