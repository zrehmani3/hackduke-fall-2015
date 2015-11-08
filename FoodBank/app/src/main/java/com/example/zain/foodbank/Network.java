package com.example.zain.foodbank;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by zain on 11/8/15.
 */
public class Network {
    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), json);
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
