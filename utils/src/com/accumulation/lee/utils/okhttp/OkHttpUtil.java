package com.accumulation.lee.utils.okhttp;

import com.accumulation.lee.utils.common.FileUtil;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by liyong on 15/5/31.
 */
public class OkHttpUtil {

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_MARKDOW = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final String CHARSET_NAME = "UTF-8";
    private static final String CACHE_DIRECTORY_NAME="cache_directory";

    /**
     * 设置超时
     */
    private static void configureTimeouts(){
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(60, TimeUnit.SECONDS);
    }

    private static void setCacheResponse(File cacheDirectory)  {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(cacheDirectory, cacheSize);
        mOkHttpClient.setCache(cache);
    }


    static {
       configureTimeouts();
        String sdPath= FileUtil.getSDPath();
        File cacheFile=new File(sdPath+FileUtil.FILE_EXTENSION_SEPARATOR+CACHE_DIRECTORY_NAME);
        if(!cacheFile.exists())
            cacheFile.mkdirs();
        setCacheResponse(cacheFile);
        //设置线程池
        Dispatcher dispatcher = new Dispatcher();
        mOkHttpClient.setDispatcher(dispatcher);
    }

    public static void httpGet(String url, OnDataReceviceListener dataReceviceListener,TreeMap<String, String> params) {
        GetDataRunnable getDataRunnable = new GetDataRunnable(dataReceviceListener, mOkHttpClient, url,params);
        mOkHttpClient.getDispatcher().getExecutorService().execute(getDataRunnable);
    }

    /**
     * 取消网络请求
     *
     * @param tag
     */
    public static  void cancelRequest(String tag) {
        mOkHttpClient.cancel(tag);
    }


}
