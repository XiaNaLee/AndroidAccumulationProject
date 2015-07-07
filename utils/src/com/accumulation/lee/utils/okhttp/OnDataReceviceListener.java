package com.accumulation.lee.utils.okhttp;

/**
 * Created by liyong on 15/6/2.
 */
public interface OnDataReceviceListener {

    void onSuccess(String url,String jsonResponse);

    void onFailed(String url);
}
