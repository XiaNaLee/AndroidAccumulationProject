package com.accumulation.lee.androidaccumulationproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accumulation.lee.androidaccumulationproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15/7/16.
 * android RecyclerView 支持添加FooterView And HeadView
 */
public class RcvSupportHead$FooterViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    private View mHeaderView;
    private View mFooterView;
    //headers
    private List<View> headers = new ArrayList<>();
    //footers
    private List<View> footers = new ArrayList<>();

    private List<String> items;

    public RcvSupportHead$FooterViewAdapter(List<String> data) {
        this.items = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item_view, parent, false);
            return new VHItem(v);
        } else if (viewType == TYPE_HEADER) {
            View v = headers.get(0);
            return new VHHeader(v);
        } else if (viewType == TYPE_FOOTER) {
            View v = footers.get(0);
            return new VHFooter(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHItem) {
            String dataItem = getItem(position);
            ((VHItem) holder).mTextView.setText(dataItem);
        } else if (holder instanceof VHHeader) {
            ((VHHeader) holder).mTextView.setText("HeaderView");
        } else if (holder instanceof VHFooter) {
            ((VHFooter) holder).mTextView.setText("FooterView");
        }
    }

    @Override
    public int getItemCount() {

        int count = headers.size() + items.size() + footers.size();
        return count;

    }

    @Override
    public int getItemViewType(int position) {
        if (position < headers.size()) {
            return TYPE_HEADER;
        } else if (position >= headers.size() + items.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private String getItem(int position) {
        return items.get(position - headers.size());
    }


    //add a header to the adapter
    public void addHeader(View header) {
        if (!headers.contains(header)) {
            headers.add(header);
            notifyItemInserted(headers.size() - 1);
        }
    }

    //remove a header from the adapter
    public void removeHeader(View header) {
        if (headers.contains(header)) {
            //animate
            notifyItemRemoved(headers.indexOf(header));
            headers.remove(header);
        }
    }

    //add a footer to the adapter
    public void addFooter(View footer) {
        if (!footers.contains(footer)) {
            footers.add(footer);
            //animate
            notifyItemInserted(headers.size() + items.size() + footers.size() - 1);
        }
    }

    //remove a footer from the adapter
    public void removeFooter(View footer) {
        if (footers.contains(footer)) {
            //animate
            notifyItemRemoved(headers.size() + items.size() + footers.indexOf(footer));
            footers.remove(footer);
        }
    }

    //add data
    public void addDatas(List<String> data) {
        items.addAll(data);
        notifyItemInserted(headers.size() + items.size() - 1);
    }

    //add data
    public void addData(String data) {
        items.add(data);
        notifyItemInserted(headers.size() + items.size() - 1);
    }


    class VHItem extends RecyclerView.ViewHolder {
        TextView mTextView;

        public VHItem(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

    class VHHeader extends RecyclerView.ViewHolder {
        TextView mTextView;

        public VHHeader(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

    class VHFooter extends RecyclerView.ViewHolder {
        TextView mTextView;

        public VHFooter(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
