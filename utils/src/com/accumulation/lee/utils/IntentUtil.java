package com.accumulation.lee.utils;

import android.content.Intent;

/**
 * Created by liyong on 15/5/31.
 */
public class IntentUtil {
    /**
     * 判断intent和它的bundle是否为空
     *
     * @param intent
     * @return
     */
    public static boolean isBundleEmpty(Intent intent) {
        return (intent == null) && (intent.getExtras() == null);
    }
}
