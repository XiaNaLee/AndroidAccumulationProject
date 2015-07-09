package com.accumulation.lee.androidaccumulationproject.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.accumulation.lee.androidaccumulationproject.R;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.AndroidPredicates;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineFactory;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class LoginActivity extends BaseActivity {
    private PtrClassicFrameLayout ptrFrameLayout;
    private WebView webView;

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
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        ptrFrameLayout = getView(R.id.store_house_ptr_frame);
        webView=getView(R.id.web_view);
    }


    @Override
    protected void initDataBeforeSetViews() {

    }


    @Override
    protected void setViews() {
        webView.loadUrl("https://www.baidu.com");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                }, 1800);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if(webView.getScrollY()==0){
                    return true;
                }else{
                    return false;
                }
            }
        });
        //阻尼系数
        ptrFrameLayout.setResistance(1.7f);
        //触发刷新时移动的位置比例.默认，1.2f，移动达到头部高度1.2倍时可触发刷新操作
        ptrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        //回弹延迟.默认 200ms，回弹到刷新高度所用时间
        ptrFrameLayout.setDurationToClose(200);
        //头部回弹时间
        ptrFrameLayout.setDurationToCloseHeader(1000);
        //下拉刷新 / 释放刷新
        ptrFrameLayout.setPullToRefresh(false);
        //刷新是保持头部
        ptrFrameLayout.setKeepHeaderWhenRefresh(true);
        ptrFrameLayout.setEnabledNextPtrAtOnce(true);

        // scroll then refresh
        // comment in base fragment
        ptrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrameLayout.autoRefresh();
            }
        }, 150);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
        CloseActivity.close();
    }

    /**
     * 清空缓存
     */
    public void clearFrescoCache() {
        ImagePipelineFactory imagePipelineFactory = Fresco.getImagePipelineFactory();
        imagePipelineFactory.getBitmapMemoryCache().removeAll(AndroidPredicates.<CacheKey>True());
        imagePipelineFactory.getEncodedMemoryCache().removeAll(AndroidPredicates.<CacheKey>True());
        imagePipelineFactory.getMainDiskStorageCache().clearAll();
        imagePipelineFactory.getSmallImageDiskStorageCache().clearAll();
    }
}



