package com.accumulation.lee.androidaccumulationproject.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.accumulation.lee.androidaccumulationproject.CustomApplication;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by liyong on 15/5/11.
 */
public class BaseFragment extends Fragment {


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher= CustomApplication.getRefWatcher();
        refWatcher.watch(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
