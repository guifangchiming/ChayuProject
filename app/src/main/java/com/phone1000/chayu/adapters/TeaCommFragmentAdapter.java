package com.phone1000.chayu.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2016/11/27 0027.
 */
public class TeaCommFragmentAdapter extends FragmentPagerAdapter{

    private static final String TAG = TeaCommFragmentAdapter.class.getSimpleName();
    private List<Fragment> data;

    public TeaCommFragmentAdapter(FragmentManager fm,List<Fragment> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        Log.e(TAG, "getItem: "+data.get(position) );
        return data.get(position);
    }

    @Override
    public int getCount() {
        Log.e(TAG, "getCount: "+data.size()+"");
        return data.size();
    }
}
