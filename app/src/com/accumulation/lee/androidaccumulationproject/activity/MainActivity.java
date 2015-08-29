package com.accumulation.lee.androidaccumulationproject.activity;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.accumulation.lee.androidaccumulationproject.R;
import com.accumulation.lee.androidaccumulationproject.adapter.MyRecyclerAdapter;
import com.accumulation.lee.androidaccumulationproject.adapter.RcvSupportHead$FooterViewAdapter;
import com.accumulation.lee.utils.okhttp.OnDataReceviceListener;
import com.accumulation.lee.androidaccumulationproject.demo.ServiceGenerator;
import com.accumulation.lee.androidaccumulationproject.demo.GitHubClient;
import com.lee.customwidget.DividerItemDecoration;
import com.lee.customwidget.OnBottomListener;
import com.lee.customwidget.OnRcvScrollListener;
import com.lee.customwidget.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liyong on 15/5/11.
 */
public class MainActivity extends BaseActivity implements OnDataReceviceListener {
    private RecyclerView mRecyclerView;
    private List<String> myDataset;
    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private MyRecyclerAdapter mAdapter;
    private RcvSupportHead$FooterViewAdapter mAdapterV2;

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
//        String[] functions = getResources().getStringArray(R.array.functions);
//        myDataset.addAll(Arrays.asList(functions));
        // mGridLayoutManager=new GridLayoutManager(this,2);
        for (int i = 0; i < 30; i++) {
            myDataset.add(String.valueOf(i));
        }
        mLayoutManager = new LinearLayoutManager(this);
    }


    android.os.Handler hander = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    List<String> data = (List<String>) msg.obj;
                    mAdapterV2.addDatas(data);
                    break;
            }
        }
    };

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
        mRecyclerView.addOnScrollListener(new OnRcvScrollListener(OnRcvScrollListener.LAYOUT_MANAGER_TYPE.LINEAR, new OnBottomListener() {
            @Override
            public void onBottom() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        int size = myDataset.size();
                        List<String> data = new ArrayList<String>();
                        for (int i = 1; i <= 30; i++) {
                            data.add(String.valueOf(size + i - 1));
                        }
                        Message message = hander.obtainMessage();
                        message.what = 1;
                        message.obj = data;
                        hander.sendMessage(message);
                    }
                }).start();
            }
        }));
        //mAdapter = new MyRecyclerAdapter(this, myDataset);
        mAdapterV2 = new RcvSupportHead$FooterViewAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapterV2);
        View headerView = LayoutInflater.from(this)
                .inflate(R.layout.recycler_item_view, null);
        mAdapterV2.addHeader(headerView);
        View footerView = LayoutInflater.from(this)
                .inflate(R.layout.recycler_item_view, null);
        mAdapterV2.addFooter(footerView);

        GitHubClient client = ServiceGenerator.createService(GitHubClient.class, API_URL);
        client.contributors("square", "retrofit")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GitHubClient.Contributor>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(List<GitHubClient.Contributor> contributors) {
                        for (GitHubClient.Contributor con : contributors
                                ) {
                            System.out.println(con.login+":"+con.contributions);
                        }
                        Toast.makeText(MainActivity.this,"onNext",Toast.LENGTH_SHORT).show();;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

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
        }
    };

    @Override
    public void onSuccess(String url, String jsonResponse) {

    }

    @Override
    public void onFailed(String url, int errorCode) {

    }


    public static final String API_URL = "https://api.github.com";



}
