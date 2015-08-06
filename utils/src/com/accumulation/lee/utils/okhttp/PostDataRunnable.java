package com.accumulation.lee.utils.okhttp;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liyong on 15/6/2.
 */
public class PostDataRunnable implements Runnable {

    private OkHttpClient okHttpClient;

    private String url;

    private OnDataReceviceListener dataReceviceListener;

    private TreeMap<String, String> params;

   // private Handler mHandler=new Handler();

    public PostDataRunnable(OnDataReceviceListener dataReceviceListener, OkHttpClient okHttpClient, String url, TreeMap<String, String> params) {
        this.dataReceviceListener = dataReceviceListener;
        this.okHttpClient = okHttpClient;
        this.url = url;
        this.params=params;
    }

    @Override
    public void run() {
        try {
            Request.Builder requestBuilder = new Request.Builder();
            if (null != params && params.size() > 0) {
                FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                   formEncodingBuilder.add(entry.getKey(),entry.getValue());
                }
                RequestBody requestBody=formEncodingBuilder.build();
                requestBuilder.post(requestBody);
            }
            requestBuilder.url(url);
            requestBuilder.tag(url);

            Request request = requestBuilder.build();
            Call call=okHttpClient.newCall(request);
            Response response =call.execute();
            if (response.isSuccessful()) {
                String result = response.body().toString();
                if (null != dataReceviceListener)
                    dataReceviceListener.onSuccess(url, result);
            } else {
                if (null != dataReceviceListener) {
                    int errorCode = response.code();
                    dataReceviceListener.onFailed(url, errorCode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
