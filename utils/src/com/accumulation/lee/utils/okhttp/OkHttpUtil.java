package com.accumulation.lee.utils.okhttp;

import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by liyong on 15/5/31.
 */
public class OkHttpUtil {

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_MARKDOW = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final String CHARSET_NAME = "UTF-8";

    private static ExecutorService mPool;


    static {
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(60, TimeUnit.SECONDS);
        mPool = Executors.newCachedThreadPool();
        //设置线程池
        Dispatcher dispatcher = new Dispatcher(mPool);
        mOkHttpClient.setDispatcher(dispatcher);
    }

    public static void httpGet(String url, OnDataReceviceListener dataReceviceListener) {
        GetDataRunnable getDataRunnable = new GetDataRunnable(dataReceviceListener, mOkHttpClient, url);
        mPool.execute(getDataRunnable);
    }

    /**
     * 取消网络请求
     *
     * @param tag
     */
    public static  void cancelRequest(String tag) {
        Dispatcher dispatcher = mOkHttpClient.getDispatcher();
        if (null != dispatcher) {
            dispatcher.cancel(tag);
        }
    }


}
