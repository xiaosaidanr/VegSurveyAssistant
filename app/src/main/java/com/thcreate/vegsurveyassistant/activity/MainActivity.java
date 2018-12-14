package com.thcreate.vegsurveyassistant.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityMainBinding;
import com.thcreate.vegsurveyassistant.fragment.SamplepointListFragment;
import com.thcreate.vegsurveyassistant.viewmodel.MainActivityViewModel;
import com.thcreate.vegsurveyassistant.fragment.MyFragment;
import com.thcreate.vegsurveyassistant.fragment.NearbyFragment;
import com.thcreate.vegsurveyassistant.fragment.SampleplotFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private final int SDK_PERMISSION_REQUEST = 127;
    private String mPermissionInfo;

    private NearbyFragment nearbyFragment;
    private SampleplotFragment sampleplotFragment;
    private SamplepointListFragment samplepointListFragment;
    private MyFragment myFragment;

//    private BaseFragment currentFragment;
    private Fragment currentFragment;

    private BottomNavigationView navigation;

    private MainActivityViewModel mViewModel;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPermissions();

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
            if (getSupportFragmentManager().findFragmentByTag(SampleplotFragment.class.getName())!=null){
                sampleplotFragment = (SampleplotFragment) getSupportFragmentManager().findFragmentByTag(SampleplotFragment.class.getName());
                if (!sampleplotFragment.isHidden()){
                    currentFragment = sampleplotFragment;
                }
            }
            if (getSupportFragmentManager().findFragmentByTag(SamplepointListFragment.class.getName())!=null){
                samplepointListFragment = (SamplepointListFragment) getSupportFragmentManager().findFragmentByTag(SamplepointListFragment.class.getName());
                if (!samplepointListFragment.isHidden()){
                    currentFragment = samplepointListFragment;
                }
            }
            if (getSupportFragmentManager().findFragmentByTag(MyFragment.class.getName())!=null){
                myFragment = (MyFragment) getSupportFragmentManager().findFragmentByTag(MyFragment.class.getName());
                if (!myFragment.isHidden()){
                    currentFragment = myFragment;
                }
            }
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

    @TargetApi(23)
    private void getPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ArrayList<String> permissions = new ArrayList<>();
            /*
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                mPermissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny\n";
            }
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)){
                mPermissionInfo += "Manifest.permission.READ_PHONE_STATE Deny\n";
            }
            if (permissions.size() > 0){
                ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }
    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }
        }else{
            return true;
        }
    }
    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

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
                    case R.id.navigation_sampleplot:
                        clickSampleplotLayout();
                        return true;
                    case R.id.navigation_samplepoint:
                        clickSamplepointLayout();
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

    private void clickSampleplotLayout(){
        if (sampleplotFragment == null){
            sampleplotFragment = SampleplotFragment.newInstance(null, null);
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), sampleplotFragment);
    }

    private void clickSamplepointLayout(){
        if (samplepointListFragment == null){
            samplepointListFragment = SamplepointListFragment.newInstance(null, null);
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), samplepointListFragment);
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
        currentFragment = fragment;
        currentFragment.setUserVisibleHint(true);//和懒加载有关 原理还没弄清 暂时也注释掉
    }
}
