package com.accumulation.lee.androidaccumulationproject.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.accumulation.lee.androidaccumulationproject.R;
import com.accumulation.lee.utils.okhttp.OkHttpUtil;
import com.accumulation.lee.utils.okhttp.OnDataReceviceListener;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.AndroidPredicates;
import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class LoginActivity extends BaseActivity {
    private PtrClassicFrameLayout ptrFrameLayout;
    private SimpleDraweeView simpleDraweeView;
    private String testUrl = "http://img4.imgtn.bdimg.com/it/u=4274835636,2877046532&fm=21&gp=0.jpg";
    private TextView tv;

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
        simpleDraweeView = getView(R.id.my_image_view);
        tv = getView(R.id.tv);
    }

    private String url = "http://www.joes-hardware.com/tools.html";
    private boolean isStartRQ = true;

    public void click(View view) {
        if (isStartRQ) {
            OkHttpUtil.httpGet(url, new OnDataReceviceListener() {
                @Override
                public void onSuccess(String url, final String jsonResponse) {
                    simpleDraweeView.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "" + jsonResponse, Toast.LENGTH_SHORT).show();
                            tv.setText(jsonResponse);
                        }
                    });
                }

                @Override
                public void onFailed(String url) {
                    simpleDraweeView.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "onFailed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            isStartRQ = false;
        } else {
            isStartRQ = true;
            OkHttpUtil.cancelRequest(url);
        }
    }

    @Override
    protected void initDataBeforeSetViews() {

    }


    @Override
    protected void setViews() {
        //设置宽高比
        simpleDraweeView.setAspectRatio(1.33f);
        Uri uri = Uri.parse(testUrl);
        // simpleDraweeView.setImageURI(uri);
        setImageDraweeController(uri);
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
                return true;
            }
        });
        // the following are default settings
        ptrFrameLayout.setResistance(1.7f);
        ptrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrameLayout.setDurationToClose(200);
        ptrFrameLayout.setDurationToCloseHeader(1000);
        // default is false
        ptrFrameLayout.setPullToRefresh(false);
        // default is true
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

    private void setImageDraweeController(Uri uri) {
        //修改图片
        Postprocessor myPostprocessor = new BasePostprocessor() {
            @Override
            public String getName() {
                return super.getName();
            }

            @Override
            public void process(Bitmap bitmap) {
                super.process(bitmap);
                for (int x = 0; x < bitmap.getWidth(); x += 2) {
                    for (int y = 0; y < bitmap.getHeight(); y += 2) {
                        bitmap.setPixel(x, y, Color.RED);
                    }
                }
            }

            @Override
            public void process(Bitmap destBitmap, Bitmap sourceBitmap) {
                super.process(destBitmap, sourceBitmap);
            }

            @Override
            public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
                return super.process(sourceBitmap, bitmapFactory);
            }
        };

        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setAutoRotateEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)//允许设置一个最低请求级别
                .setResizeOptions(new ResizeOptions(50, 50))
                .setPostprocessor(myPostprocessor)
                .setProgressiveRenderingEnabled(true)
                .build();

        //监听下载事件,对所有的图片加载，onFinalImageSet 或者 onFailure 都会被触发。前者在成功时，后者在失败时。
        ControllerListener listener = new BaseControllerListener() {
            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
            }

            @Override
            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
            }

            @Override
            public void onIntermediateImageFailed(String id, Throwable throwable) {
                super.onIntermediateImageFailed(id, throwable);
            }

            @Override
            public void onIntermediateImageSet(String id, Object imageInfo) {
                super.onIntermediateImageSet(id, imageInfo);
            }

            @Override
            public void onRelease(String id) {
                super.onRelease(id);
            }

            @Override
            public void onSubmit(String id, Object callerContext) {
                super.onSubmit(id, callerContext);
            }
        };

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                        // .setUri(Uri.parse(url))
                .setTapToRetryEnabled(true)
                .setOldController(simpleDraweeView.getController())//在指定一个新的controller的时候，使用setOldController，这可节省不必要的内存分配。
                .setControllerListener(listener)
                .build();

        simpleDraweeView.setController(controller);

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



