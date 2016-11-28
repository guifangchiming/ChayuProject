package com.phone1000.chayu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.phone1000.chayu.MainActivity;
import com.phone1000.chayu.R;
import com.phone1000.chayu.TeachApp;
import com.phone1000.chayu.adapters.TeaCommFragmentAdapter;
import com.phone1000.chayu.adapters.TeaCommImageAdapter;
import com.phone1000.chayu.constants.HttpParams;
import com.phone1000.chayu.modles.TeaComm;
import com.phone1000.chayu.path.UtilPath;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class ChaPingFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private PullToRefreshScrollView scrollView;
    private PullToRefreshScrollView mPulScrallView;
    private ViewPager mViewPagerTitle;
    private TextView mlldiquText;
    private TextView mllTexView;
    private LinearLayout mLl;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TeaCommImageAdapter teaCommImageAdapter;
    private TeaComm teaComm;
    private int proviceindex = 0;
    private TeaCommFragmentAdapter adapter;

    public static final String TAG = ChaPingFragment.class.getSimpleName();
    private View layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_cha_ping, container, false);
        return layout;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        setupView();

    }

    private void setupView() {

        OkHttpUtils.post()
                .url(UtilPath.TEA_COMMEN_VIEWPAGE_TITLE)
                .addHeader(HttpParams.CACHE_CONTROL, TeachApp.getCacheControl())
                .addParams("source","3")
                .addParams("version","5")
                .addParams("imei","4731955d1f681a93")
                .addParams("versionCode","2.2.4")
                .addParams("agent","5")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: " +response);
                        Gson gson = new Gson();

                        teaComm = gson.fromJson(response, TeaComm.class);

                        setupViewPagerTitle();
                    }
                });


    }

    private void setupViewPagerTitle() {

        List<TeaComm.DataBean.BannerBean> banner = teaComm.getData().getBanner();

        List<ImageView> data = new ArrayList<ImageView>();

        for (int i = 0; i < banner.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            Log.e(TAG, "onSuccess: "+banner.get(i).getThumb() );
            data.add(imageView);
            Picasso.with(getActivity()).load(banner.get(i).getThumb()).into(imageView);
            View view = new View(getActivity());
            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,metrics);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,width);
            layoutParams.setMargins(10,0,0,0);
            view.setLayoutParams(layoutParams);
            view.setBackgroundResource(R.mipmap.dian_uncheck);
            imageView.setOnClickListener(this);

            imageView.setTag(banner.get(i).getUrl());

            mLl.addView(view);
        }

        mlldiquText.setText(banner.get(0).getTags());
        mllTexView.setText(banner.get(0).getTitle());
        mLl.getChildAt(0).setBackgroundResource(R.mipmap.dian_checked);
        teaCommImageAdapter.updataRes(data);

    }

    private void initView() {

        mPulScrallView = (PullToRefreshScrollView)layout.findViewById(R.id.tea_conment_scrollView);
        mViewPagerTitle = (ViewPager)layout.findViewById(R.id.tea_conment_title_viewpage);
        mlldiquText = (TextView) layout.findViewById(R.id.tea_conment_fenlei);
        mllTexView = (TextView) layout.findViewById(R.id.tea_conment_text);
        mLl = (LinearLayout) layout.findViewById(R.id.ll_dot);
        mTabLayout = (TabLayout) layout.findViewById(R.id.tea_conment_tab);
        mViewPager = (ViewPager) layout.findViewById(R.id.tea_conment_viewpager);

        teaCommImageAdapter = new TeaCommImageAdapter(null);
        mViewPagerTitle.setAdapter(teaCommImageAdapter);

        mViewPagerTitle.setOnPageChangeListener(this);

        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setCustomView(R.layout.tab_chalei);
        TabLayout.Tab tab1= mTabLayout.newTab();
        tab1.setCustomView(R.layout.tab_pinpai);
        TabLayout.Tab tab2 = mTabLayout.newTab();
        tab2.setCustomView(R.layout.tab_bangdan);
        mTabLayout.addTab(tab);
        mTabLayout.addTab(tab1);
        mTabLayout.addTab(tab2);

        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mlldiquText.setText(teaComm.getData().getBanner().get(position).getTags());
        mllTexView.setText(teaComm.getData().getBanner().get(position).getTitle());
        mLl.getChildAt(proviceindex).setBackgroundResource(R.mipmap.dian_uncheck);
        mLl.getChildAt(position).setBackgroundResource(R.mipmap.dian_checked);
        proviceindex = position;

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        Log.e(TAG, "onClick: "+tag );
    }
}
