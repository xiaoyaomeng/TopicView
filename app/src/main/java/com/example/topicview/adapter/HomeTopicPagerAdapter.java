package com.example.topicview.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class HomeTopicPagerAdapter extends PagerAdapter {

    private ArrayList<RecyclerView> mList;

    public HomeTopicPagerAdapter(ArrayList<RecyclerView> mList) {
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList.isEmpty() ? 0 : mList.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }
}

