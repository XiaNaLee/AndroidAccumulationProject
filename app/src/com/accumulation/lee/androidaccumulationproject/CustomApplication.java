package com.accumulation.lee.androidaccumulationproject;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by liyong on 15/5/6.
 */
public class CustomApplication extends MultiDexApplication {

    private static CustomApplication mCustomApplication;
    private RefWatcher mRefWatcher;//内存泄露检查类

    public static CustomApplication getInstance() {
        return mCustomApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCustomApplication = this;
        mRefWatcher = LeakCanary.install(this);
        //facebook开源的图片加载工具
        Fresco.initialize(mCustomApplication);
    }


    public static RefWatcher getRefWatcher() {
        return mCustomApplication.mRefWatcher;
    }
}
