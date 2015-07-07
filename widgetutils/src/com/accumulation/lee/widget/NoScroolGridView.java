package com.accumulation.lee.widget;

/**
 * Created by lee on 15/7/7.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by liyong on 15/3/24.
 */
public class NoScroolGridView extends GridView {
    private boolean isCanDealTouch = true;

    public NoScroolGridView(Context context) {
        super(context);
    }

    public NoScroolGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置MotionEvent是否自己处理
     * @param isCanDealTouch
     */
    public void setCanDealTouchByself(boolean isCanDealTouch) {
        this.isCanDealTouch = isCanDealTouch;
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanDealTouch) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return true;//拦截事件自己处理，禁止向下传递
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanDealTouch) {
            return super.onTouchEvent(ev);
        } else {
            return false;// false 自己不处理此次事件以及后续的事件，那么向上传递给外层view
        }
    }
}
