package com.accumulation.lee.utils.okhttp;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liyong on 15/6/2.
 */
public class GetDataRunnable implements Runnable {

    private OkHttpClient okHttpClient;

    private String url;

    private OnDataReceviceListener dataReceviceListener;

    private TreeMap<String, String> params;

    public GetDataRunnable(OnDataReceviceListener dataReceviceListener, OkHttpClient okHttpClient, String url,TreeMap<String, String> params) {
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
                String appendUrlParam = "";
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    appendUrlParam += appendUrlParam = "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), HTTP.UTF_8);
                }
                if (!appendUrlParam.equals("")) {
                    appendUrlParam = appendUrlParam.replaceFirst("&", "?");
                    url += appendUrlParam;
                }

            }
            requestBuilder.url(url);
            requestBuilder.tag(url);
            // Request request = new Request.Builder().url(url).tag(url).build();
            Request request = requestBuilder.build();
            Response response = okHttpClient.newCall(request).execute();
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
