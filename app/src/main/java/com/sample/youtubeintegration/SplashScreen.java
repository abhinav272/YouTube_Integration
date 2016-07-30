package com.sample.youtubeintegration;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by indianrenters on 7/30/16.
 */
public class SplashScreen extends AppCompatActivity {
    private String BASE_URL = "https://www.youtube.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setUpYouTube();
    }
    private void setUpYouTube() {
        new YoutubeTask().execute();
    }

    class YoutubeTask extends AsyncTask<Void,Void,Void>{

        public YoutubeTask() {

        }

        @Override
        protected Void doInBackground(Void... params) {

            try{
//                URL url = new URL(BASE_URL);
//                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                DocumentBuilder db = dbf.newDocumentBuilder();
//                Document doc = db.parse(new InputSource(url.openStream()));
//                doc.getDocumentElement().normalize();

                URL url = new URL(BASE_URL);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream = connection.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inputStream, null);
                parser.nextTag();

                List<String> feeds = getFeeds(parser);
                Log.e("doInBackground: ", "done");



            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        private List<String> getFeeds(XmlPullParser parser) throws IOException, XmlPullParserException {
            List<String> entries = new ArrayList<>();

            while (parser.next() != XmlPullParser.END_TAG){
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals("button")) {
                    entries.add(getVideoLink(parser));
                }
            }


            return entries;
        }

        private String getVideoLink(XmlPullParser parser) {
            String s = parser.getAttributeValue(null,"data-video-ids");
            return s;
        }
    }
}
