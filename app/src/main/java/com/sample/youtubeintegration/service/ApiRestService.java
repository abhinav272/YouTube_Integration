package com.sample.youtubeintegration.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by indianrenters on 7/30/16.
 */
public interface ApiRestService {
    @GET("youtube/v3/search")
    Call<JsonObject> getYouTubeFeeds(@Query("key") String appId, @Query("part") String part,
                                     @Query("pageToken") String pageToken, @Query("order") String order,
                                     @Query("maxResults") int maxResults);
}
