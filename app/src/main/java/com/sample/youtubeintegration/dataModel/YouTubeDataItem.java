package com.sample.youtubeintegration.dataModel;

import java.util.List;

/**
 * Created by NoOne on 7/30/2016.
 */
public class YouTubeDataItem {
    List<YouTubeDataInfo> horizontalList;

    public YouTubeDataItem(List<YouTubeDataInfo> horizontalList) {
        this.horizontalList = horizontalList;
    }

    public List<YouTubeDataInfo> getHorizontalList() {
        return horizontalList;
    }

    public void setHorizontalList(List<YouTubeDataInfo> horizontalList) {
        this.horizontalList = horizontalList;
    }
}
