package com.sample.youtubeintegration.dataModel;

import java.util.List;

/**
 * Created by NoOne on 7/30/2016.
 */
public class YouTubeDataInfo {
    String title;
    String videoId;
    String thumbnailUrl;

    public YouTubeDataInfo(String title, String videoId, String thumbnailUrl) {
        this.title = title;
        this.videoId = videoId;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
