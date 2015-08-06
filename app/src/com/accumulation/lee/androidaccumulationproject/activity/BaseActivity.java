package com.accumulation.lee.androidaccumulationproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.accumulation.lee.androidaccumulationproject.CustomApplication;

/**
 * Created by liyong on 15/5/6.
 */
public abstract class BaseActivity extends Activity {
     protected Context mContext;
     protected String TAG;
    @Override
    protected void onStart() {
        super.onStart();
        TAG=this.getClass().getName();
        Log.d(TAG,"onStart"+ "invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart"+ "invoked");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()方法" + "invoked");
        mContext= CustomApplication.getInstance();
        CloseActivity.add(this);
        beforeSetContentView();
        if (getContentViewId() != 0) {
            setContentView(getContentViewId());
        }
        findViews();
        initDataBeforeSetViews();
        setViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()方法" + "invoked");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG,"onNewIntent()方法"+ "invoked");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(this.getClass().getName(),"onSaveInstanceState()方法"+"invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(this.getClass().getName(),"onPause()方法"+ "invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(this.getClass().getName(), "onStop()方法" + "invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(this.getClass().getName(),"onDestroy()方法"+"invoked");
        CloseActivity.remove(this);
    }

    /**
     * 通过泛型来简化findViewById
     * <E extends View>申明此方法为范型方法
     */
    protected final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            Log.e(TAG, "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }

    /**
     * 在setContentView之前触发的方法
     */
    protected void beforeSetContentView() {

    }

    /**
     * 如果没有布局，那么就返回0
     *
     * @return activity的布局文件
     */
    protected abstract int getContentViewId();

    /**
     * 找到所有的views
     */
    protected abstract void findViews();

    /**
     * 在这里初始化设置view的各种资源，比如适配器或各种变量
     */
    protected abstract void initDataBeforeSetViews();

    /**
     * 设置所有的view
     */
    protected abstract void setViews();

}
