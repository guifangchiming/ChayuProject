package com.phone1000.chayu;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.phone1000.chayu.constants.HttpParams;
import com.phone1000.chayu.utils.NetWorkUtil;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class TeachApp extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        Picasso picasso = new Picasso.Builder(this)
                .loggingEnabled(true)
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .indicatorsEnabled(true)
                .build();
        Picasso.setSingletonInstance(picasso);

        context = this;

        // 初始化OkHttpUtil

        // 实例化请求缓存
        Cache cache = new Cache(getCacheDir(),50 * 1024 * 1024);
        // 添加网络拦截器
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // 获取到这次的这个请求
                Request request = chain.request();
                if (!NetWorkUtil.isConnected(TeachApp.this)) {
                    request = request.newBuilder()
                            // 当没有网络的时候，我们将请求设置为强制从缓存中获取
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                // 获取请求的Response
                Response response = chain.proceed(request);
                if (NetWorkUtil.isConnected(TeachApp.this)) {
                    // 获取请求中的缓存规则
                    String cacheControl = request.cacheControl().toString();
                    response = response.newBuilder()
                            // 修改Response结构，让Response支持缓存的形式
                            .removeHeader(HttpParams.CACHE_CONTROL)
                            .removeHeader(HttpParams.PARMA)
                            .addHeader(HttpParams.CACHE_CONTROL,cacheControl)
                            .build();
                }else{
                    // 没有网络的时候，缓存策略设置为直接从缓存中获取，哪怕缓存是过期的
                    response = response.newBuilder()
                            .removeHeader(HttpParams.CACHE_CONTROL)
                            .removeHeader(HttpParams.PARMA)
                            // 配置缓存策略 共有的，仅从缓存中获取，可接受最大过期时间
                            .addHeader(HttpParams.CACHE_CONTROL,"public, only-if-cached, max-stale=" + 2 * 60 * 60)
                            .build();
                }
                return response;
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                // 添加缓存
                .cache(cache)
                // 添加一个自定义的网络拦截器
                .addNetworkInterceptor(cacheInterceptor)

                .build();
        OkHttpUtils.initClient(client);
    }

    public static String getCacheControl(){
        return NetWorkUtil.isConnected(context)?"max-age=15":"only-if-cache,max-stale=" + 2 * 60 * 60;
    }


}
