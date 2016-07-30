package com.sample.youtubeintegration;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.youtubeintegration.dataModel.YouTubeDataItem;

import java.util.List;

/**
 * Created by NoOne on 7/30/2016.
 */
public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<YouTubeDataItem> mVerticalList;

    public VerticalRecyclerViewAdapter(Context mContext, List<YouTubeDataItem> mVerticalList) {
        this.mContext = mContext;
        this.mVerticalList = mVerticalList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vertical_adapter_data_item, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mVerticalList.get(position) != null) {
            MyHolder myHolder = (MyHolder) holder;

            HorizontalRecyclerViewAdapter adapter = new HorizontalRecyclerViewAdapter(mContext, mVerticalList.get(position).getHorizontalList());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            myHolder.horizontalRecyclerView.setLayoutManager(mLayoutManager);
            myHolder.horizontalRecyclerView.setItemAnimator(new DefaultItemAnimator());
            myHolder.horizontalRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return mVerticalList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public RecyclerView horizontalRecyclerView;
        public MyHolder(View itemView) {
            super(itemView);

            horizontalRecyclerView = (RecyclerView) itemView.findViewById(R.id.horizontal_recycler_view);
        }
    }
}
