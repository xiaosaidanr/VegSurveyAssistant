package com.thcreate.vegsurveyassistant.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
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

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.SamplepointActivity;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.adapter.RecyclerViewSwipeDismissController;
import com.thcreate.vegsurveyassistant.adapter.SamplepointAdapter;
import com.thcreate.vegsurveyassistant.databinding.FragmentSamplepointListBinding;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PointMainInfo;
import com.thcreate.vegsurveyassistant.service.ActivityCollector;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.upload.LandUploadService;
import com.thcreate.vegsurveyassistant.upload.PointUploadService;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.MainActivityViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SamplepointListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SamplepointListFragment extends BaseFragment {

    private static final String TAG = "SamplepointListFragment";

    private Toolbar mToolbar;

    private MainActivityViewModel mSharedViewModel;
    private FragmentSamplepointListBinding mBinding;

    private SamplepointAdapter mSamplepointAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SamplepointListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SamplepointListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SamplepointListFragment newInstance(String param1, String param2) {
        SamplepointListFragment fragment = new SamplepointListFragment();
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
            mSharedViewModel.softDeletePointById(id);
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(controller);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_samplepoint_list, container, false);
        mToolbar = mBinding.samplepointListFragmentToolbar;

        mSamplepointAdapter = new SamplepointAdapter(mItemClickCallback);

        mBinding.samplepointList.setAdapter(mSamplepointAdapter);
        itemTouchHelper.attachToRecyclerView(mBinding.samplepointList);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);

        subscribeUi();
    }

    private void subscribeUi() {
        mSharedViewModel.getPointList().observe(this, (samplepointList)->{
            if (samplepointList != null) {
                mBinding.setIsLoading(false);
                mSamplepointAdapter.setDataList(samplepointList);
            } else {
                mBinding.setIsLoading(true);
            }
            mBinding.executePendingBindings();
        });
        mBinding.buttonQuit.setOnClickListener((view)->{
            ((BasicApp)getActivity().getApplication()).Logout();
        });
        mBinding.buttonUpload.setOnClickListener((view)->{
            TestTask testTask = new TestTask();
            testTask.execute();
        });
    }

    private class TestTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            PointUploadService pointUploadService = new PointUploadService();
            pointUploadService.start();
            LandUploadService landUploadService = new LandUploadService();
            landUploadService.start();
            return null;
        }
    }

    private final ItemClickCallback<PointMainInfo> mItemClickCallback = new ItemClickCallback<PointMainInfo>() {
        @Override
        public void onClick(PointMainInfo item) {
            Intent intent = new Intent(getActivity(), SamplepointActivity.class);
            intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
            intent.putExtra(Macro.SAMPLEPOINT_ID, item.pointId);
            startActivity(intent);
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        inflater.inflate(R.menu.samplepoint_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.samplepoint_add:
                intent = new Intent(getActivity(), SamplepointActivity.class);
                intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
                intent.putExtra(Macro.SAMPLEPOINT_ID, IdGenerator.getId(SessionManager.getLoggedInUserId()));
                startActivity(intent);
                return true;
        }
        return false;
    }
}
