package com.accumulation.lee.androidaccumulationproject.activity;

import android.app.Activity;

import java.util.LinkedList;

/**
 * Created by liyong on 15/5/11.
 */
public class CloseActivity {


    private static LinkedList<Activity> acys = new LinkedList<>();

    public static void add(Activity acy) {
        acys.add(acy);
    }

    public static void remove(Activity acy) {
        acys.remove(acy);
    }

    public static void close() {
        Activity acy;
        while (acys.size() != 0) {
            acy = acys.poll();
            if (!acy.isFinishing()) {
                acy.finish();
            }
        }
       System.exit(0);
    }
}

