package com.sample.youtubeintegration;

import com.sample.youtubeintegration.service.ApiRestService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by indianrenters on 7/30/16.
 */
public class RetrofitClass {

    public static String BASE_URL = "https://www.googleapis.com/";

    static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
    private static ApiRestService restService;

    public static ApiRestService getRestService() {
        OkHttpClient client = okHttpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        restService = retrofit.create(ApiRestService.class);

        return restService;
    }

}
