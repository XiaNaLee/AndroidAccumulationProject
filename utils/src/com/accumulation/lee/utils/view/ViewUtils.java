package com.accumulation.lee.utils.view;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.EditText;

import com.accumulation.lee.utils.common.StringUtils;

/**
 * Created by liyong on 15/5/6.
 */
public class ViewUtils {

    /**
     * 设置view组件gone或者visible
     *
     * @param view
     * @param gone
     * @return view
     */
    public static <V extends View> V setGone(final V view, final boolean gone) {
        if (view != null)
            if (gone) {
                if (View.GONE != view.getVisibility())
                    view.setVisibility(View.GONE);
            } else {
                if (View.VISIBLE != view.getVisibility())
                    view.setVisibility(View.VISIBLE);
            }
        return view;
    }

    /**
     * 设置view组件invisible或者visible
     *
     * @param view
     * @param invisible
     * @return view
     */
    public static <V extends View> V setInvisible(final V view,
                                                  final boolean invisible) {
        if (view != null)
            if (invisible) {
                if (View.INVISIBLE != view.getVisibility())
                    view.setVisibility(View.INVISIBLE);
            } else {
                if (View.VISIBLE != view.getVisibility())
                    view.setVisibility(View.VISIBLE);
            }
        return view;
    }

    /**
     * 增加view的点击区域，当view是一张小图或者不方便点击时可采用此方法
     *
     * @param amount
     * @param delegate
     */
    public static void increaseHitRectBy(final float amount, final View delegate) {
        increaseHitRectBy(amount, amount, amount, amount, delegate);
    }

    /**
     *
     * @param top
     * @param left
     * @param bottom
     * @param right
     * @param delegate
     */
    public static void increaseHitRectBy(final float top, final float left,
                                         final float bottom, final float right, final View delegate) {
        final View parent = (View) delegate.getParent();
        if (parent != null && delegate.getContext() != null) {
            parent.post(new Runnable() {
                // Post in the parent's message queue to make sure the parent
                // lays out its children before we call getHitRect()
                public void run() {
                    final float densityDpi = delegate.getContext()
                            .getResources().getDisplayMetrics().densityDpi;
                    final Rect r = new Rect();
                    delegate.getHitRect(r);
                    r.top -= transformToDensityPixel(top, densityDpi);
                    r.left -= transformToDensityPixel(left, densityDpi);
                    r.bottom += transformToDensityPixel(bottom, densityDpi);
                    r.right += transformToDensityPixel(right, densityDpi);
                    parent.setTouchDelegate(new TouchDelegate(r, delegate));
                }
            });
        }
    }

    public static int transformToDensityPixel(int regularPixel,
                                              DisplayMetrics displayMetrics) {
        return transformToDensityPixel(regularPixel, displayMetrics.densityDpi);
    }

    public static int transformToDensityPixel(float regularPixel, float densityDpi) {
        return (int) (regularPixel * densityDpi);
    }

    public static boolean checkBtnEnable(EditText... editTexts) {
        boolean enable = true;
        for (EditText each : editTexts) {
            if (StringUtils.isBlank(each.getText())) {
                enable = false;
                break;
            }
        }
        return enable;
    }

    /**
     * 设置沉浸模式
     *
     * @param view
     */
    public static void setImmersionMode(View view){
        int visibility = 0;
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            visibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            visibility |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            visibility |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        } else if (android.os.Build.VERSION.SDK_INT >= 14 && android.os.Build.VERSION.SDK_INT < 19) {
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            visibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            visibility |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            visibility |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            view.setSystemUiVisibility(visibility);
        }
    }


}
