package com.thcreate.vegsurveyassistant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.thcreate.vegsurveyassistant.fragment.MyFragment;
import com.thcreate.vegsurveyassistant.fragment.NearbyFragment;
import com.thcreate.vegsurveyassistant.fragment.YangdianFragment;
import com.thcreate.vegsurveyassistant.fragment.YangfangFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Fragment nearbyFragment;
    private Fragment yangfangFragment;
    private Fragment yangdianFragment;
    private Fragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("11111111111111111", "onCreate MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                switch (menuItem.getItemId()) {
                    case R.id.navigation_nearby:
                        if (nearbyFragment == null){
                            nearbyFragment = NearbyFragment.newInstance(null, null);
                        }
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, nearbyFragment).commit();
                        return true;
                    case R.id.navigation_yangfang:
                        if (yangfangFragment == null){
                            yangfangFragment = YangfangFragment.newInstance(null, null);
                        }
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, yangfangFragment).commit();
                        return true;
                    case R.id.navigation_yangdian:
                        if (yangdianFragment == null){
                            yangdianFragment = YangdianFragment.newInstance(null, null);
                        }
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, yangdianFragment).commit();
                        return true;
                    case R.id.navigation_my:
                        if (myFragment == null){
                            myFragment = MyFragment.newInstance(null, null);
                        }
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, myFragment).commit();
                        return true;
                }
                return false;
            }
        });

        // 设置默认的Fragment
        if (savedInstanceState == null){
            setDefaultFragment();
        }
    }

    private void setDefaultFragment(){
        nearbyFragment = NearbyFragment.newInstance(null, null);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, nearbyFragment).commit();
    }

}
