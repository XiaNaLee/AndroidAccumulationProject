package com.accumulation.lee.androidaccumulationproject.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.accumulation.lee.androidaccumulationproject.R;
import com.accumulation.lee.androidaccumulationproject.adapter.MyRecyclerAdapter;
import com.accumulation.lee.utils.okhttp.OkHttpUtil;
import com.accumulation.lee.utils.okhttp.OnDataReceviceListener;
import com.lee.customwidget.DividerItemDecoration;
import com.lee.customwidget.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * Created by liyong on 15/5/11.
 */
public class MainActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<String> myDataset;
    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private MyRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void beforeSetContentView() {
        super.beforeSetContentView();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        mRecyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected void initDataBeforeSetViews() {
        myDataset = new ArrayList<>();
        String []functions=getResources().getStringArray(R.array.functions);
        myDataset.addAll(Arrays.asList(functions));
       // mGridLayoutManager=new GridLayoutManager(this,2);
        mLayoutManager = new LinearLayoutManager(this);
    }


    @Override
    protected void setViews() {
        mRecyclerView.setHasFixedSize(true);
        //mLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 5, R.color.blue));
        //mRecyclerView.addItemDecoration(new SpacesItemDecoration(DisplayUtil.dip2px(this,5)));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, onItemClickListener));
        mRecyclerView.setItemAnimator(new FadeInAnimator());//设置item的动画
        mRecyclerView.getItemAnimator().setAddDuration(500);
        mRecyclerView.getItemAnimator().setRemoveDuration(500);
        mAdapter = new MyRecyclerAdapter(this, myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
//            if (position % 2 == 0) {
//                mAdapter.remove(position);
//            } else {
//                mAdapter.add(position,String.valueOf(position));
//            }
            TreeMap<String,String> param=new TreeMap<>();
            param.put("name","lee");
            OkHttpUtil.httpGet("https://www.baidu.com", new OnDataReceviceListener() {
                @Override
                public void onSuccess(String url, String jsonResponse) {
                    System.out.println("url " + jsonResponse);
                }

                @Override
                public void onFailed(String url, int errorCode) {
                    System.out.println("url "+errorCode);
                }
            },param);
        }
    };
}
