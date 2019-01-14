package com.thcreate.vegsurveyassistant.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.thcreate.vegsurveyassistant.activity.HerbLandActivity;
import com.thcreate.vegsurveyassistant.activity.ShrubLandActivity;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.ArborLandActivity;
import com.thcreate.vegsurveyassistant.customUi.NoScrollViewPager;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SampleplotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SampleplotFragment extends BaseFragment {

    private static final String TAG = "SampleplotFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TabAdapter mTabAdapter;
    private NoScrollViewPager mViewPager;
    private TabLayout mTabLayout;

    private Toolbar mToolbar;


    public SampleplotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SampleplotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SampleplotFragment newInstance(String param1, String param2) {
        SampleplotFragment fragment = new SampleplotFragment();
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
        View view = inflater.inflate(R.layout.fragment_sampleplot, container, false);
        mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mTabAdapter = new TabAdapter(getChildFragmentManager());
        mViewPager = view.findViewById(R.id.viewpager);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        inflater.inflate(R.menu.sampleplot_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Class activityClazz = null;
        String samplelandType = "";
        switch (item.getItemId()){
            case R.id.herbland_add:
                activityClazz = HerbLandActivity.class;
                samplelandType = Macro.SAMPLELAND_TYPE_GRASS;
                break;
            case R.id.shrubland_add:
                activityClazz = ShrubLandActivity.class;
                samplelandType = Macro.SAMPLELAND_TYPE_BUSH;
                break;
            case R.id.arborland_add:
                activityClazz = ArborLandActivity.class;
                samplelandType = Macro.SAMPLELAND_TYPE_TREE;
                break;
            default:
                break;
        }
        if (activityClazz != null){
            Intent intent = new Intent(getActivity(), activityClazz);
            intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
            intent.putExtra(Macro.SAMPLELAND_ID, IdGenerator.getId(SessionManager.getLoggedInUserId()));
            intent.putExtra(Macro.SAMPLELAND_TYPE, samplelandType);
            startActivity(intent);
            return true;
        }
        else {
            return false;
        }
    }

    public class TabAdapter extends FragmentPagerAdapter{

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        private String tabTitles[] = new String[] {
                getResources().getString(R.string.grass),
                getResources().getString(R.string.bush),
                getResources().getString(R.string.forest)
        };

        @Override
        public Fragment getItem(int i) {
            if (i==0){
                return HerbLandListFragment.newInstance(null, null);
            }
            if (i==1){
                return ShrubLandListFragment.newInstance(null, null);
            }
            if (i==2){
                return ArborLandListFragment.newInstance(null, null);
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
