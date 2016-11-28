package com.phone1000.chayu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.phone1000.chayu.fragments.ChaPingFragment;
import com.phone1000.chayu.fragments.HomeFragment;
import com.phone1000.chayu.fragments.QuanZiFragment;
import com.phone1000.chayu.fragments.ShiJiFragment;
import com.phone1000.chayu.fragments.WenZhangFragment;
import com.phone1000.chayu.weidgt.TopBar;

import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup main_rg;
    private Fragment showfragment;
    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        main_rg = ((RadioGroup) findViewById(R.id.main_controller));
        main_rg.setOnCheckedChangeListener(this);
        mTopBar = (TopBar) findViewById(R.id.top_bar);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        showfragment = new HomeFragment();
        transaction.add(R.id.main_container,showfragment,HomeFragment.TAG);
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){

            case R.id.main_controller_home:
                switchpage(HomeFragment.TAG,HomeFragment.class);
                mTopBar.logoVisible(true);
                mTopBar.topshareVisible(false);
                break;
            case R.id.main_controller_cha_ping:
                switchpage(ChaPingFragment.TAG,ChaPingFragment.class);
                mTopBar.logoVisible(false);
                mTopBar.topshareVisible(true);
                break;
            case R.id.main_controller_shi_ji:
                switchpage(ShiJiFragment.TAG,ShiJiFragment.class);
                mTopBar.logoVisible(false);
                mTopBar.topshareVisible(true);
                break;
            case R.id.main_controller_quan_zi:
                switchpage(QuanZiFragment.TAG,QuanZiFragment.class);
                mTopBar.logoVisible(false);
                mTopBar.topshareVisible(true);
                break;
            case R.id.main_controller_wen_zhang:
                switchpage(WenZhangFragment.TAG,WenZhangFragment.class);
                mTopBar.logoVisible(false);
                mTopBar.topshareVisible(true);
                break;
        }
    }
    private void switchpage(String tag, Class<? extends Fragment> cls) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(showfragment);
        showfragment = (Fragment) manager.findFragmentByTag(tag);
        if (showfragment != null) {
            transaction.show(showfragment);
        }else {
            try {
                showfragment = cls.getConstructor().newInstance();
                transaction.add(R.id.main_container,showfragment,tag);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        transaction.commit();
    }

}
