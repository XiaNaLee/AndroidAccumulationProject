package com.accumulation.lee.utils.okhttp;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by liyong on 15/6/2.
 */
public class GetDataRunnable implements Runnable {

    private OkHttpClient okHttpClient;

    private String url;

    private OnDataReceviceListener dataReceviceListener;

    public GetDataRunnable(OnDataReceviceListener dataReceviceListener, OkHttpClient okHttpClient, String url) {
        this.dataReceviceListener = dataReceviceListener;
        this.okHttpClient = okHttpClient;
        this.url = url;
    }

    @Override
    public void run() {
        Request request = new Request.Builder().url(url).tag(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result=response.body().toString();
                dataReceviceListener.onSuccess(url,result);
            } else {
                dataReceviceListener.onFailed(url);
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
