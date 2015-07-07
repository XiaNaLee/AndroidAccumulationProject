package com.accumulation.lee.utils.view;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by liyong on 15/5/29.
 * <p/>
 * 代码demo
 * if (convertView == null) {
 * convertView = LayoutInflater.from(context)
 * .inflate(R.layout.banana_phone, parent, false);
 * }
 * <p/>
 * ImageView bananaView = ViewHolder.get(convertView, R.id.banana);
 * TextView phoneView = ViewHolder.get(convertView, R.id.phone);
 */
public class ViewHolder {

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
