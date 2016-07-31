package com.sample.youtubeintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sample.youtubeintegration.dataModel.YouTubeDataInfo;
import com.sample.youtubeintegration.dataModel.YouTubeDataItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String appId = "AIzaSyAm21PFKisoy8ajlldLnPmTWt6pybZxOCY";
    public static final String part = "snippet,id";
    public static final String order = "date";
    public static final int maxResults = 50;

    private List<YouTubeDataItem> verticalList;
    private VerticalRecyclerViewAdapter mAdapter;
    private RecyclerView mVerticalRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        verticalList = new ArrayList<>();
        mVerticalRecyclerView = (RecyclerView) findViewById(R.id.vertical_recycler_view);

        mAdapter = new VerticalRecyclerViewAdapter(this, verticalList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mVerticalRecyclerView.setLayoutManager(mLayoutManager);
        mVerticalRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mVerticalRecyclerView.setAdapter(mAdapter);

        fetchYouTubeData();
    }

    private String nextPageToken = "";
    private int horizontalItemCount = 5;
    private void fetchYouTubeData() {
        Call call = RetrofitClass.getRestService().getYouTubeFeeds(appId, part, nextPageToken, order, maxResults);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                JsonObject jsonObject;
                try {
                    if (response != null && (jsonObject = (JsonObject) response.body()) != null) {
                        nextPageToken = jsonObject.get("nextPageToken").getAsString();

                        JsonArray jsonArray = jsonObject.getAsJsonArray("items");


                        List<YouTubeDataInfo> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            try {
                                String videoId = jsonArray.get(i).getAsJsonObject().get("id").getAsJsonObject().get("videoId").getAsString();
                                String thumbnailUrl = jsonArray.get(i).getAsJsonObject().get("snippet").getAsJsonObject().get("thumbnails").getAsJsonObject().get("default").getAsJsonObject().get("url").getAsString();
                                String title = jsonArray.get(i).getAsJsonObject().get("snippet").getAsJsonObject().get("title").getAsString();
                                list.add(new YouTubeDataInfo(title, videoId, thumbnailUrl));
                            } catch (Exception e) {
                                // exception
                            }

                            if ((i+1) % horizontalItemCount == 0 || i == jsonArray.size() - 1) {
                                List<YouTubeDataInfo> tempList = new ArrayList<>();
                                tempList.addAll(list);
                                verticalList.add(new YouTubeDataItem(tempList));

                                list.clear();
                            }
                        }

                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    // exception
                }

                System.out.println("yes");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println("no");
            }
        });
    }
}
