package com.sample.youtubeintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HomeActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();
    }

    private void initializeViews() {
        webview = (WebView) findViewById(R.id.webview);
        String html = getFormedLinkForYouTube("8pLylomQCe0");
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//            }
//        });
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setUserAgentString("Android");
        webview.setWebChromeClient(new WebChromeClient());
//        webview.loadData(html, "text/html", "utf-8");
        webview.loadDataWithBaseURL("",html,"text/html", "utf-8","");
    }

    public String getFormedLinkForYouTube(String videoToken) {
        String html =
//                "<iframe class=\"youtube-player\" "
//                        + "style=\"border: 0; width: 100%; height: 95%;"
//                        + "padding:0px; margin:0px\" "
//                        + "id=\"ytplayer\" type=\"text/html\" "
//                        + "src=\"http://www.youtube.com/embed/" + videoToken
//                        + "?fs=0\" frameborder=\"0\" " + "allowfullscreen autobuffer "
//                        + "controls onclick=\"this.play()\">\n" + "</iframe>\n";
//
//        "<iframe width=\"300\" height=\"300\" src=\"https://www.youtube.com/embed/"+
//                videoToken+"\" frameborder=\"0\" allowfullscreen></iframe>";
//

        "<iframe width=\"300\" height=\"300\" src=\"https://www.youtube.com/embed/"+
                videoToken+"?rel=0&amp;controls=0&amp;showinfo=0\" frameborder=\"0\" allowfullscreen></iframe>";

        return html;
    }
}
