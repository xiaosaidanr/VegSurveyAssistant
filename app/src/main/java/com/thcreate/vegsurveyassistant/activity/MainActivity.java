package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityMainBinding;
import com.thcreate.vegsurveyassistant.viewmodel.MainActivityViewModel;
import com.thcreate.vegsurveyassistant.fragment.BaseFragment;
import com.thcreate.vegsurveyassistant.fragment.MyFragment;
import com.thcreate.vegsurveyassistant.fragment.NearbyFragment;
import com.thcreate.vegsurveyassistant.fragment.YangdianFragment;
import com.thcreate.vegsurveyassistant.fragment.YangfangFragment;

public class MainActivity extends BaseActivity {

    private NearbyFragment nearbyFragment;
    private YangfangFragment yangfangFragment;
    private YangdianFragment yangdianFragment;
    private MyFragment myFragment;

    private BaseFragment currentFragment;

    private BottomNavigationView navigation;

    private MainActivityViewModel mViewModel;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);

//        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            if (getSupportFragmentManager().findFragmentByTag(NearbyFragment.class.getName())!=null){
                nearbyFragment = (NearbyFragment) getSupportFragmentManager().findFragmentByTag(NearbyFragment.class.getName());
                if (!nearbyFragment.isHidden()){
                    currentFragment = nearbyFragment;
                }
            }
            if (getSupportFragmentManager().findFragmentByTag(YangfangFragment.class.getName())!=null){
                yangfangFragment = (YangfangFragment) getSupportFragmentManager().findFragmentByTag(YangfangFragment.class.getName());
                if (!yangfangFragment.isHidden()){
                    currentFragment = yangfangFragment;
                }
            }
            if (getSupportFragmentManager().findFragmentByTag(YangdianFragment.class.getName())!=null){
                yangdianFragment = (YangdianFragment) getSupportFragmentManager().findFragmentByTag(YangdianFragment.class.getName());
                if (!yangdianFragment.isHidden()){
                    currentFragment = yangdianFragment;
                }
            }
            if (getSupportFragmentManager().findFragmentByTag(MyFragment.class.getName())!=null){
                myFragment = (MyFragment) getSupportFragmentManager().findFragmentByTag(MyFragment.class.getName());
                if (!myFragment.isHidden()){
                    currentFragment = myFragment;
                }
            }
            Log.e("Fragment", currentFragment.getClass().getName());
        }
        initSetLayoutId();
        initData();
        initListener();
//        // 设置默认的Fragment
//        if (savedInstanceState == null){
//            setDefaultFragment();
//        }
    }

//    private void setDefaultFragment(){
//        nearbyFragment = NearbyFragment.newInstance(null, null);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.fragment_container, nearbyFragment).commit();
//    }

    private void initSetLayoutId(){
        navigation = findViewById(R.id.navigation);
    }

    private void initData(){
        if (currentFragment != null){
            return;
        }
        if (nearbyFragment == null){
            nearbyFragment = NearbyFragment.newInstance(null, null);
        }
        if (!nearbyFragment.isAdded()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, nearbyFragment, nearbyFragment.getClass().getName())
                    .commit();
            currentFragment = nearbyFragment;
            nearbyFragment.setUserVisibleHint(true);
        }
    }

    private void initListener(){
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_nearby:
                        clickNearbyLayout();
                        return true;
                    case R.id.navigation_yangfang:
                        clickYangfangLayout();
                        return true;
                    case R.id.navigation_yangdian:
                        clickYangdianLayout();
                        return true;
                    case R.id.navigation_my:
                        clickMyLayout();
                        return true;
                }
                return false;
            }
        });
    }

    private void clickNearbyLayout(){

        if (nearbyFragment == null){
            nearbyFragment =NearbyFragment.newInstance(null, null);
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), nearbyFragment);
    }

    private void clickYangfangLayout(){
        if (yangfangFragment == null){
            yangfangFragment = YangfangFragment.newInstance(null, null);
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), yangfangFragment);
    }

    private void clickYangdianLayout(){
        if (yangdianFragment == null){
            yangdianFragment = YangdianFragment.newInstance(null, null);
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), yangdianFragment);
    }

    private void clickMyLayout(){
        if (myFragment == null){
            myFragment = MyFragment.newInstance(null, null);
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), myFragment);
    }

    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment){
        if (currentFragment == fragment)
            return;
        if (!fragment.isAdded()){
            transaction.hide(currentFragment)
                    .add(R.id.fragment_container, fragment, fragment.getClass().getName())
                    .commit();
        }
        else{
            transaction.hide(currentFragment)
                    .show(fragment)
                    .commit();
        }
        currentFragment.setUserVisibleHint(false);//和懒加载有关 原理还没弄清 暂时也注释掉
        currentFragment = (BaseFragment) fragment;
        currentFragment.setUserVisibleHint(true);//和懒加载有关 原理还没弄清 暂时也注释掉
    }
}
