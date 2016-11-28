package com.phone1000.chayu.weidgt;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.phone1000.chayu.R;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class TopBar extends LinearLayout
{
    private ImageView mTopbtn;
    private ImageView mMine;
    private ImageView mTopSerch;
    private ImageView mLogo;

    public TopBar(Context context) {
        this(context,null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.topbar,this,true);

        mTopbtn = (ImageView) findViewById(R.id.top_right_btn);
        mMine = (ImageView) findViewById(R.id.top_mine_btn);
        mTopSerch = (ImageView) findViewById(R.id.top_share_btn);
        mLogo = (ImageView) findViewById(R.id.chayu_logo);

    }


    public void logoVisible(boolean b){

        if (b) {
            mLogo.setVisibility(VISIBLE);
        }else{
            mLogo.setVisibility(INVISIBLE);
        }
    }

    public void topshareVisible(boolean b){

        if(b){
            mTopSerch.setVisibility(VISIBLE);
        }else {
            mTopSerch.setVisibility(INVISIBLE);
        }

    }


}
