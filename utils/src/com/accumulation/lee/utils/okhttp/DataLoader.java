package com.accumulation.lee.utils.okhttp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liyong on 15/6/2.
 */
public class DataLoader {

    private ExecutorService mPool;

    private static DataLoader dataLoader;

    private DataLoader(){
        mPool= Executors.newCachedThreadPool();

    }

}
