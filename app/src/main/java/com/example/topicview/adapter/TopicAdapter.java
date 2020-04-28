/*
 * Copyright (c) 2020 - present. All rights reserved.
 *
 * Author : Xianxiang.Hu
 * E-mail : huxianxiang@gmail.com
 * Version: v1.0.0
 * Date   : 2020/01/14 12:55
 * Desc   :
 */

package com.example.topicview.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topicview.R;
import com.example.topicview.bean.TopicBean;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private List<TopicBean> mData;
    private OnItemClickListener onItemClickListener;
    private Context mContext;
    private int columnCount = 5;

    public TopicAdapter(Context context, List<TopicBean> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_topic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, final int position) {
        final TopicBean item = mData.get(position);
        if (item == null) {
            return;
        }
        holder.title.setText(item.getTitle());
        Drawable top = mContext.getResources().getDrawable(item.getIcon() == 0 ? R.mipmap.icon_home_99 : item.getIcon());
        holder.title.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onTopicItemClick(item);
                }
            }
        });

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels; //屏幕宽度
        params.width = screenWidth / columnCount;
        holder.itemView.setLayoutParams(params);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public TopicViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }
    }

    public interface OnItemClickListener {

        public void onTopicItemClick(TopicBean position);

    }

}
