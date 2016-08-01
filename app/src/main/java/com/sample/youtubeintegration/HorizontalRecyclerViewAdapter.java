package com.sample.youtubeintegration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sample.youtubeintegration.dataModel.YouTubeDataInfo;
import com.sample.youtubeintegration.dataModel.YouTubeDataItem;

import java.util.List;

/**
 * Created by NoOne on 7/30/2016.
 */
public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<YouTubeDataInfo> mHorizontalList;
    private WebView mWebView;
    private Button mPlayPause;
    private Button mStop;
    private boolean isWebPageLoaded;
    private boolean dispatchEvents = false;

    public HorizontalRecyclerViewAdapter(Context mContext, List<YouTubeDataInfo> mHorizontalList) {
        this.mContext = mContext;
        this.mHorizontalList = mHorizontalList;

        initializeWebView();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizonal_adapter_data_item, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mHorizontalList.get(position) != null) {
            MyHolder myHolder = (MyHolder) holder;
            String url = "";
            if (!TextUtils.isEmpty(mHorizontalList.get(position).getThumbnailUrl())) {
                url = mHorizontalList.get(position).getThumbnailUrl();
            }
            Glide.with(mContext).load(url)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(myHolder.iv);

            myHolder.tv.setText(mHorizontalList.get(position).getTitle());
            myHolder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playVideo(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHorizontalList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView tv;
        public MyHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.video_title);
            iv = (ImageView) itemView.findViewById(R.id.video_item);
        }
    }




    private void initializeWebView() {
        mWebView = (WebView) ((AppCompatActivity) mContext).findViewById(R.id.webview);
        mPlayPause = (Button) ((AppCompatActivity) mContext).findViewById(R.id.btn_play_pause);
        mStop = (Button) ((AppCompatActivity) mContext).findViewById(R.id.btn_stop);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setUserAgentString("Android");

//        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isWebPageLoaded = false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                isWebPageLoaded = true;
            }
        });

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(dispatchEvents){
                    dispatchEvents = false;
                    if(event.getAction() == MotionEvent.ACTION_UP)
                        togglePlayPause();
                    return false;
                }
                return true;
            }
        });

        mPlayPause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isWebPageLoaded) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
                        dispatchEvents = true;
                        mWebView.dispatchTouchEvent(event);
                    }
                }
                return false;
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWebPageLoaded) {
                    String html = getFormedLinkForYouTube(currentPlayingVideoId);
                    mWebView.loadDataWithBaseURL("", html, "text/html", "utf-8", "");
                }
            }
        });
    }

    private String getFormedLinkForYouTube(String videoToken) {
        String html = "<iframe width=\"336\" height=\"189\" src=\"https://www.youtube.com/embed/"+
                videoToken+"?rel=0&amp;controls=0&amp;showinfo=0\" frameborder=\"0\" allowfullscreen></iframe>";

        return html;
    }

    private void togglePlayPause(){
        if (mPlayPause != null) {
            if (mPlayPause.getText().equals("play")){
                mPlayPause.setText("pause");
            } else {
                mPlayPause.setText("play");
            }
        }
    }

    private String currentPlayingVideoId = "";
    private void playVideo(int position) {
        currentPlayingVideoId = mHorizontalList.get(position).getVideoId();
        String html = getFormedLinkForYouTube(currentPlayingVideoId);
        mWebView.loadDataWithBaseURL("",html,"text/html", "utf-8","");
        mPlayPause.setText("play");
    }

}
