package com.thcreate.vegsurveyassistant.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.thcreate.vegsurveyassistant.activity.CaodiyangdiActivity;
import com.thcreate.vegsurveyassistant.activity.GuancongyangdiActivity;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.SenlinyangdiActivity;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.lang.reflect.Type;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YangfangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YangfangFragment extends BaseFragment {

    private static final String TAG = "YangfangFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private YangfangTabAdapter mYangfangTabAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private Toolbar mToolbar;


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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onCreateView" + " " + this.toString());
        View view = inflater.inflate(R.layout.fragment_yangfang, container, false);
        mToolbar = view.findViewById(R.id.yangfang_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mYangfangTabAdapter = new YangfangTabAdapter(getChildFragmentManager());
        mViewPager = view.findViewById(R.id.yangfang_viewpager);
        mTabLayout = view.findViewById(R.id.yangfang_tab_bar);
        mViewPager.setAdapter(mYangfangTabAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        inflater.inflate(R.menu.yangfang_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Class activityClazz = null;
        String yangdiType = "";
        switch (item.getItemId()){
            case R.id.caodiyangfang_add:
                activityClazz = CaodiyangdiActivity.class;
                yangdiType = Macro.YANGDI_TYPE_GRASS;
                break;
            case R.id.guancongyangfang_add:
                activityClazz = GuancongyangdiActivity.class;
                yangdiType = Macro.YANGDI_TYPE_BUSH;
                break;
            case R.id.senlinyangfang_add:
                activityClazz = SenlinyangdiActivity.class;
                yangdiType = Macro.YANGDI_TYPE_TREE;
                break;
            default:
                break;
        }
        if (activityClazz != null){
            Intent intent = new Intent(getActivity(), activityClazz);
            intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
            //TODO userid1
            intent.putExtra(Macro.YANGDI_CODE, IdGenerator.getId(1, Yangdi.class));
            intent.putExtra(Macro.YANGDI_TYPE, yangdiType);
            startActivity(intent);
            return true;
        }
        else {
            return false;
        }
    }

    public class YangfangTabAdapter extends FragmentPagerAdapter{

        public YangfangTabAdapter(FragmentManager fm) {
            super(fm);
        }

        private String tabTitles[] = new String[] {
                getResources().getString(R.string.caodi),
                getResources().getString(R.string.guancong),
                getResources().getString(R.string.senlin)
        };

        @Override
        public Fragment getItem(int i) {
            Log.d(TAG, "getItem " + String.valueOf(i));
            if (i==0){
                return CaodiListFragment.newInstance(null, null);
            }
            if (i==1){
                return GuancongListFragment.newInstance(null, null);
            }
            if (i==2){
                return SenlinListFragment.newInstance(null, null);
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
